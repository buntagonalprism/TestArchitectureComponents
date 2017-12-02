package com.buntagon.testarchitecturecomponents.ui.authors

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.data.source.AuthorRepository
import com.buntagon.testarchitecturecomponents.util.SingleLiveEvent

/**
 * View model for the authors fragment
 * Created by Alex on 29/11/2017.
 */
class AuthorsViewModel : ViewModel() {
    private val mAuthorRepo = AuthorRepository()

    val authors = mAuthorRepo.getAll()

    val saveCompleteEvent = SingleLiveEvent<Void>()
    val editEvent = SingleLiveEvent<Void>()
    val createEvent = SingleLiveEvent<Void>()

    private var selectedAuthor: LiveData<AuthorDetails> = MutableLiveData<AuthorDetails>()

    fun getSelected() : LiveData<AuthorDetails> {
        return selectedAuthor
    }
    fun editAuthor(id: String) {
        selectedAuthor = mAuthorRepo[id].editable
        editEvent.call()
    }

    fun createAuthor() {
        selectedAuthor = MutableLiveData<AuthorDetails>()
        createEvent.call()
    }

    fun delete(authorDetails: AuthorDetails) {
        mAuthorRepo.delete(authorDetails)
    }

    override fun onCleared() {
        super.onCleared()
        mAuthorRepo.close()
    }

    fun saveAuthor(authorDetails: AuthorDetails) {
        mAuthorRepo.insertOrUpdate(authorDetails)
        saveCompleteEvent.call()
        selectedAuthor = MutableLiveData<AuthorDetails>()
    }
}