package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmObjectChangeListener;

/**
 * Class connecting the Realm lifecycle to that of LiveData objects.
 */
public class LiveRealmObject<T extends RealmModel> extends StaticLiveData<T> {

    private T results;
    private final RealmObjectChangeListener<T> listener = new RealmObjectChangeListener<T>() {
        @Override
        public void onChange(@NonNull T results, ObjectChangeSet changeSet) {
            setValue(results);
        }
    };

    public LiveRealmObject(T realmResults) {
        results = realmResults;
    }

    @Override
    public MutableLiveData<T> getEditable() {
        return new MutableRealmObject<>(results);
    }

    @Override
    protected void onActive() {
        RealmObject.addChangeListener(results, listener);
    }

    @Override
    protected void onInactive() {
        RealmObject.removeChangeListener(results, listener);
    }

}