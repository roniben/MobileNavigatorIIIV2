package com.example.idgaf.mobilenavigatoriii.AdaptersConstructors;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String mDescription;

    public Upload(){

    }

    public Upload(String name, String imageUrl, String description) {
        if (name.trim().equals("") && description.equals("")) {
            name = "No Name";
            description = "No Description";
        }

        mName = name;
        mImageUrl = imageUrl;
        mDescription = description;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

}
