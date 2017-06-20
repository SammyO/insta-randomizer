package com.oddhov.insta_randomizer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oddhov.insta_randomizer.R;
import com.oddhov.insta_randomizer.models.TagItem;
import com.oddhov.insta_randomizer.presenters.TagItemPresenter;
import com.oddhov.insta_randomizer.views.TagItemViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewAdapter extends RecyclerView.Adapter<TagItemViewHolder> {
    // Hold internal set of TagItems and their Presenters
    private final List<TagItem> mTagItems;
    private final Map<String, TagItemPresenter> mTagItemPresenters;

    //region Constructor
    public OverviewAdapter() {
        this.mTagItems = new ArrayList<>();
        this.mTagItemPresenters = new HashMap<>();
    }
    //endregion

    //region Setup Methods
    public void setupData(List<TagItem> tagItems) {
        mTagItems.clear();
        mTagItemPresenters.clear();

        for (TagItem tagItem : tagItems) {
            mTagItems.add(tagItem);
            mTagItemPresenters.put(tagItem.getId(), createTagItemPresenter(tagItem));
        }

        notifyDataSetChanged();
    }
    //endregion

    //region RecyclerView Interface
    @Override
    public void onViewRecycled(TagItemViewHolder holder) {
        super.onViewRecycled(holder);

        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(TagItemViewHolder holder) {
        // Sometimes, if animations are running on the itemView's children, the RecyclerView won't
        // be able to recycle the view. We should still unbind the presenter.
        holder.unbindPresenter();

        return super.onFailedToRecycleView(holder);
    }

    @Override
    public TagItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag_item, parent, false);

        return new TagItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagItemViewHolder holder, int position) {
        holder.bindPresenter(getTagItemPresenter(mTagItems.get(position)));
    }

    @Override
    public int getItemCount() {
        return mTagItems.size();
    }
    //endregion

    //region Helper Methods
    private TagItemPresenter createTagItemPresenter(TagItem tagItem) {
        TagItemPresenter tagItemPresenter = new TagItemPresenter();
        tagItemPresenter.setData(tagItem);
        return tagItemPresenter;
    }

    private TagItemPresenter getTagItemPresenter(TagItem tagItem) {
        Log.e("OverviewAdapter", "Getting presenter for item " + tagItem.getId());
        return mTagItemPresenters.get(tagItem.getId());
    }


}