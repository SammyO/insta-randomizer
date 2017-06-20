package com.oddhov.insta_randomizer.presenters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.utils.SharedPreferencesUtils;
import com.oddhov.insta_randomizer.views.OverviewView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OverviewPresenterImpl extends BasePresenter<OverviewView, List<TagItem>> implements OverviewPresenter, DialogInterface.OnClickListener {
    private boolean mLoadingData = false;

    private SharedPreferencesUtils mSharedPreferencesUtils;
    private EditText tagItemInput;

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
    @Override
    public void setup(@NonNull OverviewView view) {
        super.bindView(view);

        this.mSharedPreferencesUtils = new SharedPreferencesUtils(view().getActivityContext());

        // Only reload data  when it's not there yet
        if (mData == null && !mLoadingData) {
            view().showLoading();
            loadData();
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
        tagItemInput = (EditText) editTextParentView.findViewById(R.id.etHashtagInput);
        tagItemInput.setHint(R.string.add_tag_item_hint);

        view().showAddTagItemDialog(R.string.add_tag_item_title,
                editTextParentView,
                R.string.add_tag_item_save,
                this,
                R.string.add_tag_item_cancel);
    }
    //endregion

    //region DialogInterface.OnClickListener Interface
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mSharedPreferencesUtils.addTagItemToMemory(tagItemInput.getText().toString());
            if (view() != null) {
                view().showToast(R.string.added_tag_item_message, tagItemInput.getText().toString());
            }
            loadData();

        }
    }
    //endregion

    //region Helper Methods
    private void loadData() {
        List<TagItem> tagItems = new ArrayList<>();
        Set<String> storedTagItemStrings = mSharedPreferencesUtils.getTagItemsFromMemory();
        if (storedTagItemStrings != null) {
            for (String storedTagItem : storedTagItemStrings) {
                tagItems.add(new TagItem(UUID.randomUUID().toString(), storedTagItem));
            }
            setData(tagItems);
        } else if (view() != null) {
            view().showEmpty();
        }
    }
    //endregion
}
