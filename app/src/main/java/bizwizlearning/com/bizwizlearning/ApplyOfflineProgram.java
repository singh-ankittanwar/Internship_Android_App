package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ApplyOfflineProgram extends AppCompatActivity {

    private EditText name, mobile, email, org, message;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_offline_program);

        final String cousrename = getIntent().getStringExtra("course");

        name = findViewById(R.id.etnameApplyOffPro);
        mobile = findViewById(R.id.etmobileApplyOffPro);
        email = findViewById(R.id.etemailApplyOffPro);
        org = findViewById(R.id.etOrgApplyOffPro);
        message = findViewById(R.id.etMessageApplyOffPro);

        send = findViewById(R.id.btnSendOffProCustomerDetails);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim();
                String Mobile = mobile.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Org = org.getText().toString().trim();
                String Message = message.getText().toString().trim();
                String cName = cousrename;

                if (Name.isEmpty()) {
                    Toast.makeText(ApplyOfflineProgram.this, "Please enter your Name", Toast.LENGTH_LONG).show();
                }

                else if (Mobile.isEmpty()) {
                    Toast.makeText(ApplyOfflineProgram.this, "Please enter your Mobile", Toast.LENGTH_LONG).show();
                }

                else if (Email.isEmpty()) {
                    Toast.makeText(ApplyOfflineProgram.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                }

                else if (Org.isEmpty()) {
                    Toast.makeText(ApplyOfflineProgram.this, "Please enter your Organization", Toast.LENGTH_LONG).show();
                }
                else {
                    String content = "Hello,\n\nWe have received the following join request for course\n\n\nCourse Name:\t"+cName+"\n\nName:\t"+Name+"\n\nEmail:\t"
                            +Email+"\n\nMobile:\t"+Mobile+"\n\nOrganization:\t"+Org+"\n\nMessage:\t"+Message
                            +"\n\n\n\nThanks,\nBizwizLearning Team";

                    Intent OffProIntent = new Intent(Intent.ACTION_SEND);
                    OffProIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "contact@bizwizlearning.com" });
                    OffProIntent.putExtra(Intent.EXTRA_SUBJECT, "Course Join Request");
                    OffProIntent.putExtra(Intent.EXTRA_TEXT, content);

                    OffProIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(OffProIntent, "Send Using"));
                }

            }
        });
    }
}
