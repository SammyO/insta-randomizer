package com.oddhov.insta_randomizer.models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TagItem extends RealmObject {
    @PrimaryKey
    @Required
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
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        TagItem tagItem = (TagItem) o;
//
//        if (!mId.equals(tagItem.mId)) {
//            return false;
//        }
//        if (mTagValue != null ? !mTagValue.equals(tagItem.mTagValue) : tagItem.mTagValue != null) {
//            return false;
//
//        }
//        return mUrl != null ? mUrl.equals(tagItem.mUrl) : tagItem.mUrl == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = mId.hashCode();
//        result = 31 * result + (mTagValue != null ? mTagValue.hashCode() : 0);
//        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
//        return result;
//    }
}
