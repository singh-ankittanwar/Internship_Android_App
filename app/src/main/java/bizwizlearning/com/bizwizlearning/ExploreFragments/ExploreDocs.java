package bizwizlearning.com.bizwizlearning.ExploreFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Adapter.DocsAdapter;
import bizwizlearning.com.bizwizlearning.Model.UploadDocModel;
import bizwizlearning.com.bizwizlearning.R;

public class ExploreDocs extends Fragment {

    RecyclerView recyclerView;
    DocsAdapter myAdapter;

    List<UploadDocModel> mUpload;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    //public static String url;
    ValueEventListener valueEventListener;


    public ExploreDocs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_docs, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewExploreDocs);
        progressBar = v.findViewById(R.id.progressBarShowDocs);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUpload = new ArrayList<>();

        myAdapter = new DocsAdapter(getActivity(), mUpload);
        //myAdapter = new DocsAdapter(recyclerView, getActivity(), new ArrayList<UploadDocModel>());
        recyclerView.setAdapter(myAdapter);

        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("docs");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                for (DataSnapshot dss:dataSnapshot.getChildren()){
                    UploadDocModel uploadDocModel = dss.getValue(UploadDocModel.class);
                    uploadDocModel.setmxKey(dss.getKey());
                    mUpload.add(uploadDocModel);
                }
                myAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),""+ databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }


    /*public static void viewDocs() throws IOException{

        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("application/pdf");

    }*/



    //------------------------------SearchView Below---------------------------------------


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
