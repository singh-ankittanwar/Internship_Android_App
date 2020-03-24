package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminAccess extends AppCompatActivity {

    private EditText etAdminPass;
    private Button checkadminPass, uploadResource, uploadCoach, uploadCourse;
    private TextView x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_access);

        checkadminPass = findViewById(R.id.btnconfirmadmin);
        etAdminPass = findViewById(R.id.et_adminPass);
        uploadResource = findViewById(R.id.btnToUploadResource);
        uploadCoach = findViewById(R.id.btnToUploadCoach);
        uploadCourse = findViewById(R.id.btnToUploadCourse);
        x = findViewById(R.id.poiu);

        checkadminPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAdminPass.getText().toString().equals("1234567")){
                    uploadCoach.setVisibility(View.VISIBLE);
                    uploadCourse.setVisibility(View.VISIBLE);
                    uploadResource.setVisibility(View.VISIBLE);

                    etAdminPass.setVisibility(View.INVISIBLE);
                    checkadminPass.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.INVISIBLE);
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        });

        uploadResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Uploading.class));
            }
        });

        uploadCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CoachUID.class));
            }
        });

        uploadCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadOfflineCourses.class));
            }
        });
    }
}
