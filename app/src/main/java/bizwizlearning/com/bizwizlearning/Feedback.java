package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    private EditText sub, feed;
    private Button sendFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        sendFeed = (Button) findViewById(R.id.btnSendFeed);
        sub = (EditText) findViewById(R.id.etMailSubject);
        feed = (EditText) findViewById(R.id.etMailFeed);

        sendFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

    }

    private void sendMail() {

        String subject = sub.getText().toString();
        String message = feed.getText().toString();
        String to = "it.bizwizlearning@gmail.com";

        if (subject.isEmpty()){
            Toast.makeText(Feedback.this, "Subject cannot be empty!", Toast.LENGTH_SHORT).show();

        }

        else if (message.isEmpty()){
            Toast.makeText(Feedback.this, "Message cannot be empty!", Toast.LENGTH_SHORT).show();
        }

        else {
            Intent feedbackIntent = new Intent(Intent.ACTION_SEND);
            feedbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "rahulkjain69@gmail.com" });
            feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            feedbackIntent.putExtra(Intent.EXTRA_TEXT, message);

            feedbackIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(feedbackIntent, "Send Using"));
        }
    }

}
