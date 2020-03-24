package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CoachUID extends AppCompatActivity {

    EditText etcoachUID;
    Button btncoachUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_uid);

        etcoachUID = findViewById(R.id.et_coachUID);
        btncoachUID = findViewById(R.id.btn_coachUID);

        btncoachUID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etcoachUID.getText().toString().isEmpty())
                    Toast.makeText(CoachUID.this, "Coach UID can't be empty", Toast.LENGTH_SHORT).show();
                else {
                    String cUID = etcoachUID.getText().toString().trim();

                    Intent i = new Intent(CoachUID.this, UploadCoach.class);
                    i.putExtra("cUID", cUID);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
