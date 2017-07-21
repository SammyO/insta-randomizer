package com.oddhov.insta_randomizer.presenters;


import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V, M> {
    //region Fields
    protected WeakReference<V> mView;
    protected M mData;
    //endregion

    public void bindView(@NonNull V view) {
        mView = new WeakReference<>(view);
        refreshView();
    }

    public void unbindView() {
        mView = null;
    }

    public void setData(M data) {
        mData = data;

        if (setupDone()) {
            updateView();
        }
    }

    protected V view() {
        if (mView == null) {
            return null;
        } else {
            return mView.get();
        }
    }

    protected boolean setupDone() {
        return view() != null && mData != null;
    }

    protected void refreshView() {
        if (setupDone()) {
            updateView();
        }
    }

    //endregion

    protected abstract void updateView();
}
