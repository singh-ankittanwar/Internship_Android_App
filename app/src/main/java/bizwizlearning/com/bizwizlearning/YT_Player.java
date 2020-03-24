package bizwizlearning.com.bizwizlearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YT_Player extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
        ,YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {

    YouTubePlayerView playerView;

    String API_KEY = "AIzaSyBrjv17t2G_oU18w6kKsQssRsg9aA29LRk";
    String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yt__player);

        if (getIntent().getStringExtra("videoId") == null)
        {
            Toast.makeText(YT_Player.this, "Video not found!",Toast.LENGTH_SHORT).show();
        }else
        {
            VIDEO_ID = getIntent().getStringExtra("videoId");
        }

        playerView = findViewById(R.id.yt_video_player);
        playerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);

        if (!b)
        {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
