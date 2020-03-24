package bizwizlearning.com.bizwizlearning.UploadingFragments;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bizwizlearning.com.bizwizlearning.Model.UploadSong;
import bizwizlearning.com.bizwizlearning.R;

import static android.app.Activity.RESULT_OK;

public class UploadAudio extends Fragment {

    AppCompatEditText editTextTitle;
    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri;
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    DatabaseReference referenceSongs;
    Button choose, upload;


    public UploadAudio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_audio, container, false);

        editTextTitle = (AppCompatEditText)v.findViewById(R.id.songTitle);
        textViewImage = (TextView)v.findViewById(R.id.tvSongFileSelected);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBarSong);
        referenceSongs = FirebaseDatabase.getInstance().getReference().child("songs");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("songs");
        choose = (Button) v.findViewById(R.id.btn_uploadAudio);
        upload = (Button) v.findViewById(R.id.btn_chooseAudio);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAudioFile();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAudioToFirebase();
            }
        });

        return v;
    }

    public void openAudioFile()
    {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");   //audio -> video
        startActivityForResult(i,101);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null)
        {
            audioUri = data.getData();
            String fileName = getFileName(audioUri);
            textViewImage.setText(fileName);
            Log.w("----------FA2---------", "swag cursor try");


        }
        else if (requestCode != 101) {
            Toast.makeText(getActivity(), "failed request code", Toast.LENGTH_LONG).show();
            Log.w("----------FA2---------", "swag cursor try1");

        }
        else if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), "result not ok", Toast.LENGTH_LONG).show();
            Log.w("----------FA2---------", "swag cursor try2");

        }
        else {
            Toast.makeText(getActivity(), "we r fucked up", Toast.LENGTH_LONG).show();
            Log.w("----------FA2---------", "swag cursor try3");

        }
    }

    private String getFileName(Uri uri)
    {
        String result = null;
        if (uri.getScheme().equals("content"))
        {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst())
                {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }


        if (result == null)
        {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1)
            {
                result = result.substring(cut + 1);
            }
        }

        return result;
    }

    public void uploadAudioToFirebase()
    {
        if (textViewImage.getText().toString().equals("No file selected"))
        {
            Toast.makeText(getActivity(),"Please select an audio",Toast.LENGTH_LONG).show();
        }
        else {
            if (mUploadTask != null && mUploadTask.isInProgress()){
                Toast.makeText(getActivity(),"Upload in progress...",Toast.LENGTH_LONG).show();
            }
            else
                uploadFile();
        }
    }

    private void uploadFile() {

        if (audioUri != null)
        {
            String durationTXT;
            Toast.makeText(getActivity(),"Uploading...",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);

            final StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(audioUri));     //----> here

            int durationInMillis = findSongDuration(audioUri);                         //----> here

            if (durationInMillis == 0)
                durationTXT = "NA";

            durationTXT = getDurationFromMilli(durationInMillis);                     //----> here

            final String finalDurationTXT = durationTXT;
            mUploadTask = storageReference.putFile(audioUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UploadSong uploadSong = new UploadSong(editTextTitle.getText().toString(), finalDurationTXT,uri.toString());

                                    String uploadId = referenceSongs.push().getKey();
                                    referenceSongs.child(uploadId).setValue(uploadSong);
                                }
                            });

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);

                        }
                    });

        }
        else
        {
            Toast.makeText(getActivity(),"No file selected to upload",Toast.LENGTH_LONG).show();
        }

    }


    private String getDurationFromMilli(int durationInMillis) {

        Date date = new Date(durationInMillis);
        SimpleDateFormat simple = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String myTime = simple.format(date);
        return  myTime;
    }

    private int findSongDuration(Uri audioUri) {
        int timeInMillisec = 0;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(getActivity(), audioUri);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            timeInMillisec = Integer.parseInt(time);

            retriever.release();
            return timeInMillisec;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private String getFileExtension(Uri audioUri) {

        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));

    }
}
