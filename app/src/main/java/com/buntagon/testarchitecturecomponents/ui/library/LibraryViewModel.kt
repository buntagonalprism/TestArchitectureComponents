package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.data.source.AuthorRepository
import com.buntagon.testarchitecturecomponents.data.source.BookRepository
import com.buntagon.testarchitecturecomponents.data.util.StaticListLiveData
import com.buntagon.testarchitecturecomponents.util.SingleLiveEvent

/**
 * View model for books list view
 * Created by t on 19/11/2017.
 */
class LibraryViewModel : ViewModel() {

    private val mRepository = BookRepository()
    private val mAuthorRepo = AuthorRepository()

    val authors = mAuthorRepo.getAll()
    val authorNames = mAuthorRepo.allAuthorNames
    val books : StaticListLiveData<BookDetails> = mRepository.getAll()

    val saveCompleteEvent = SingleLiveEvent<Void>()
    val editEvent = SingleLiveEvent<Void>()
    val createEvent = SingleLiveEvent<Void>()

    private var selectedBook : LiveData<BookDetails> = MutableLiveData<BookDetails>()

    fun getSelected() : LiveData<BookDetails> {
        return selectedBook
    }
    fun editBook(id: String) {
        selectedBook = mRepository[id].editable
        editEvent.call()
    }

    fun createBook() {
        selectedBook = MutableLiveData<BookDetails>()
        createEvent.call()
    }

    fun delete(book: BookDetails) {
        mRepository.delete(book)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.close()
    }

    fun saveBook(book: BookDetails) {
        mRepository.insertOrUpdate(book)
        saveCompleteEvent.call()
        selectedBook = MutableLiveData<BookDetails>()
    }


}