package bizwizlearning.com.bizwizlearning;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private DatabaseReference usersref;
    private TextView quote, quoteBy;
    private Button toEvents;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        quote = v.findViewById(R.id.quote);
        quoteBy = v.findViewById(R.id.quoteBy);
        toEvents = v.findViewById(R.id.btnToEvent);
        usersref = FirebaseDatabase.getInstance().getReference().child("Home Screen Quote");

        toEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Events.class));
            }
        });

        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String fname = dataSnapshot.child("Quote").getValue().toString();
                    quote.setText(fname);
                    String mNum = dataSnapshot.child("Quote by").getValue().toString();
                    quoteBy.setText(mNum);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

}
