package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Thin wrapper around a LiveData set generated from Room
 * Created by t on 25/11/2017.
 */

public class RoomLiveListData<T extends SyncedItem> extends StaticListLiveData<T> {

    private DiffUtil.DiffResult diffResult;
    private List<T> oldData;
    private LiveData<List<T>> data;

    public RoomLiveListData(LiveData<List<T>> dataSet) {
        data = dataSet;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, final @NonNull Observer<List<T>> observer) {
        data.observe(owner, new Observer<List<T>>() {
            @Override
            public void onChanged(@Nullable List<T> ts) {
                if (oldData != null) {
                    // Compute the diff between
                    diffResult = DiffUtil.calculateDiff(new SyncedItemDiffCallback<>(oldData, ts));
                }
                oldData = ts;
                observer.onChanged(ts);
            }
        });
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        data.removeObservers(owner);
    }

    @Override
    public LiveData<List<T>> getEditable() {
        return data;
    }

    @Override
    public void applyChangesToAdapter(RecyclerView.Adapter adapter) {
        if (diffResult != null) {
            diffResult.dispatchUpdatesTo(adapter);
        }
        else {
            adapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public List<T> getValue() {
        return data.getValue();
    }

}
