package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Wrapper around live data which should not be edited
 * Created by t on 21/11/2017.
 */

public abstract class StaticLiveData<T> extends LiveData<T> {

    public abstract LiveData<T> getEditable();

}
