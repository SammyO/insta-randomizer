package com.oddhov.insta_randomizer.presenters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.oddhov.insta_randomizer.views.OverviewView;

public interface OverviewPresenter {
    void setup(@NonNull OverviewView view);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder);

    void destroy();

    void onAddButtonClicked();

    void onRefresh();
}
