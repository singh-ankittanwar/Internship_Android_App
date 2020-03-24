package bizwizlearning.com.bizwizlearning.Model;

import com.google.firebase.database.Exclude;

public class UploadDocModel {

    public String mxName, mxDocsUrl, mxKey;

    public UploadDocModel() {}

    public UploadDocModel(String name, String docUrl) {

        if (name.trim().equals("")) {
            name = "No Name";
        }

        mxName = name;
        mxDocsUrl = docUrl;
    }

    public String getmxName() {
        return mxName;
    }

    public void setName(String name) {
        mxName = name;
    }

    public String getmxDocsUrl() {
        return mxDocsUrl;
    }

    public void setmxDocsUrl(String docsUrl) {
        mxDocsUrl = docsUrl;
    }

    @Exclude
    public String getmxKey() {
        return mxKey;
    }

    @Exclude
    public void setmxKey(String mKey) {
        this.mxKey = mKey;
    }
}
