package bizwizlearning.com.bizwizlearning;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import bizwizlearning.com.bizwizlearning.Model.UploadCourseModel;

public class UploadOfflineCourses extends AppCompatActivity {

    private Button chooseCoursePoster, uploadCourse;
    private EditText pName, pDescription, pBenefits;
    private ImageView pPosterView;
    private ProgressBar progressBarP;

    Uri pUri;
    private StorageTask pUploadTask;
    private StorageReference pStorageRef;
    private DatabaseReference pDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_offline_courses);

        chooseCoursePoster = findViewById(R.id.btnToUploadCoursePoster);
        uploadCourse = findViewById(R.id.btnUploadCourse);
        pName = findViewById(R.id.uploadCourseName);
        pDescription = findViewById(R.id.uploadCourseDescription);
        pBenefits = findViewById(R.id.uploadCourseBenefits);
        pPosterView = findViewById(R.id.uploadCoursePoster);
        progressBarP = findViewById(R.id.progress_barUploadProgram);

        pDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        pStorageRef = FirebaseStorage.getInstance().getReference().child("course posters");

        chooseCoursePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,101);
            }
        });

        uploadCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pUploadTask != null && pUploadTask.isInProgress()) {
                    Toast.makeText(UploadOfflineCourses.this, "Upload in progress...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void uploadFile() {

        if (pUri != null)
        {
            StorageReference fileReference = pStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(pUri));

            pUploadTask = fileReference.putFile(pUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            UploadCourseModel uploadPr = new UploadCourseModel(pName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString(), pDescription.getText().toString().trim(),
                                    pBenefits.getText().toString().trim());
                            String uploadxid = pDatabaseRef.push().getKey();
                            pDatabaseRef.child(uploadxid).setValue(uploadPr);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadOfflineCourses.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBarP.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(UploadOfflineCourses.this,"No course poster selected",Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null)
        {
            pUri = data.getData();
            Picasso.get().load(pUri).into(pPosterView);
        }
    }
}
