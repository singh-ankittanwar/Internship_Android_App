package bizwizlearning.com.bizwizlearning.ExploreFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bizwizlearning.com.bizwizlearning.OfflineCourses;
import bizwizlearning.com.bizwizlearning.R;


public class ExplorePrograms extends Fragment {

    private ImageView onCImage, offCImage;
    private TextView onCtv, offCtv;

    public ExplorePrograms() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_programs, container, false);

        offCImage = v.findViewById(R.id.ivOfflineCourse);
        onCImage = v.findViewById(R.id.ivOnlineCourse);
        onCtv = v.findViewById(R.id.tvOnlineCourse);
        offCtv = v.findViewById(R.id.tvOfflineCourse);

        offCtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OfflineCourses.class));
            }
        });

        offCImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OfflineCourses.class));
            }
        });

        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
