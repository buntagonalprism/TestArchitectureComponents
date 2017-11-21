package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmObjectChangeListener;

/**
 * Class connecting the Realm lifecycle to that of LiveData objects.
 */
public class MutableRealmObject<T extends RealmModel> extends MutableLiveData<T> {

    private T results;
    private final RealmObjectChangeListener<T> listener = new RealmObjectChangeListener<T>() {
        @Override
        public void onChange(@NonNull T results, ObjectChangeSet changeSet) {
            Realm realm = Realm.getDefaultInstance();
            setValue(realm.copyFromRealm(results));
            realm.close();
        }
    };

    MutableRealmObject(T realmResults) {
        results = realmResults;
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