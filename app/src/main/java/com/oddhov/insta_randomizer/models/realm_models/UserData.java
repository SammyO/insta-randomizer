package com.oddhov.insta_randomizer.models.realm_models;

import com.oddhov.insta_randomizer.models.TagItem;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UserData extends RealmObject {
    private RealmList<TagItem> mTagItems;

    public UserData() {
    }

    public RealmList<TagItem> getTagItems() {
        return mTagItems;
    }

    public void addTagItem(TagItem tagItem) {
        mTagItems.add(tagItem);
    }

    public void deleteTagItem(TagItem tagItem) {
        mTagItems.remove(tagItem);
    }
}
