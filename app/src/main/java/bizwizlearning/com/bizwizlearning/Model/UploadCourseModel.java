package bizwizlearning.com.bizwizlearning.Model;

public class UploadCourseModel {

    String prName, prImageUrl, prDescription, prBenefits;

    public UploadCourseModel() {}

    public UploadCourseModel(String prName, String prImageUrl, String prDescription, String prBenefits) {
        this.prName = prName;
        this.prImageUrl = prImageUrl;
        this.prDescription = prDescription;
        this.prBenefits = prBenefits;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrImageUrl() {
        return prImageUrl;
    }

    public void setPrImageUrl(String prImageUrl) {
        this.prImageUrl = prImageUrl;
    }

    public String getPrDescription() {
        return prDescription;
    }

    public void setPrDescription(String prDescription) {
        this.prDescription = prDescription;
    }

    public String getPrBenefits() {
        return prBenefits;
    }

    public void setPrBenefits(String prBenefits) {
        this.prBenefits = prBenefits;
    }
}
