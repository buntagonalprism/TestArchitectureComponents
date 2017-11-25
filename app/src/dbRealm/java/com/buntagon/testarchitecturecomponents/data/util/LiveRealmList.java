package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.annotation.Nullable;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Class connecting the Realm lifecycle to that of LiveData objects.
 */
public class LiveRealmList<T extends RealmModel> extends StaticListLiveData<T> {

    private RealmResults<T> results;
    private OrderedCollectionChangeSet changeSet = null;
//    private final RealmChangeListener<RealmResults<T>> listener = new RealmChangeListener<RealmResults<T>>() {
//        @Override
//        public void onChange(@NonNull RealmResults<T> results) {
//            setValue(results);
//        }
//
//    };
    private final OrderedRealmCollectionChangeListener<RealmResults<T>> listener = new OrderedRealmCollectionChangeListener<RealmResults<T>>() {
        @Override
        public void onChange(@NonNull RealmResults<T> newResults, @Nullable OrderedCollectionChangeSet newChangeSet) {
            changeSet = newChangeSet;
            setValue(newResults);
        }
    };



    public LiveRealmList(RealmResults<T> realmResults) {
        results = realmResults;
        setValue(results);
    }

    @Override
    public void applyChangesToAdapter(RecyclerView.Adapter adapter) {
        // `null`  means the async query returns the first time.
        if (changeSet == null) {
            adapter.notifyDataSetChanged();
            return;
        }
        // For deletions, the adapter has to be notified in reverse order.
        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
            OrderedCollectionChangeSet.Range range = deletions[i];
            adapter.notifyItemRangeRemoved(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
            adapter.notifyItemRangeInserted(range.startIndex, range.length);
        }

        // Made need custom comparator to detect if changes already made
        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
            adapter.notifyItemRangeChanged(range.startIndex, range.length);
        }
    }

    @Override
    public MutableLiveData<List<T>> getEditable() {
        return new MutableRealmList<>(results);
    }

    @Override
    protected void onActive() {
        results.addChangeListener(listener);
    }

    @Override
    protected void onInactive() {
        results.removeChangeListener(listener);
    }

}