package com.buntagon.testarchitecturecomponents.data.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 *
 * Created by t on 30/11/2017.
 */

public class SyncedItemDiffCallback<T extends SyncedItem> extends DiffUtil.Callback {

    private List<T> oldItems;
    private List<T> newItems;

    public SyncedItemDiffCallback(List<T> oldItems, List<T> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).getId().equals(newItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).getLastUpdate() == newItems.get(newItemPosition).getLastUpdate();
    }
}
