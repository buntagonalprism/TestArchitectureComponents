package com.buntagon.testarchitecturecomponents.data.util;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Wrapper around live data which should not be edited
 * Created by t on 21/11/2017.
 */

public abstract class StaticListLiveData<T> extends StaticLiveData<List<T>> {

    public abstract void applyChangesToAdapter(RecyclerView.Adapter adapter);

}
