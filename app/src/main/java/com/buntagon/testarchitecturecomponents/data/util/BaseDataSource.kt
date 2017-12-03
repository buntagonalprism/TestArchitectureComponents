package com.buntagon.testarchitecturecomponents.data.util

/**
 * Common functions exposed by data sources for a data model class
 * Created by Alex on 19/11/2017.
 */

interface BaseDataSource<T : SyncedItem> {

    fun getAll(): StaticLiveData<MutableList<T>>

    fun insertOrUpdate(item: T)

    fun insertOrUpdate(items: List<T>)

    fun delete(item: T)

    operator fun get(id: String): StaticLiveData<T>

    fun close()
}
