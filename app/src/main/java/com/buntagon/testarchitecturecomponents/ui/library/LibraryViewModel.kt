package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.BookDetails
import com.buntagon.testarchitecturecomponents.data.model.BookWithAuthor
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
    val books : StaticListLiveData<BookWithAuthor> = mRepository.getBooksWithAuthors()

    val saveCompleteEvent = SingleLiveEvent<Void>()
    val editEvent = SingleLiveEvent<Void>()
    val createEvent = SingleLiveEvent<Void>()

    private var selectedBook : LiveData<BookWithAuthor> = MutableLiveData<BookWithAuthor>()

    fun getSelected() : LiveData<BookWithAuthor> {
        return selectedBook
    }
    fun editBook(id: String) {
        selectedBook = mRepository.getBookWithAuthor(id).editable
        editEvent.call()
    }

    fun createBook() {
        selectedBook = MutableLiveData<BookWithAuthor>()
        createEvent.call()
    }

    fun delete(book: BookDetails) {
        mRepository.delete(book)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.close()
    }

    fun saveBook(book: BookWithAuthor) {
        mRepository.insertOrUpdate(book)
        saveCompleteEvent.call()
        selectedBook = MutableLiveData<BookWithAuthor>()
    }


}