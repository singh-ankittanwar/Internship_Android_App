package bizwizlearning.com.bizwizlearning.UploadingFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import bizwizlearning.com.bizwizlearning.Model.UploadEventModel;
import bizwizlearning.com.bizwizlearning.R;

import static android.app.Activity.RESULT_OK;

public class UploadEvent extends Fragment{

    private Button choosePoster, uploadEvent;
    private ProgressBar progressBarE;
    private EditText eName, eDate, eTime, eVenue, eDescription, eLink;
    private ImageView posterView;

    Uri mUri;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    public UploadEvent(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_event, container, false);

        choosePoster = v.findViewById(R.id.btn_choosePoster);
        uploadEvent = v.findViewById(R.id.btn_UploadEvent);
        progressBarE = v.findViewById(R.id.progress_barUploadEvent);

        eName = v.findViewById(R.id.et_eventName);
        eDate = v.findViewById(R.id.et_eventDate);
        eTime = v.findViewById(R.id.et_eventTime);
        eVenue = v.findViewById(R.id.et_eventVenue);
        eDescription = v.findViewById(R.id.et_eventDescription);
        eLink = v.findViewById(R.id.et_eventLink);

        posterView = v.findViewById(R.id.event_poster);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Events");

        mStorageRef = FirebaseStorage.getInstance().getReference().child("event posters");

        choosePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,101);
            }
        });

        uploadEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        return v;
    }

    private void uploadFile() {

        if (mUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mUri));

            mUploadTask = fileReference.putFile(mUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            UploadEventModel upload = new UploadEventModel(eName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString(), eDate.getText().toString().trim(),
                                    eTime.getText().toString().trim(), eVenue.getText().toString().trim(),
                                    eDescription.getText().toString().trim(), eLink.getText().toString().trim());
                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBarE.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(getActivity(),"No poster selected",Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null)
        {
            mUri = data.getData();
            Picasso.get().load(mUri).into(posterView);
        }
    }

}
