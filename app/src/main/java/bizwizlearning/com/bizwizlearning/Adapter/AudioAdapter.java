package bizwizlearning.com.bizwizlearning.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Explore;
import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreAudio;
import bizwizlearning.com.bizwizlearning.Model.UploadSong;
import bizwizlearning.com.bizwizlearning.R;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioAdapterViewHolder> implements Filterable {

    Context context;
    List<UploadSong> arrayListsongs;
    List<UploadSong> arrayListsongsFull;
    int abc = 0;

    public AudioAdapter(Context context, List<UploadSong> arrayListsongs)
    {
        this.context = context;
        this.arrayListsongs = arrayListsongs;
        arrayListsongsFull = new ArrayList<>(arrayListsongs);
    }

    @NonNull
    @Override
    public AudioAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_item, viewGroup, false);
        return  new AudioAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioAdapterViewHolder audioAdapterViewHolder, int i) {

        UploadSong uploadSong = arrayListsongs.get(i);
        audioAdapterViewHolder.titleTxt.setText(uploadSong.getSongTitle());
        audioAdapterViewHolder.durationTxt.setText(uploadSong.getSongDuration());
    }

    @Override
    public int getItemCount() {
        return arrayListsongs.size();
    }

    public class AudioAdapterViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView titleTxt, durationTxt;

        public AudioAdapterViewHolder(View itemView){
            super(itemView);
            titleTxt = itemView.findViewById(R.id.song_title);
            durationTxt = itemView.findViewById(R.id.song_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){

            try {
                //((ExploreAudio)context).playSong(arrayListsongs, getAdapterPosition());
                ExploreAudio.playSong(arrayListsongs, getAdapterPosition());

            } catch (IOException e) {
                e.printStackTrace();
            }
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
            if (abc == 0 )
                arrayListsongsFull.addAll(arrayListsongs);
            List<UploadSong> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
                filteredList.addAll(arrayListsongsFull);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (UploadSong item : arrayListsongsFull){
                    if (item.getSongTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            abc++;

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            arrayListsongs.clear();
            arrayListsongs.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
