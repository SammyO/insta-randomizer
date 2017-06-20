package com.oddhov.insta_randomizer.presenters;

import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.views.TagItemViewHolderView;

public class TagItemPresenter extends BasePresenter<TagItemViewHolderView, TagItem> {

    @Override
    protected void updateView() {
        view().setTagItemValue(mData.getTagValue());
    }
}
