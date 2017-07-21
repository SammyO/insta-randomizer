package com.oddhov.insta_randomizer.models.realm_models;

import com.oddhov.insta_randomizer.models.TagItem;

import java.util.Iterator;

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

    public boolean deleteTagItem(TagItem tagItem) {
        Iterator<TagItem> it = mTagItems.iterator();
        while (it.hasNext()) {
            TagItem next = it.next();
            if (next.getId().equals(tagItem.getId())) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
