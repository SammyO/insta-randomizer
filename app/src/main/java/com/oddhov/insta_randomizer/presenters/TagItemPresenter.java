package com.oddhov.insta_randomizer.presenters;

import android.util.Log;

import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.views.TagItemViewHolderView;

public class TagItemPresenter extends BasePresenter<TagItemViewHolderView, TagItem> {

    @Override
    protected void updateView() {
        Log.e("TagItemPresenter", "updateView() called");
        view().setTagItemValue(mData.getTagValue());
    }

    public TagItem getData() {
        return mData;
    }
}
