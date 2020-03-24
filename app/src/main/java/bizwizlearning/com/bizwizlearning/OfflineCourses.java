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

import bizwizlearning.com.bizwizlearning.Adapter.OfflineCoursesAdapter;
import bizwizlearning.com.bizwizlearning.Model.UploadCourseModel;

public class OfflineCourses extends AppCompatActivity {

    private RecyclerView pRecyclerView;
    private ProgressBar pProgressCircle;
    private OfflineCoursesAdapter pAdapter;

    private DatabaseReference pDatabaseRef;
    private List<UploadCourseModel> pUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_courses);

        pRecyclerView = findViewById(R.id.recyclerViewExploreCourse);
        pRecyclerView.setHasFixedSize(true);
        pRecyclerView.setLayoutManager(new LinearLayoutManager(OfflineCourses.this));

        pProgressCircle = findViewById(R.id.progress_barExploreCourse);
        pUploads = new ArrayList<>();

        pDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        pDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    UploadCourseModel uploadCourseModel = dataSnapshot1.getValue(UploadCourseModel.class);
                    pUploads.add(uploadCourseModel);
                }
                pAdapter = new OfflineCoursesAdapter(OfflineCourses.this, pUploads);
                pRecyclerView.setAdapter(pAdapter);
                pProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OfflineCourses.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
