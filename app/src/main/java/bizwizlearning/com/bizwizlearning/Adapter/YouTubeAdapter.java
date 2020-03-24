package bizwizlearning.com.bizwizlearning.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Explore;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreVideo;
import bizwizlearning.com.bizwizlearning.Model.VideoDetails;
import bizwizlearning.com.bizwizlearning.R;
import bizwizlearning.com.bizwizlearning.YT_Player;

public class YouTubeAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    ArrayList<VideoDetails> videoDetailsArrayList;
    ArrayList<VideoDetails> videoDetailsArrayListFull;
    LayoutInflater inflater;

    int xabc = 0;

    public YouTubeAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsArrayList) {
        this.activity = activity;
        this.videoDetailsArrayList = videoDetailsArrayList;
        videoDetailsArrayListFull = new ArrayList<>(videoDetailsArrayList);
    }


    @Override
    public int getCount() {
        return this.videoDetailsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.videoDetailsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
        {
            inflater = this.activity.getLayoutInflater();
        }

        if (view == null)
        {
            view = inflater.inflate(R.layout.youtube_item, null);
        }

        ImageView imageView = view.findViewById(R.id.ytImageView);
        TextView textView = view.findViewById(R.id.ytTitle);
        LinearLayout linearLayout = view.findViewById(R.id.root);

        final VideoDetails videoDetails = this.videoDetailsArrayList.get(i);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View xview) {
                Intent k = new Intent(activity, YT_Player.class);
                String x = videoDetails.getYtvideoId();
                k.putExtra("videoId", x);
                activity.startActivity(k);
            }
        });

        Picasso.get().load(videoDetails.getYturl()).into(imageView);
        textView.setText(videoDetails.getYttitle());

        return view;
    }

    @Override
    public Filter getFilter() {
        return vexampleFilter;
    }

    private Filter vexampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (xabc == 0 )
                videoDetailsArrayListFull.addAll(videoDetailsArrayList);
            List<VideoDetails> vfilteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
                vfilteredList.addAll(videoDetailsArrayListFull);
            else {
                String vfilterPattern = charSequence.toString().toLowerCase().trim();

                for (VideoDetails vitem : videoDetailsArrayListFull){
                    if (vitem.getYttitle().toLowerCase().contains(vfilterPattern)){
                        vfilteredList.add(vitem);
                    }
                }
            }
            xabc++;

            FilterResults vresults = new FilterResults();
            vresults.values = vfilteredList;
            return vresults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            videoDetailsArrayList.clear();
            videoDetailsArrayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
