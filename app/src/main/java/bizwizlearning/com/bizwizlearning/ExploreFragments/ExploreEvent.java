package bizwizlearning.com.bizwizlearning.ExploreFragments;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bizwizlearning.com.bizwizlearning.Adapter.EventAdapter;
import bizwizlearning.com.bizwizlearning.Model.UploadEventModel;
import bizwizlearning.com.bizwizlearning.R;

public class ExploreEvent extends Fragment {

    private RecyclerView eRecyclerView;
    private ProgressBar eProgressCircle;
    private EventAdapter eAdapter;

    private DatabaseReference eDatabaseRef;
    private List<UploadEventModel> eUploads;

    public ExploreEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_event, container, false);

        eRecyclerView = v.findViewById(R.id.recyclerViewExploreEvents);
        eRecyclerView.setHasFixedSize(true);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eProgressCircle = v.findViewById(R.id.progress_barExploreEvents);
        eUploads = new ArrayList<>();

        eAdapter = new EventAdapter(getContext(), eUploads);
        eRecyclerView.setAdapter(eAdapter);

        setHasOptionsMenu(true);

        eDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Events");

        eDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    UploadEventModel upload = postSnapshot.getValue(UploadEventModel.class);
                    eUploads.add(upload);
                }
                eAdapter.notifyDataSetChanged();
                eProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                eProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

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
                eAdapter.getFilter().filter(s);
                return false;
            }
        });

        //return true ;
    }
}
