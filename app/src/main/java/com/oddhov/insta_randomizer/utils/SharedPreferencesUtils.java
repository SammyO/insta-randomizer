package com.oddhov.insta_randomizer.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesUtils {
    private static final String SHARED_PREFERENCES_TAG_ITEMS_NAME = "tagItemsList";

    private Context mContext;

    public SharedPreferencesUtils(Context context) {
        this.mContext = context;
    }

    public void addTagItemToMemory(String tagItem) {
        Set<String> currentTagItems = getTagItemsFromMemory();
        Set<String> newTagItems;
        if (currentTagItems != null) {
            newTagItems = new HashSet<>(currentTagItems);
        } else {
            newTagItems = new HashSet<>();
        }
        newTagItems.add(tagItem.startsWith("#") ? tagItem.substring(1) : tagItem);
        sharedPreferences()
                .edit()
                .putStringSet(SHARED_PREFERENCES_TAG_ITEMS_NAME, newTagItems)
                .apply();
    }

    public Set<String> getTagItemsFromMemory() {
        return sharedPreferences().getStringSet(SHARED_PREFERENCES_TAG_ITEMS_NAME, null);
    }


    private SharedPreferences sharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
