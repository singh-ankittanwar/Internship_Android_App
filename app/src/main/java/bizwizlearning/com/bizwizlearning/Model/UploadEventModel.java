package bizwizlearning.com.bizwizlearning.Model;

public class UploadEventModel  {

    String eName, eImageURL, eDate, eTime, eVenue, eDescription, eLink;

    public UploadEventModel() {}

    public UploadEventModel(String eName, String eImageURL, String eDate, String eTime, String eVenue, String eDescription, String eLink) {
        this.eName = eName;
        this.eImageURL = eImageURL;
        this.eDate = eDate;
        this.eTime = eTime;
        this.eVenue = eVenue;
        this.eDescription = eDescription;
        this.eLink = eLink;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteImageURL() {
        return eImageURL;
    }

    public void seteImageURL(String eImageURL) {
        this.eImageURL = eImageURL;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String geteVenue() {
        return eVenue;
    }

    public void seteVenue(String eVenue) {
        this.eVenue = eVenue;
    }

    public String geteDescription() {
        return eDescription;
    }

    public void seteDescription(String eDescription) {
        this.eDescription = eDescription;
    }

    public String geteLink() {
        return eLink;
    }

    public void seteLink(String eLink) {
        this.eLink = eLink;
    }
}
