package bizwizlearning.com.bizwizlearning.ExploreFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bizwizlearning.com.bizwizlearning.Adapter.YouTubeAdapter;
import bizwizlearning.com.bizwizlearning.Model.VideoDetails;
import bizwizlearning.com.bizwizlearning.R;

public class ExploreVideo extends Fragment {

    ListView listView;
    ArrayList<VideoDetails> videoDetailsArrayList;
    YouTubeAdapter youTubeAdapter;

    ProgressBar vProgressBar;
    String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCwy4OTRkX5xx9Y3lS5M6Ifg&maxResults=50&key=AIzaSyBrjv17t2G_oU18w6kKsQssRsg9aA29LRk";

    public ExploreVideo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_explore_video, container, false);

        listView = v.findViewById(R.id.listView);
        videoDetailsArrayList = new ArrayList<>();
        vProgressBar = v.findViewById(R.id.progressBarShowVideos);

        youTubeAdapter = new YouTubeAdapter(getActivity(), videoDetailsArrayList);
        setHasOptionsMenu(true);
        displayVideos();

        return v;
    }

    private void displayVideos()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for (int i=1; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        String video_id = jsonVideoId.getString("videoId");

                        VideoDetails vd = new VideoDetails();

                        vd.setYtvideoId(video_id);
                        vd.setYttitle(jsonObjectSnippet.getString("title"));
                        vd.setYtdescription(jsonObjectSnippet.getString("description"));
                        vd.setYturl(jsonObjectDefault.getString("url"));

                        videoDetailsArrayList.add(vd);
                    }
                    listView.setAdapter(youTubeAdapter);
                    youTubeAdapter.notifyDataSetChanged();
                    vProgressBar.setVisibility(View.GONE);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView vsearchView = (SearchView) searchItem.getActionView();

        vsearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        vsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                youTubeAdapter.getFilter().filter(s);
                return false;
            }
        });

        //return true ;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
