package com.buntagon.testarchitecturecomponents.data.model

import com.buntagon.testarchitecturecomponents.data.util.SyncedItem
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Model for storing book data
 * Created by Alex on 17/11/2017.
 */
open class Book : RealmObject(), SyncedItem {

    var title : String = ""
    var author : String = ""
    var published = Date()
    var wordCount : Int = -1
    var price : Float = -1.0f
    var description : String = ""

    @PrimaryKey
    private var id: String = ""
    private var lastUpdate: Long = -1
    private var isSynced: Boolean = false

    override fun getId(): String {
        return id
    }

    override fun setId(id: String) {
        this.id = id
    }

    override fun getLastUpdate(): Long {
        return lastUpdate
    }

    override fun setLastUpdate(lastUpdate: Long) {
        this.lastUpdate = lastUpdate
    }

    override fun isSynced(): Boolean {
        return isSynced
    }

    override fun setSynced(synced: Boolean) {
        isSynced = synced
    }
}