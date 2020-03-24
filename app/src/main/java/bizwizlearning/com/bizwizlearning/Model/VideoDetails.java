package bizwizlearning.com.bizwizlearning.Model;

public class VideoDetails {

    public String ytvideoId, yttitle, ytdescription, yturl;

    public VideoDetails() {}

    public VideoDetails(String ytvideoId, String yttitle, String ytdescription, String yturl) {
        this.ytvideoId = ytvideoId;
        this.yttitle = yttitle;
        this.ytdescription = ytdescription;
        this.yturl = yturl;
    }

    public String getYtvideoId() {
        return ytvideoId;
    }

    public void setYtvideoId(String ytvideoId) {
        this.ytvideoId = ytvideoId;
    }

    public String getYttitle() {
        return yttitle;
    }

    public void setYttitle(String yttitle) {
        this.yttitle = yttitle;
    }

    public String getYtdescription() {
        return ytdescription;
    }

    public void setYtdescription(String ytdescription) {
        this.ytdescription = ytdescription;
    }

    public String getYturl() {
        return yturl;
    }

    public void setYturl(String yturl) {
        this.yturl = yturl;
    }
}
