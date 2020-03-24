package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Model.UploadImageModel;
import bizwizlearning.com.bizwizlearning.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements Filterable {

    private Context mContext;
    private List<UploadImageModel> mUploads;
    private List<UploadImageModel> mUploadFull;
    int abc =0;

    public ImageAdapter(Context context, List<UploadImageModel> uploads)
    {
        mContext = context;
        mUploads = uploads;
        mUploadFull = new ArrayList<>(mUploads);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        UploadImageModel uploadCurrent = mUploads.get(i);
        imageViewHolder.textViewName.setText(uploadCurrent.getName());
        Picasso.get().load(uploadCurrent.getImageUrl()).placeholder(R.drawable.sc_logo).fit()
                .centerCrop().into(imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_ViewName1);
            imageView = itemView.findViewById(R.id.imageViewUpload);
        }
    }

    //------------------------------------SearchView-----------------------------------------------

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            /*if (abc == 0)
                mUploadFull.addAll(mUploads);*/
            List<UploadImageModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
                filteredList.addAll(mUploadFull);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (UploadImageModel item : mUploadFull)
                    if (item.getName().toLowerCase().contains(filterPattern))
                        filteredList.add(item);
            }
            abc = 1;

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mUploads.clear();
            mUploads.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
