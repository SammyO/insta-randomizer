package com.oddhov.insta_randomizer.models;


import io.realm.RealmObject;

public class TagItem extends RealmObject {
    private String mId;
    private String mTagValue;
    private String mUrl;

    public TagItem() {
    }

    public TagItem(String id, String tagValue, String url) {
        this.mId = id;
        this.mTagValue = tagValue;
        this.mUrl = url;
    }

    public String getId() {
        return mId;
    }

    public String getTagValue() {
        return mTagValue;
    }

    public String getUrl() {
        return this.mUrl;
    }
}
