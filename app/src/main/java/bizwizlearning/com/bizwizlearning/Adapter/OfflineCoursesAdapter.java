package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bizwizlearning.com.bizwizlearning.CourseDetails;
import bizwizlearning.com.bizwizlearning.Model.UploadCourseModel;
import bizwizlearning.com.bizwizlearning.R;
import bizwizlearning.com.bizwizlearning.UploadOfflineCourses;

public class OfflineCoursesAdapter extends RecyclerView.Adapter<OfflineCoursesAdapter.OfflineCoursesViewHolder> {

    private Context prContext;
    private List<UploadCourseModel> prUploads;

    public OfflineCoursesAdapter(Context prContext, List<UploadCourseModel> prUploads) {
        this.prContext = prContext;
        this.prUploads = prUploads;
    }

    @NonNull
    @Override
    public OfflineCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View pr = LayoutInflater.from(prContext).inflate(R.layout.course_item, viewGroup, false);
        return new OfflineCoursesViewHolder(pr);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineCoursesViewHolder offlineCoursesViewHolder, int i) {

        UploadCourseModel currentUpload = prUploads.get(i);

        final String NameP = currentUpload.getPrName();
        final String DescriptionP = currentUpload.getPrDescription();
        final String BenefitP = currentUpload.getPrBenefits();
        final String posterP = currentUpload.getPrImageUrl();

        offlineCoursesViewHolder.PName.setText(NameP);

        Picasso.get().load(posterP).placeholder(R.drawable.sc_logo).fit().centerInside()
                .into(offlineCoursesViewHolder.prImageView);

        offlineCoursesViewHolder.relLayCO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pr = new Intent(prContext, CourseDetails.class);
                pr.putExtra("namePR", NameP);
                pr.putExtra("descriptionPR", DescriptionP);
                pr.putExtra("benefitPR", BenefitP);
                pr.putExtra("posterPR", posterP);
                prContext.startActivity(pr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prUploads.size();
    }

    public class OfflineCoursesViewHolder extends RecyclerView.ViewHolder {

        public ImageView prImageView;
        public RelativeLayout relLayCO;
        public TextView PName;

        public OfflineCoursesViewHolder(@NonNull View itemView) {
            super(itemView);

            prImageView = itemView.findViewById(R.id.courseItemPoster);
            PName = itemView.findViewById(R.id.courseItemName);
            relLayCO = itemView.findViewById(R.id.relLayCourseOff);
        }
    }
}
