package com.oddhov.insta_randomizer.models;


public class TagItem {
    private long mId;
    private String mTagValue;

    public TagItem(long id, String tagValue) {
        this.mId = id;
        this.mTagValue = tagValue;
    }

    public long getId() {
        return mId;
    }

    public String getTagValue() {
        return mTagValue;
    }
}
