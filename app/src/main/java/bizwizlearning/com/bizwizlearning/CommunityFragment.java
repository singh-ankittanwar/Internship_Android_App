package bizwizlearning.com.bizwizlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {

    RadioGroup radioGroup;
    Button enterForum, askCoach;

    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_community, container, false);

        enterForum = v.findViewById(R.id.btnEForum);
        radioGroup = v.findViewById(R.id.radiogroupCommunity);
        askCoach = v.findViewById(R.id.askCoach);

        askCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Coaches.class));
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i)  {
                    case R.id.radioGeneral:
                        startActivity(new Intent(getActivity(), Forum1.class));
                        break;
                    case R.id.radioHealth:
                        startActivity(new Intent(getActivity(), Health.class));
                        break;
                }
            }
        });

        /*enterForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(radioID);

                if (radioButton.getText().toString().equals("General")){
                    startActivity(new Intent(getActivity(), Forum1.class));
                }
                else if (radioButton.getText() == "Health"){
                    startActivity(new Intent(getActivity(), Health.class));
                }

            }
        });*/

        return v;
    }

}
