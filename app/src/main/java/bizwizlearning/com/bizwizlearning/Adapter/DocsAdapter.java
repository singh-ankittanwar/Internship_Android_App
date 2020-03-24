package bizwizlearning.com.bizwizlearning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.ExploreFragments.ExploreDocs;
import bizwizlearning.com.bizwizlearning.Model.UploadDocModel;
import bizwizlearning.com.bizwizlearning.R;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.mViewHolder> implements Filterable {

    //RecyclerView recyclerView;
    Context context;
    List<UploadDocModel> items;
    List<UploadDocModel> itemFull;
    int abc = 0;

    //List<UploadDocModel> items = new ArrayList<>();
    //ArrayList<String> urls = new ArrayList<>();

    public DocsAdapter(Context context, List<UploadDocModel> items) {
        this.context = context;
        this.items = items;
        itemFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.docs_item, viewGroup, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final mViewHolder viewHolder, final int position) {

        UploadDocModel uploadDocModel = items.get(position);
        viewHolder.nameOfFile.setText(uploadDocModel.getmxName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(items.get(position).getmxDocsUrl()),"application/pdf");
                viewHolder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        TextView nameOfFile;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfFile = itemView.findViewById(R.id.tv_DocName);
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
            if (abc == 0)
                itemFull.addAll(items);
            List<UploadDocModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
                filteredList.addAll(itemFull);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (UploadDocModel xitem: itemFull)
                    if (xitem.getmxName().toLowerCase().contains(filterPattern))
                        filteredList.add(xitem);
            }
            abc++;

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
