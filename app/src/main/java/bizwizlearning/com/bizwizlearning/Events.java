package bizwizlearning.com.bizwizlearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Adapter.EventAdapter;
import bizwizlearning.com.bizwizlearning.Model.UploadEventModel;

public class Events extends AppCompatActivity {

    private RecyclerView eRecyclerView;
    private EventAdapter eAdapter;
    private ProgressBar eProgressCircle;

    private DatabaseReference eDatabaseRef;
    private List<UploadEventModel> eUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eRecyclerView =findViewById(R.id.recyclerViewEvents);
        eRecyclerView.setHasFixedSize(true);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(Events.this));

        eProgressCircle = findViewById(R.id.progress_barEvents);
        eUploads = new ArrayList<>();

        eDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Events");

        eDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    UploadEventModel upload = postSnapshot.getValue(UploadEventModel.class);
                    eUploads.add(upload);
                }
                eAdapter = new EventAdapter(Events.this, eUploads);
                eRecyclerView.setAdapter(eAdapter);
                eProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Events.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                eProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
