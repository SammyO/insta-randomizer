package com.oddhov.insta_randomizer.views;


import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import com.oddhov.insta_randomizer.models.TagItem;

import java.util.List;

public interface OverviewView {

    void showEmpty();

    void showLoading();

    void showContent();

    Activity getActivityContext();

    void showAddTagItemDialog(int title, View view, int buttonPositive,
                              DialogInterface.OnClickListener buttonPositiveListener, int buttonNegative,
                              DialogInterface.OnClickListener buttonNegativeListener);

    void adapterSetupData(List<TagItem> tagItems);

    void adapterRemoveItem(TagItem tagItem);

    View getInflatedView(int layoutResource);

    void showToast(int message, String tagItemValue);
}
