package com.oddhov.insta_randomizer.presenters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.utils.SharedPreferencesUtils;
import com.oddhov.insta_randomizer.views.OverviewView;
import com.oddhov.insta_randomizer.views.TagItemViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class OverviewPresenterImpl extends BasePresenter<OverviewView, List<TagItem>> implements OverviewPresenter, DialogInterface.OnClickListener {
    private SharedPreferencesUtils mSharedPreferencesUtils;
    private EditText mTagItemInput;

    private boolean mLoadingData = false;

    //region BasePresenter
    @Override
    protected void updateView() {
        if (mData == null || mData.size() == 0) {
            view().showEmpty();
        } else {
            view().adapterSetupData(mData);
            view().showContent();
        }
    }
    //endregion

    //region OverviewPresenter
    @Override
    public void setup(@NonNull OverviewView view) {
        super.bindView(view);

        this.mSharedPreferencesUtils = new SharedPreferencesUtils(view().getActivityContext());

        // Only reload data when it hasn't been set yet
        if (mData == null && !mLoadingData) {
            onRefresh();
        }
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        TagItemViewHolder tagItemViewHolder = (TagItemViewHolder) viewHolder;
        mSharedPreferencesUtils.removeTagItemToMemory(
                tagItemViewHolder.getViewHolderPresenter().getData().getTagValue());

        if (view() != null) {
            view().adapterRemoveItem(tagItemViewHolder.getViewHolderPresenter().getData());
        }
    }

    @Override
    public void destroy() {
        super.unbindView();
    }

    @Override
    public void onAddButtonClicked() {
        if (view() == null) {
            return;
        }

        View editTextParentView = view().getInflatedView(R.layout.text_input_tagitem);
        mTagItemInput = (EditText) editTextParentView.findViewById(R.id.etHashtagInput);
        mTagItemInput.setHint(R.string.add_tag_item_hint);

        view().showAddTagItemDialog(R.string.add_tag_item_title,
                editTextParentView,
                R.string.add_tag_item_save,
                this,
                R.string.add_tag_item_cancel,
                null);
    }

    @Override
    public void onRefresh() {
        if (view() != null) {
            view().showLoading();
            loadDataFromMemory();
        }
    }
    //endregion

    //region DialogInterface.OnClickListener Interface
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mSharedPreferencesUtils.addTagItemToMemory(mTagItemInput.getText().toString());
            if (view() != null) {
                view().showToast(R.string.added_tag_item_message, mTagItemInput.getText().toString());
                view().adapterAddItem(new TagItem(UUID.randomUUID().toString(), mTagItemInput.getText().toString()));
                loadDataFromMemory();
            }
        }
    }
    //endregion

    //region Helper Methods
    private void loadDataFromMemory() {
        List<TagItem> tagItems = new ArrayList<>();
        Set<String> storedTagItemStrings = mSharedPreferencesUtils.getTagItemsFromMemory();
        if (storedTagItemStrings != null) {
            for (String storedTagItem : storedTagItemStrings) {
                tagItems.add(new TagItem(UUID.randomUUID().toString(), storedTagItem));
            }
        }
        setData(tagItems);
        if (view() != null) {
            view().setSwipeRefreshLayoutRefreshing(false);
        }
    }
    //endregion
}