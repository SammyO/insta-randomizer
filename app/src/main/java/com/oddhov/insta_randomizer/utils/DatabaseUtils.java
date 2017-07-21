package com.oddhov.insta_randomizer.utils;


import android.content.Context;

import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.models.realm_models.UserData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.exceptions.RealmException;

public class DatabaseUtils {
    private Realm mRealm;

    public DatabaseUtils(Context context) {
    }

    private void closeRealm() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }

    public void ensureUserDataIsSetup() throws RealmException {
        mRealm = Realm.getDefaultInstance();
        if (getUserData() == null) {
            final UserData userdata = new UserData();

            mRealm.executeTransactionAsync(realm -> realm.copyToRealm(userdata));

        }
        closeRealm();
    }

    public List<TagItem> getTagItems() throws RealmException {
        mRealm = Realm.getDefaultInstance();
        List<TagItem> tagItemsCopy;
        RealmList<TagItem> tagItems = getUserData().getTagItems();
        tagItemsCopy = mRealm.copyFromRealm(tagItems);
        closeRealm();
        return tagItemsCopy;
    }

    public void addTagItem(TagItem tagItem) {
        mRealm = Realm.getDefaultInstance();
        final UserData userData = getUserData();
        mRealm.executeTransaction(realm -> userData.addTagItem(tagItem));
        closeRealm();
    }

    public void deleteTagItem(TagItem tagItem) {
        mRealm = Realm.getDefaultInstance();
        final UserData userData = getUserData();
        mRealm.executeTransaction(realm ->
                userData.deleteTagItem(tagItem));
        closeRealm();
    }

    private UserData getUserData() throws RealmException {
        long instances = mRealm.where(UserData.class).count();
        if (instances <= 1) {
            return mRealm.where(UserData.class).findFirst();
        } else {
            throw new RealmException("More than one UserData instance");
        }
    }
}

