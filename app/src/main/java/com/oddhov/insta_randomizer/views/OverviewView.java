package com.oddhov.insta_randomizer.views;


import com.oddhov.insta_randomizer.models.TagItem;

import java.util.List;

public interface OverviewView {

    void showEmpty();

    void showLoading();

    void showContent(List<TagItem> tagItems);
}
