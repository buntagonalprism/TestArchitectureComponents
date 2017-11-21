package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Class connecting the Realm lifecycle to that of LiveData objects.
 */
public class MutableRealmList<T extends RealmModel> extends MutableLiveData<List<T>> {

    private RealmResults<T> results;
    private final RealmChangeListener<RealmResults<T>> listener = new RealmChangeListener<RealmResults<T>>() {
        @Override
        public void onChange(@NonNull RealmResults<T> results) { setValue(results);}
    };

    MutableRealmList(RealmResults<T> realmResults) {
        results = realmResults;
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