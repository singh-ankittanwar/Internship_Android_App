package bizwizlearning.com.bizwizlearning.Model;

import com.google.firebase.database.Exclude;

public class UploadSong {

    public String songTitle, songDuration, songLink, mKey;

    public UploadSong(){}

    public UploadSong(String songTitle, String songDuration, String songLink){

        if (songTitle.trim().equals("")){
            songTitle = "No title";
        }

        this.songDuration = songDuration;
        this.songLink = songLink;
        this.songTitle = songTitle;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
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
