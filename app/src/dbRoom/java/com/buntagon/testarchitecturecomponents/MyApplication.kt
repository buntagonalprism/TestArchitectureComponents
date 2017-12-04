package com.buntagon.testarchitecturecomponents

import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.data.util.AppDatabase
import kotlinx.coroutines.experimental.launch
import java.util.ArrayList


/**
 * Custom application to allow initialisation methods
 * Created by t on 17/11/2017.
 */
class MyApplication : Application() {

    companion object {
        var db : AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "database-name").fallbackToDestructiveMigration().build()


        shouldSeedData()

    }

    private val observer = Observer<List<BookDetails>> { books ->
        if (books?.size == 0) {
            seedData()
        }
        removeObserver()
    }

    private fun removeObserver() {
        MyApplication.db!!.bookDao().allBooks.removeObserver(observer)
    }

    private fun shouldSeedData() {
        MyApplication.db!!.bookDao().allBooks.observeForever(observer)
    }

    private fun seedData() {

        launch {
            val authorsList = ArrayList<AuthorDetails>()

            val author1 = AuthorDetails()
            author1.name = "George RR Martin"
            author1.age = 26
            authorsList.add(author1)

            val author2 = AuthorDetails()
            author2.name = "J.K. Rowling"
            author2.age = 36
            authorsList.add(author2)

            val author3 = AuthorDetails()
            author3.name = "Eoin Colfter"
            author3.age = 28
            authorsList.add(author3)

            MyApplication.db!!.authorDao().insertAll(authorsList)

            val book = BookDetails()
            book.title = "A Game of Thrones"
            book.description = "The first epic book about people wanting to sit on a spiky chair"
            book.authorId = author1.id
            val book2 = BookDetails()
            book2.title = "A Storm of Swords"
            book2.description = "The weather gets a little dangerous when it literally starts raining weapons"
            book2.authorId = author1.id
            val books = ArrayList<BookDetails>()
            books.add(book)
            books.add(book2)
            MyApplication.db!!.bookDao().insertAll(books)
        }

    }



}