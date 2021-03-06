package com.oddhov.insta_randomizer.views;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.adapter.OverviewAdapter;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.presenters.OverviewPresenter;
import com.oddhov.insta_randomizer.presenters.OverviewPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OverviewActivity extends AppCompatActivity implements OverviewView, SwipeRefreshLayout.OnRefreshListener {
    //region Static Fields
    private static final int POSITION_LIST = 0;
    private static final int POSITION_LOADING = 1;
    private static final int POSITION_EMPTY = 2;
    //endregion

    //region Fields
    @BindView(R.id.animator)
    ViewAnimator mViewAnimator;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private OverviewPresenter mPresenter;
    private OverviewAdapter mAdapter;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new OverviewPresenterImpl();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        mPresenter.onItemSwiped(viewHolder);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mAdapter = new OverviewAdapter();
        recyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout.setOnRefreshListener(this);
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

    //region SwipeRefreshLayout.OnRefreshListener
    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }
    //endregion

    //region View Injection
    @OnClick(R.id.floating_action_button)
    public void onAddButtonClicked() {
        mPresenter.onAddButtonClicked();
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
    public void showContent() {
        mViewAnimator.setDisplayedChild(POSITION_LIST);
    }

    @Override
    public void showToast(int message, String tagItemValue) {
        Toast.makeText(this, getString(message, tagItemValue), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddTagItemDialog(int title, View view, int buttonPositive,
                                     DialogInterface.OnClickListener buttonPositiveListener, int buttonNegative,
                                     DialogInterface.OnClickListener buttonNegativeListener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(buttonPositive, buttonPositiveListener)
                .setNegativeButton(buttonNegative, buttonNegativeListener)
                .show();
    }

    @Override
    public void setSwipeRefreshLayoutRefreshing(boolean setRefreshing) {
        mSwipeRefreshLayout.setRefreshing(setRefreshing);
    }

    @Override
    public void adapterSetupData(List<TagItem> tagItems) {
        mAdapter.setupData(tagItems);
    }

    @Override
    public void adapterAddItem(TagItem tagItem) {
        mAdapter.addItem(tagItem);
    }

    @Override
    public void adapterRemoveItem(TagItem tagItem) {
        mAdapter.removeItem(tagItem);
    }

    @Override
    public Activity getActivityContext() {
        return this;
    }

    @Override
    public View getInflatedView(int layoutResource) {
        return LayoutInflater.from(this).inflate(layoutResource,
                (ViewGroup) findViewById(android.R.id.content), false);
    }
    //endregion
}
