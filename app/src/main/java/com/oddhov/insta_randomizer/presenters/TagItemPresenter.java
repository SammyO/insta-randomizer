package com.oddhov.insta_randomizer.presenters;

import android.util.Log;

import com.oddhov.insta_randomizer.api.ImagesService;
import com.oddhov.insta_randomizer.models.Image;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.views.TagItemViewHolderView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TagItemPresenter extends BasePresenter<TagItemViewHolderView, TagItem> {

    private ImagesService mImagesService;
    private Observer<Image> mDisposableObserver;

    @Override
    protected void updateView() {
        this.mImagesService = new ImagesService();

        Log.e("TagItemPresenter", "updateView() called");
        view().setTagItemValue(mData.getTagValue());
        loadDataFromApi();
    }

    //TODO unbind Observer

    public TagItem getData() {
        return mData;
    }

    private void loadDataFromApi() {
        mDisposableObserver = new Observer<Image>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Image image) {
                Log.e("TagItemPresenter", "onNext images size: " + image.getTitle());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mImagesService.getImageForQuery(mDisposableObserver, "laptop");
    }
}