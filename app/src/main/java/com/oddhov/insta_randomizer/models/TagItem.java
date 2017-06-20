package com.oddhov.insta_randomizer.models;


public class TagItem {
    private String mId;
    private String mTagValue;

    public TagItem(String id, String tagValue) {
        this.mId = id;
        this.mTagValue = tagValue;
    }

    public String getId() {
        return mId;
    }

    public String getTagValue() {
        return mTagValue;
    }
}
