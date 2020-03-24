package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreDocs;
import bizwizlearning.com.bizwizlearning.Model.CoachModel;
import bizwizlearning.com.bizwizlearning.R;

public class CoachesAdapter extends RecyclerView.Adapter<CoachesAdapter.CoachViewHolder> {

    private Context mCOntext;
    private List<CoachModel> mUploads;

    public CoachesAdapter(Context mCOntext, List<CoachModel> mUploads) {
        this.mCOntext = mCOntext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public CoachViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vi = LayoutInflater.from(mCOntext).inflate(R.layout.coach_item, viewGroup, false);
        return new CoachViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachViewHolder coachViewHolder, int i) {

        final CoachModel current = mUploads.get(i);
        coachViewHolder.CoachName.setText(current.getcName());
        coachViewHolder.CoachDescription.setText(current.getcDescription());
        coachViewHolder.CoachOrganization.setText(current.getcOrganisation());
        coachViewHolder.CoachSpecialization.setText(current.getcSpecializtion());

        Picasso.get().load(current.getcImageUrl()).fit().centerInside().into(coachViewHolder.CoachimageView);

        coachViewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calling = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + current.getcMNum()));
                mCOntext.startActivity(calling);
            }
        });
        coachViewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailing = new Intent(Intent.ACTION_SEND);
                emailing.setType("text/email");
                emailing.putExtra(Intent.EXTRA_EMAIL, new String[] { current.getcEmail() });
                mCOntext.startActivity(emailing);
            }
        });
        coachViewHolder.linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LINing = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/" + current.getcLIN()));
                mCOntext.startActivity(LINing);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class CoachViewHolder extends RecyclerView.ViewHolder {
        public TextView CoachName, CoachSpecialization, CoachOrganization, CoachDescription;
        public ImageView CoachimageView, email, call, linkedIn;

        public CoachViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.emailCoach);
            call = itemView.findViewById(R.id.callCoach);
            linkedIn = itemView.findViewById(R.id.linkedInCoach);
            CoachimageView = itemView.findViewById(R.id.Imagecoach);

            CoachName = itemView.findViewById(R.id.Namecoach);
            CoachSpecialization = itemView.findViewById(R.id.Specializatincoach);
            CoachOrganization = itemView.findViewById(R.id.Organizationcoach);
            CoachDescription = itemView.findViewById(R.id.Descriptioncoach);

        }
    }
}
