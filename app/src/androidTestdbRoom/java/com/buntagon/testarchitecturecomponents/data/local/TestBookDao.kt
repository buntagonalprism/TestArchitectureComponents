package com.buntagon.testarchitecturecomponents.data.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.data.util.AppDatabase

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by Alex on 4/12/2017.
 */
@RunWith(AndroidJUnit4::class)
class TestBookDao {

    private var mDatabase: AppDatabase? = null

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java).build()
        seedData()
    }

    @After
    fun closeDb() {
        mDatabase!!.close()
    }

    private fun seedData() {


        val author1 = AuthorDetails()
        author1.name = "George RR Martin"
        author1.age = 26
        val authors = ArrayList<AuthorDetails>()
        authors.add(author1)

        mDatabase!!.authorDao().insertAll(authors)

        val book = BookDetails()
        book.title = "A Game of Thrones"
        book.description = "The first epic book about people wanting to sit on a spiky chair"
        book.author = author1.name
        book.authorId = author1.id
        val book2 = BookDetails()
        book2.title = "A Storm of Swords"
        book2.description = "The weather gets a little dangerous when it literally starts raining weapons"
        book2.author = author1.name
        book2.authorId = author1.id
        val books = ArrayList<BookDetails>()
        books.add(book)
        books.add(book2)
        mDatabase!!.bookDao().insertAll(books)

    }

    @Test
    fun checkBooksLoaded() {

        val books = mDatabase!!.bookDao().allBooks.blockingObserver()
        Assert.assertNotEquals(books?.size, 0)

    }

    @Test
    fun loadBookWithAuthor() {
        val bookDao = mDatabase!!.bookDao()
        val books = bookDao.allBooks.blockingObserver()
        Assert.assertNotEquals(books?.size, 0)
        books?.let {
            val book = books[0]
            val bookWithAuthor = bookDao.getBookWithAuthor(book.id).blockingObserver()
            Assert.assertEquals(book.author, bookWithAuthor?.authorName2)
        }
    }

    @Test
    fun loadAuthorWithBooks() {
        val authorDoa = mDatabase!!.authorDao()
        val authors = authorDoa.allAuthorDetails.blockingObserver()
        authors?.let {
            Assert.assertNotEquals(authors.size, 0)
            val author = authors[0]
            val authorWithBooks = authorDoa.getAuthor(author.id).blockingObserver()
            Assert.assertEquals(authorWithBooks?.books?.size, 2)

        }
    }

    /**
     * An extension to LiveData that allows synchronous retrieval of the contents for the purposes
     * of running unit tests. Should never be used in production code as it puts main thread blocks
     * in to wait for database operations to complete before continuing
     */
    private fun <T> LiveData<T>.blockingObserver(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}
