package bizwizlearning.com.bizwizlearning.Model;

public class CoachModel {

    private String cName, cImageUrl, cMNum, cOrganisation, cLIN, cEmail, cSpecializtion, cDescription;

    public CoachModel() {}

    public CoachModel(String cName, String cImageUrl, String cMNum, String cOrganisation, String cLIN, String cEmail, String cSpecializtion, String cDescription) {
        this.cName = cName;
        this.cImageUrl = cImageUrl;
        this.cMNum = cMNum;
        this.cOrganisation = cOrganisation;
        this.cLIN = cLIN;
        this.cEmail = cEmail;
        this.cSpecializtion = cSpecializtion;
        this.cDescription = cDescription;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcImageUrl() {
        return cImageUrl;
    }

    public void setcImageUrl(String cImageUrl) {
        this.cImageUrl = cImageUrl;
    }

    public String getcMNum() {
        return cMNum;
    }

    public void setcMNum(String cMNum) {
        this.cMNum = cMNum;
    }

    public String getcOrganisation() {
        return cOrganisation;
    }

    public void setcOrganisation(String cOrganisation) {
        this.cOrganisation = cOrganisation;
    }

    public String getcLIN() {
        return cLIN;
    }

    public void setcLIN(String cLIN) {
        this.cLIN = cLIN;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcSpecializtion() {
        return cSpecializtion;
    }

    public void setcSpecializtion(String cSpecializtion) {
        this.cSpecializtion = cSpecializtion;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }
}
