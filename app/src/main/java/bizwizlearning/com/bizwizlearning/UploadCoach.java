package bizwizlearning.com.bizwizlearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Random;

import bizwizlearning.com.bizwizlearning.Model.CoachModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class UploadCoach extends AppCompatActivity {

    private Button chooseImage, addCoach;
    private EditText cName,cMNum, cOrganisation, cLIN, cEmail, cSpecializtion, cDescription;
    private CircleImageView cImage;

    private ProgressDialog loadingBar;
    private DatabaseReference usersref;
    private StorageReference userstore;

    Uri mUri;
    private StorageTask mUploadTask;
    private TextView selectMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_coach);

        loadingBar = new ProgressDialog(this);
        final String cUID = getIntent().getStringExtra("cUID");

        chooseImage = findViewById(R.id.coachChooseImage);
        addCoach = findViewById(R.id.btnAddCoach);
        selectMessage = findViewById(R.id.coachImageText);

        cName = findViewById(R.id.coachUploadName);
        cMNum = findViewById(R.id.coachUploadMobile);
        cOrganisation = findViewById(R.id.coachUploadOrg);
        cLIN = findViewById(R.id.coachUploadLIN);
        cEmail = findViewById(R.id.coachUploadEmail);
        cSpecializtion = findViewById(R.id.coachUploadSpecialization);
        cDescription = findViewById(R.id.coachUploadDescription);

        //String xyz = System.currentTimeMillis() + ".json";

        usersref = FirebaseDatabase.getInstance().getReference().child("coaches").child(cUID);
        userstore = FirebaseStorage.getInstance().getReference().child("Coaches Images");

        cImage = findViewById(R.id.coachUploadImage);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togallery = new Intent();
                togallery.setAction(Intent.ACTION_GET_CONTENT);
                togallery.setType("image/*");
                startActivityForResult(togallery, 101);
            }
        });

        addCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AccountSetup();

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadCoach.this, "Upload in progress...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    public void uploadFile() {
        if (mUri != null)
        {
            StorageReference filepath = userstore.child(System.currentTimeMillis() + ".jpg");

            mUploadTask = filepath.putFile(mUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            CoachModel upload = new CoachModel(cName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString(),
                                    cMNum.getText().toString().trim(), cOrganisation.getText().toString().trim(),
                                    cLIN.getText().toString().trim(), cEmail.getText().toString().trim(),
                                    cSpecializtion.getText().toString().trim(), cDescription.getText().toString().trim());
                            String uploadID = usersref.push().getKey();
                            usersref.setValue(upload);
                            Toast.makeText(UploadCoach.this, "Successfully added",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadCoach.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else {
            Toast.makeText(UploadCoach.this,"No image selected",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null)
        {
            mUri = data.getData();
            String fileExtension = data.getData().getLastPathSegment();
            selectMessage.setText("Selected file :\n" + fileExtension);
            Picasso.get().load(mUri).into(cImage);
        }
    }

}
