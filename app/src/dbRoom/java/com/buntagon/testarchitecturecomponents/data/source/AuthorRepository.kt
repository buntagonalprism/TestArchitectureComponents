package com.buntagon.testarchitecturecomponents.data.source

import android.arch.lifecycle.LiveData
import com.buntagon.testarchitecturecomponents.MyApplication
import com.buntagon.testarchitecturecomponents.data.model.AuthorDao
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.AuthorWithBooks
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

class AuthorRepository : AuthorDataSource {

    private val authorDao: AuthorDao = MyApplication.db!!.authorDao()



    override fun insertOrUpdate(item: AuthorDetails) {
        item.lastUpdate = Date().time
        launch {
            authorDao.insert(item)
        }
    }

    override fun insertOrUpdate(items: List<AuthorDetails>) {
        for (book in items) {
            book.lastUpdate = Date().time
        }
        launch {
            authorDao.insertAll(items)
        }
    }

    override fun delete(item: AuthorDetails) {
        launch {
            authorDao.delete(item)
        }
    }

    override fun get(id: String): StaticLiveData<AuthorDetails> {
        val author : LiveData<AuthorDetails> = authorDao.getAuthorDetails(id)
        return RoomLiveData(author)
    }


    override fun getAll(): StaticListLiveData<AuthorDetails> {
        return RoomLiveListData<AuthorDetails>(authorDao.allAuthorDetails)
    }

    override fun getAllAuthorNames(): LiveData<List<String>> {
        return authorDao.allAuthorNames()
    }

    fun getAuthorWithBooks(id: String) : StaticLiveData<AuthorWithBooks> {
        return RoomLiveData(authorDao.getAuthor(id))
    }

    override fun searchAuthorNames(likeName: String?): LiveData<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        // Empty implementation - no resources to clean up here
    }
}
