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

import bizwizlearning.com.bizwizlearning.Adapter.CoachesAdapter;
import bizwizlearning.com.bizwizlearning.Model.CoachModel;

public class Coaches extends AppCompatActivity {

    private RecyclerView cRecyclerView;
    private CoachesAdapter cAdapter;
    private ProgressBar cProgressCircle;

    private DatabaseReference cDatabaseRef;
    private List<CoachModel> cUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches);

        cRecyclerView = findViewById(R.id.recyclerViewCoaches);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(Coaches.this));

        cProgressCircle = findViewById(R.id.progressBarCoaches);
        cUploads = new ArrayList<>();

        cDatabaseRef = FirebaseDatabase.getInstance().getReference().child("coaches");

        cDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    CoachModel cUploading = dss.getValue(CoachModel.class);
                    cUploads.add(cUploading);
                }
                cAdapter = new CoachesAdapter(Coaches.this, cUploads);
                cRecyclerView.setAdapter(cAdapter);
                cProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Coaches.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                cProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
