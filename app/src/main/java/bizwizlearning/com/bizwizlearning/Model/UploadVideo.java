package bizwizlearning.com.bizwizlearning.Model;

import android.widget.ImageView;

import com.google.firebase.database.Exclude;

public class UploadVideo {

    public String videoTitle, videoDuration, videoLink, mKey;

    public UploadVideo(){}

    public UploadVideo (String videoTitle, String videoDuration, String videoLink){

        if (videoTitle.trim().equals("")){
            videoTitle = "No title";
        }

        this.videoDuration = videoDuration;
        this.videoLink = videoLink;
        this.videoTitle = videoTitle;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
