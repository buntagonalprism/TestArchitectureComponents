package com.buntagon.testarchitecturecomponents.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.buntagon.testarchitecturecomponents.data.util.SyncedItem
import java.util.*

/**
 * Model for storing book data
 * Created by Alex on 17/11/2017.
 */
@Entity
open class Publisher : SyncedItem {

    var name: String = ""
    var address: String = ""
    var employeeCount: Int = 0
    var stockPrice : Float = 0.0f





    @PrimaryKey
    @NonNull
    private var id: String = UUID.randomUUID().toString()
    private var lastUpdate: Long = -1
    private var isSynced: Boolean = false

    override fun getId(): String = id

    override fun setId(id: String) {
        this.id = id
    }

    override fun getLastUpdate(): Long = lastUpdate

    override fun setLastUpdate(lastUpdate: Long) {
        this.lastUpdate = lastUpdate
    }

    override fun isSynced(): Boolean = isSynced

    override fun setSynced(synced: Boolean) {
        isSynced = synced
    }
}