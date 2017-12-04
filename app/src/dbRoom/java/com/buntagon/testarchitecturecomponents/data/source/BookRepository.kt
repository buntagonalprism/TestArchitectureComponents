package com.buntagon.testarchitecturecomponents.data.source

import android.arch.lifecycle.LiveData
import com.buntagon.testarchitecturecomponents.MyApplication
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.data.model.BookDao
import com.buntagon.testarchitecturecomponents.data.model.BookWithAuthor
import com.buntagon.testarchitecturecomponents.data.util.RoomLiveData
import com.buntagon.testarchitecturecomponents.data.util.RoomLiveListData
import com.buntagon.testarchitecturecomponents.data.util.StaticListLiveData
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * Repository using Room
 * Created by t on 25/11/2017.
 */

class BookRepository : BookDataSource {

    private val bookDao: BookDao = MyApplication.db!!.bookDao()



    override fun sync() {

    }

    override fun insertOrUpdate(item: BookDetails) {
        item.lastUpdate = Date().time
        launch {
            bookDao.insert(item)
        }
    }

    override fun insertOrUpdate(items: List<BookDetails>) {
        for (book in items) {
            book.lastUpdate = Date().time
        }
        launch {
            bookDao.insertAll(items)
        }
    }

    override fun delete(item: BookDetails) {
        launch {
            bookDao.delete(item)
        }
    }

    override fun get(id: String): StaticLiveData<BookDetails> {
        val book : LiveData<BookDetails> = bookDao.getBook(id)
        return RoomLiveData(book)
    }

    fun getBooksWithAuthors() : StaticListLiveData<BookWithAuthor> {
        return RoomLiveListData(bookDao.booksWithAuthors)
    }

    fun getBookWithAuthor(id: String): StaticLiveData<BookWithAuthor> {
        val book : LiveData<BookWithAuthor> = bookDao.getBookWithAuthor(id)
        return RoomLiveData(book)
    }


    override fun getAll(): StaticListLiveData<BookDetails> = RoomLiveListData(bookDao.allBooks)


    override fun close() {
        // Empty implementation - no resources to clean up here
    }
}
