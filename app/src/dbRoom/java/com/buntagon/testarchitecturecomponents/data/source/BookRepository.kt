package com.buntagon.testarchitecturecomponents.data.source

import android.arch.lifecycle.LiveData
import com.buntagon.testarchitecturecomponents.MyApplication
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.data.model.BookDao
import com.buntagon.testarchitecturecomponents.data.source.remote.BookApi
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

    init {
        getAll().observeForever { books ->
            if (books != null && books.size == 0) {
                seedData()
            }
        }
    }

    private fun seedData() {
        val book = Book()
        book.title = "A Game of Thrones"
        book.description = "The first epic book about people wanting to sit on a spiky chair"
        book.author = "George RR Martin"
        val book2 = Book()
        book2.title = "A Storm of Swords"
        book2.description = "The weather gets a little dangerous when it literally starts raining weapons"
        book2.author = "George RR Martin"
        val books = ArrayList<Book>()
        books.add(book)
        books.add(book2)
        insertOrUpdate(books)
    }

    override fun sync() {

    }

    override fun insertOrUpdate(item: Book) {
        item.lastUpdate = Date().time
        launch {
            bookDao.insert(item)
        }
    }

    override fun insertOrUpdate(items: List<Book>) {
        for (book in items) {
            book.lastUpdate = Date().time
        }
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


    override fun getAll(): StaticListLiveData<Book> = RoomLiveListData(bookDao.allBooks)


    override fun close() {
        // Empty implementation - no resources to clean up here
    }
}
