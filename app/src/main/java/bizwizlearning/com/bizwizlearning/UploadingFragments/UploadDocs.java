package bizwizlearning.com.bizwizlearning.UploadingFragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import bizwizlearning.com.bizwizlearning.Model.UploadDocModel;
import bizwizlearning.com.bizwizlearning.Model.UploadImageModel;
import bizwizlearning.com.bizwizlearning.R;

import static android.app.Activity.RESULT_OK;

public class UploadDocs extends Fragment {

    private Button selectFile, uploadFile;
    private TextView selectMessage;

    private ProgressBar progressBarUIm;
    private EditText etDocName;

    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    String fileExtension;

    Uri pdfUri;

    //FirebaseStorage storage;
    //FirebaseDatabase database;

    public UploadDocs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_docs, container, false);

        selectFile = (Button) v.findViewById(R.id.btn_chooseDoc);
        uploadFile = (Button) v.findViewById(R.id.btn_UploadDoc);
        selectMessage = v.findViewById(R.id.tv_selectedDoc);
        etDocName = v.findViewById(R.id.et_DocName);
        progressBarUIm = v.findViewById(R.id.progressBarDoc);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("docs");
        //database = FirebaseDatabase.getInstance();

        //storage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("docs");
        //mStorageRef = FirebaseStorage.getInstance().getReference();

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf();
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null)
                    uploadingFile();
                else
                    Toast.makeText(getActivity(), "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null){

            pdfUri = data.getData();
            fileExtension = data.getData().getLastPathSegment();
            selectMessage.setText("Selected file :\n" + fileExtension);
            //Toast.makeText(getActivity(),"Extension - " + getMimeType(pdfUri),Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"Error here",Toast.LENGTH_LONG).show();
        }
    }

    private void selectPdf() {

        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 101);
    }

    private void uploadingFile() {

        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getMimeType(pdfUri));

        mUploadTask = fileReference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        UploadDocModel upload = new UploadDocModel(etDocName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                        String uploadID = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadID).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful())
                                    Toast.makeText(getActivity(), "Upload success", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(), "Upload unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"YOYO" + e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBarUIm.setProgress((int) progress);
                    }
                });
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getActivity().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType.substring(mimeType.lastIndexOf("/")+1);
    }

}
