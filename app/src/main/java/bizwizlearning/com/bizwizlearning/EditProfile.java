package bizwizlearning.com.bizwizlearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    
    private CircleImageView EditDP;
    private EditText fullname, mNumber;
    private Button profilesaving;

    private FirebaseAuth mAuth;
    private DatabaseReference usersref;
    private StorageReference UserProfileImageRef;

    String currentUserID;
    final static int gallery_pick = 1;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        
        EditDP = findViewById(R.id.profilepicsetup);
        fullname = findViewById(R.id.NameSetup);
        mNumber = findViewById(R.id.MobileSetup);
        profilesaving = findViewById(R.id.btnSaveProfile);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        
        profilesaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountSetup();
            }
        });

        EditDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togallery = new Intent();
                togallery.setAction(Intent.ACTION_GET_CONTENT);
                togallery.setType("image/*");
                startActivityForResult(togallery, gallery_pick);
            }
        });

        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if (dataSnapshot.child("profileimage").exists()) {
                        String image = dataSnapshot.child("profileimage").getValue().toString();

                        //Picasso.with(EditProfile.this).load(image).placeholder(R.drawable.usernosign).into(EditDP);
                        Picasso.get().load(image).placeholder(R.drawable.usernosign).error(R.drawable.sc_logo).into(EditDP);

                    /*
                    * String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.with(SetupActivity.this).load(image).placeholder(R.drawable.profile).into(ProfileImage);
                    * */
                    }
                    else {
                        Toast.makeText(EditProfile.this, "dp dont exist initially", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(EditProfile.this, "Please Setup your profile", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == gallery_pick && resultCode == RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait, while we updating your profile image...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri =result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(EditProfile.this, "DP saved to firebase...", Toast.LENGTH_SHORT).show();

                            final String downloadUrl = task.getResult().getDownloadUrl().toString();
                            usersref.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                loadingBar.dismiss();
                                                Intent closegallery = new Intent(EditProfile.this, EditProfile.class);
                                                startActivity(closegallery);
                                                finish();

                                                Toast.makeText(EditProfile.this, "DP saved to firebase database...", Toast.LENGTH_SHORT).show();

                                            }
                                            else {
                                                loadingBar.dismiss();
                                                String message = task.getException().getMessage();
                                                Toast.makeText(EditProfile.this, "Error to firebase..." + message, Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                    }
                });
            }
            else {
                Toast.makeText(EditProfile.this, "Error in cropping image", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }

    private void AccountSetup() {

        String fname = fullname.getText().toString();
        String mNum = mNumber.getText().toString();

        if (TextUtils.isEmpty(fname)) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mNum)){
            Toast.makeText(this, "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap userMap = new HashMap();
            userMap.put("fullname", fname);
            userMap.put("mobile", mNum);
            userMap.put("country", "not yet developed");
            userMap.put("designation", "not yet developed");
            userMap.put("gender", "not yet developed");
            usersref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfile.this, "Information Stored!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        String message = task.getException().getMessage();
                        Toast.makeText(EditProfile.this, "Database update error = " + message, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
