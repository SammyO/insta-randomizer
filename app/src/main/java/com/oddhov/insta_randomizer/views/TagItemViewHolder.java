package com.oddhov.insta_randomizer.views;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.presenters.TagItemPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagItemViewHolder extends RecyclerView.ViewHolder implements TagItemViewHolderView {
    @BindView(R.id.tvTagItemValue)
    TextView tvTagItemValue;

    private TagItemPresenter mPresenter;

    public TagItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindPresenter(TagItemPresenter presenter) {
        this.mPresenter = presenter;
        mPresenter.bindView(this);
    }

    public void unbindPresenter() {
        mPresenter = null;
    }

    //region Interface TagItemViewHolderView
    @Override
    public void setTagItemValue(String tagItemValue) {
        tvTagItemValue.setText(tagItemValue);
    }
    //endregion
}
