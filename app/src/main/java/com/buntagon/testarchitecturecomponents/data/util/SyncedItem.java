package com.buntagon.testarchitecturecomponents.data.util;

/**
 * Base template for data synced with the server
 * Created by t on 19/11/2017.
 */

public interface SyncedItem {

    void setId(String id);
    String getId();
    void setSynced(boolean synced);
    boolean isSynced();
    void setLastUpdate(long lastUpdate);
    long getLastUpdate();


    /* JAVA
     * Below code contains a default implementation to confirm to this interface
     */
//     @PrimaryKey
//     private String id;
//     private long lastUpdate;
//     private boolean isSynced;
//
//     public String getId() {
//     return id;
//     }
//
//     public void setId(String id) {
//     this.id = id;
//     }
//
//     public long getLastUpdate() {
//     return lastUpdate;
//     }
//
//     public void setLastUpdate(long lastUpdate) {
//     this.lastUpdate = lastUpdate;
//     }
//
//     public boolean isSynced() {
//     return isSynced;
//     }
//
//     public void setSynced(boolean synced) {
//     isSynced = synced;
//     }


    /* KOTLIN
     * Below code contains a default implementation to confirm to this interface
     */

//    @PrimaryKey
//    private var id: String = ""
//    private var lastUpdate: Long = -1
//    private var isSynced: Boolean = false
//
//    override fun getId(): String {
//        return id
//    }
//
//    override fun setId(id: String) {
//        this.id = id
//    }
//
//    override fun getLastUpdate(): Long {
//        return lastUpdate
//    }
//
//    override fun setLastUpdate(lastUpdate: Long) {
//        this.lastUpdate = lastUpdate
//    }
//
//    override fun isSynced(): Boolean {
//        return isSynced
//    }
//
//    override fun setSynced(synced: Boolean) {
//        isSynced = synced
//    }


}
