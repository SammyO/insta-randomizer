package com.oddhov.insta_randomizer.views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewAnimator;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.adapter.OverviewAdapter;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.presenters.OverviewPresenter;
import com.oddhov.insta_randomizer.presenters.OverviewPresenterImpl;

import java.util.List;

public class OverviewActivity extends AppCompatActivity implements OverviewView {
    //region Static Fields
    private static final int POSITION_LIST = 0;
    private static final int POSITION_LOADING = 1;
    private static final int POSITION_EMPTY = 2;
    //endregion

    //region Fields
    private OverviewPresenter mPresenter;
    private ViewAnimator mViewAnimator;
    private OverviewAdapter mAdapter;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mPresenter = new OverviewPresenterImpl();
        } else {
            // TODO
        }

        setContentView(R.layout.activity_main);

        mViewAnimator = (ViewAnimator) findViewById(R.id.animator);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new OverviewAdapter();
        recyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setup(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.destroy();
    }
    //endregion

    //region OverviewView interface
    @Override
    public void showEmpty() {
        mViewAnimator.setDisplayedChild(POSITION_EMPTY);
    }

    @Override
    public void showLoading() {
        mViewAnimator.setDisplayedChild(POSITION_LOADING);
    }

    @Override
    public void showContent(List<TagItem> tagItems) {
        mAdapter.setupData(tagItems);
        mViewAnimator.setDisplayedChild(POSITION_LIST);
    }
    //endregion
}
