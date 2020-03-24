package bizwizlearning.com.bizwizlearning.ExploreFragments;

import android.content.Context;
import android.media.MediaPlayer;
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

import bizwizlearning.com.bizwizlearning.Adapter.AudioAdapter;
import bizwizlearning.com.bizwizlearning.Model.UploadSong;
import bizwizlearning.com.bizwizlearning.R;

public class ExploreAudio extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<UploadSong> mUpload;
    DatabaseReference databaseReference;

    ValueEventListener valueEventListener;
    static MediaPlayer mediaPlayer;
    AudioAdapter adapter;

    public ExploreAudio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_audio, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewExploreAudio);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBarShowSongs);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUpload = new ArrayList<>();

        adapter = new AudioAdapter(getActivity(), mUpload);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("songs");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                for (DataSnapshot dss:dataSnapshot.getChildren()) {
                    UploadSong uploadSong = dss.getValue(UploadSong.class);
                    uploadSong.setmKey(dss.getKey());
                    mUpload.add(uploadSong);
                }
                adapter.notifyDataSetChanged();
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

    public static void playSong(List<UploadSong> arrayListSongs, int adapterPosition) throws IOException {

        UploadSong uploadSong = arrayListSongs.get(adapterPosition);

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setDataSource(uploadSong.getSongLink());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.prepareAsync();
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
                adapter.getFilter().filter(s);
                return false;
            }
        });

        //return true ;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}