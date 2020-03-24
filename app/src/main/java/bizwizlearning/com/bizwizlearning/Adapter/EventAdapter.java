package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.EventDetails;
import bizwizlearning.com.bizwizlearning.Model.UploadEventModel;
import bizwizlearning.com.bizwizlearning.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> implements Filterable {

    private Context eContext;
    private List<UploadEventModel> eUploads;
    private List<UploadEventModel> eUploadsFull;
    int abc = 0;

    public EventAdapter(Context eContext, List<UploadEventModel> eUploads) {
        this.eContext = eContext;
        this.eUploads = eUploads;
        eUploadsFull = new ArrayList<>(eUploads);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View e = LayoutInflater.from(eContext).inflate(R.layout.event_item, viewGroup, false);
        return new EventViewHolder(e);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

        UploadEventModel currentUpload = eUploads.get(i);
        final String nameX = currentUpload.geteName();
        final String date = currentUpload.geteDate();
        final String time = currentUpload.geteTime();
        final String venue = currentUpload.geteVenue();
        final String description = currentUpload.geteDescription();
        final String link = currentUpload.geteLink();
        final String poster = currentUpload.geteImageURL();

        eventViewHolder.EName.setText(currentUpload.geteName());
        eventViewHolder.EDate.setText(currentUpload.geteDate());
        eventViewHolder.ETime.setText(currentUpload.geteTime());
        eventViewHolder.EVenue.setText(currentUpload.geteVenue());

        Picasso.get().load(currentUpload.geteImageURL()).placeholder(R.drawable.sc_logo).fit()
                .centerCrop().into(eventViewHolder.EimageView);

        eventViewHolder.relLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(eContext, EventDetails.class);
                e.putExtra("bruh", nameX);
                e.putExtra("dateE", date);
                e.putExtra("timeE", time);
                e.putExtra("venueE", venue);
                e.putExtra("desE", description);
                e.putExtra("linkE", link);
                e.putExtra("posterE", poster);
                eContext.startActivity(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eUploads.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public ImageView EimageView;
        public TextView EName, EDate, ETime, EVenue, EDescription, ELink;
        public RelativeLayout relLay;

        public EventViewHolder(@NonNull final View itemView) {
            super(itemView);

            EimageView = itemView.findViewById(R.id.eventItemPoster);
            EName = itemView.findViewById(R.id.eventItemName);
            EDate = itemView.findViewById(R.id.tvDateEvent);
            ETime = itemView.findViewById(R.id.tvTimeEvent);
            EVenue = itemView.findViewById(R.id.tvVenueEvent);
            relLay = itemView.findViewById(R.id.relLayEvent);

        }
    }


    //------------------------------SearchView Below---------------------------------------

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            //if (abc == 0 )
              //  eUploadsFull.addAll(eUploads);
            List<UploadEventModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
                filteredList.addAll(eUploadsFull);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (UploadEventModel item : eUploadsFull){
                    if (item.geteName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            //abc++;

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            eUploads.clear();
            eUploads.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
