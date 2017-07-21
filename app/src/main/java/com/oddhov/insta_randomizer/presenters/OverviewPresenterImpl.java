package com.oddhov.insta_randomizer.presenters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.api.ImagesService;
import com.oddhov.insta_randomizer.models.Image;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.utils.DatabaseUtils;
import com.oddhov.insta_randomizer.views.OverviewView;
import com.oddhov.insta_randomizer.views.TagItemViewHolder;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class OverviewPresenterImpl extends BasePresenter<OverviewView, List<TagItem>> implements OverviewPresenter, DialogInterface.OnClickListener {
    private ImagesService mImagesService;
    private DatabaseUtils mDatabaseUtils;
    private Observer<Image> mDisposableObserver;
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

        this.mImagesService = new ImagesService();
        this.mDatabaseUtils = new DatabaseUtils(view().getActivityContext());

        // Only reload data when it hasn't been set yet
        if (mData == null && !mLoadingData) {
            onRefresh();
        }
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        TagItemViewHolder tagItemViewHolder = (TagItemViewHolder) viewHolder;
        mDatabaseUtils.deleteTagItem(tagItemViewHolder.getViewHolderPresenter().getData());

        if (view() != null) {
            view().adapterRemoveItem(tagItemViewHolder.getViewHolderPresenter().getData());
        }
        setData(mDatabaseUtils.getTagItems());
    }

    @Override
    public void destroy() {
        super.unbindView();
        // TODO dispose observer
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
            loadDataFromApi(mTagItemInput.getText().toString());
        }
    }
    //endregion

    //region Helper Methods
    private void loadDataFromMemory() {
        List<TagItem> storedTagItems = mDatabaseUtils.getTagItems();
        if (storedTagItems != null) {
            setData(storedTagItems);
        }
        if (view() != null) {
            view().setSwipeRefreshLayoutRefreshing(false);
        }
    }

    private void loadDataFromApi(String tagValue) {
        mDisposableObserver = new Observer<Image>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Image image) {
                TagItem newTagItem = new TagItem(
                        UUID.randomUUID().toString(),
                        mTagItemInput.getText().toString(),
                        image.getDisplaySizes().get(0).getUri());
                mDatabaseUtils.addTagItem(newTagItem);

                if (view() != null) {
                    view().showToast(R.string.added_tag_item_message, mTagItemInput.getText().toString());
                    view().adapterAddItem(newTagItem);
                }

                loadDataFromMemory();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mImagesService.getImageForQuery(mDisposableObserver, tagValue);
    }
    //endregion
}