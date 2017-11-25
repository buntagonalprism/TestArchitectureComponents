package com.buntagon.testarchitecturecomponents.data.source

import android.arch.lifecycle.LiveData
import com.buntagon.testarchitecturecomponents.MyApplication
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.data.model.BookDao
import com.buntagon.testarchitecturecomponents.data.util.RoomLiveData
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData
import kotlinx.coroutines.experimental.launch

/**
 * Repository using Room
 * Created by t on 25/11/2017.
 */

class BookRepository : BookDataSource {

    private val bookDao: BookDao = MyApplication.db!!.bookDao()

    override fun sync() {

    }

    override fun insertOrUpdate(item: Book) {
        launch {
            bookDao.insert(item)
        }
    }

    override fun insertOrUpdate(items: List<Book>) {
        launch {
            bookDao.insertAll(items)
        }

    }

    override fun delete(book: Book) {
        launch {
            bookDao.delete(book)
        }
    }

    override fun get(id: String): StaticLiveData<Book> {
        val book : LiveData<Book> = bookDao.getBook(id)
        return RoomLiveData(book)
    }


    override fun getAll(): StaticLiveData<MutableList<Book>> = RoomLiveData(bookDao.allBooks)


    override fun close() {
        // Empty implementation - no resources to clean up here
    }
}
