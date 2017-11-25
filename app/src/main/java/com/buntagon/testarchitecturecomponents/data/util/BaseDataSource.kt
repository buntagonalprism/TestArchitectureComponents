package com.buntagon.testarchitecturecomponents.data.util

import com.buntagon.testarchitecturecomponents.data.model.Book

/**
 * Common functions exposed by data sources for a data model class
 * Created by Alex on 19/11/2017.
 */

interface BaseDataSource<T : SyncedItem> {

    fun getAll(): StaticLiveData<MutableList<T>>

    fun insertOrUpdate(item: T)

    fun insertOrUpdate(items: List<T>)

    fun delete(book: Book)

    operator fun get(id: String): StaticLiveData<T>

    fun close()
}
