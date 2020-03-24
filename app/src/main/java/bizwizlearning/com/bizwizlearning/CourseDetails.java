package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CourseDetails extends AppCompatActivity {

    TextView Namecd, Descriptioncd, Benefitscd;
    Button btnApply;
    ImageView Postercd;
    String name, description, benefit, urlPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Namecd = findViewById(R.id.cdName);
        Descriptioncd = findViewById(R.id.cdDescription);
        Benefitscd = findViewById(R.id.cdBenefits);
        Postercd = findViewById(R.id.cdPoster);
        btnApply = findViewById(R.id.btnApplyCourse);

        name = getIntent().getStringExtra("namePR");
        description = getIntent().getStringExtra("descriptionPR");
        benefit = getIntent().getStringExtra("benefitPR");
        urlPoster = getIntent().getStringExtra("posterPR");

        //Toast.makeText(this, "xyz" + name, Toast.LENGTH_SHORT).show();

        Namecd.setText(name);
        Descriptioncd.setText(description);
        Benefitscd.setText(benefit);
        Picasso.get().load(urlPoster).placeholder(R.drawable.sc_logo).fit().centerInside().into(Postercd);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CourseDetails.this, ApplyOfflineProgram.class);
                i.putExtra("course", name);
                startActivity(i);
            }
        });

    }
}
