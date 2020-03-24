package bizwizlearning.com.bizwizlearning;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logout, settings, editProfile;
    private TextView profileInfo, fullname, MobNum;
    private CircleImageView displayPic;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersref;

    String currentUID;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = v.findViewById(R.id.btnLogout);
        settings = v.findViewById(R.id.btnSettings);
        editProfile = v.findViewById(R.id.btnEditProfile);
        profileInfo = v.findViewById(R.id.etEmailProfile);
        displayPic = (CircleImageView) v.findViewById(R.id.display_pic);
        fullname = v.findViewById(R.id.etFullName);
        MobNum = v.findViewById(R.id.tvMNumber);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUID = firebaseAuth.getCurrentUser().getUid();
        usersref = FirebaseDatabase.getInstance().getReference().child("Users");

        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getActivity(), Register.class));
            getActivity().finish();
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user.getEmail() != null) {
            profileInfo.setText(user.getEmail());
        }
        else{
            profileInfo.setText("No email provided");
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                //FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getActivity(), LogIn.class));
                getActivity().finish();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Settings.class));
            }
        });

        usersref.child(currentUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("fullname")) {
                        String fname = dataSnapshot.child("fullname").getValue().toString();
                        fullname.setText(fname);
                        String mNum = dataSnapshot.child("mobile").getValue().toString();
                        MobNum.setText(mNum);
                    }
                    if (dataSnapshot.hasChild("profileimage")) {
                        String dp = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(dp).placeholder(R.drawable.usernosign).error(R.drawable.sc_logo).into(displayPic);
                    }
                    else{
                        Toast.makeText(getActivity(), "No record in database", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfile.class));
            }
        });

        return v;
    }

}
