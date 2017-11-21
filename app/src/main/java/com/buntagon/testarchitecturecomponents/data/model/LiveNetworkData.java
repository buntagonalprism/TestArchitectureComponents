package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.lifecycle.LiveData;

/**
 * Potentially used for revealing network failures to the UI
 * Created by t on 19/11/2017.
 */

public class LiveNetworkData<T> extends LiveData<T> {

    private String id;
    private long lastUpdate;
    private boolean isSynced;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
