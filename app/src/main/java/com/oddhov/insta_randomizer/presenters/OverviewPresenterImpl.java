package com.oddhov.insta_randomizer.presenters;

import android.support.annotation.NonNull;

import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.views.OverviewView;

import java.util.ArrayList;
import java.util.List;

public class OverviewPresenterImpl extends BasePresenter<OverviewView, List<TagItem>> implements OverviewPresenter {
    private boolean mLoadingData = false;

    //region BasePresenter
    @Override
    protected void updateView() {
        // Business logic is in the presenter
        if (mData == null || mData.size() == 0) {
            view().showEmpty();
        } else {
            view().showContent(mData);
        }
    }
    //endregion

    //region OverviewPresenter
    public void setup(@NonNull OverviewView view) {
        super.bindView(view);

        // Only reload data when it's not there yet
        if (mData == null && !mLoadingData) {
            view().showLoading();
            loadData();
        }
    }

    public void destroy() {
        super.unbindView();
    }
    //endregion

    //region Helper Methods
    private void loadData() {
        // TODO fetch data from Realm
        List<TagItem> tagItems = new ArrayList<>();
        tagItems.add(new TagItem(1, "picoftheday"));
        tagItems.add(new TagItem(2, "random"));
        tagItems.add(new TagItem(3, "amsterdam"));

        setData(tagItems);
    }
    //endregion

}
