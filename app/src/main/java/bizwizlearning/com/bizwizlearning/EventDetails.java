package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventDetails extends AppCompatActivity {

    private String name, date, time, description, venue, url, Eposter;
    private ImageView poster;
    private TextView Name, Date, Time, Venue, Description;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        name = getIntent().getStringExtra("bruh");
        date = getIntent().getStringExtra("dateE");
        time = getIntent().getStringExtra("timeE");
        venue = getIntent().getStringExtra("venueE");
        description = getIntent().getStringExtra("desE");
        url = getIntent().getStringExtra("linkE");
        Eposter = getIntent().getStringExtra("posterE");

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Name = findViewById(R.id.edName);
        Date = findViewById(R.id.edDate);
        Time = findViewById(R.id.edTime);
        Venue = findViewById(R.id.edVenue);
        Description = findViewById(R.id.edDescription);

        poster = findViewById(R.id.edPoster);
        register = findViewById(R.id.edbtnRegister);

        Name.setText(name);
        Date.setText(date);
        Time.setText(time);
        Venue.setText(venue);
        Description.setText(description);

        Picasso.get().load(Eposter).placeholder(R.drawable.sc_logo).fit()
                .centerInside().into(poster);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
