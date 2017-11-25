package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Thin wrapper around a LiveData set generated from Room
 * Created by t on 25/11/2017.
 */

public class RoomLiveData<T> extends StaticLiveData<T> {

    private LiveData<T> data;

    public RoomLiveData(LiveData<T> dataSet) {
        data = dataSet;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        data.observe(owner, observer);
    }

    @Nullable
    @Override
    public T getValue() {
        return data.getValue();
    }

    @Override
    public LiveData<T> getEditable() {
        return data;
    }
}
