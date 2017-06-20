package com.oddhov.insta_randomizer.presenters;

import android.support.annotation.NonNull;

import com.oddhov.insta_randomizer.views.OverviewView;

public interface OverviewPresenter {
    void setup(@NonNull OverviewView view);

    void destroy();
}
