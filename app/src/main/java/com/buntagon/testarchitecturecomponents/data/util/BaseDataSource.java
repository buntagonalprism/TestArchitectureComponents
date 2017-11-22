package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.realm.RealmModel;

/**
 * Common functions exposed by data sources for a data model class
 * Created by Alex on 19/11/2017.
 */

public interface BaseDataSource<T extends RealmModel & SyncedItem> {

    void insertOrUpdate(T item);

    void insertOrUpdate(List<T> items);

    void delete(String id);

    LiveData<T> get(String id);

    LiveData<List<T>> getAll();
}
