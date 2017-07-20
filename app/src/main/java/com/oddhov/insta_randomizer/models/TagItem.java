package com.oddhov.insta_randomizer.models;


import io.realm.RealmObject;

public class TagItem extends RealmObject {
    private String mId;
    private String mTagValue;

    public TagItem() {
    }

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
