package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.data.source.BookRepository
import com.buntagon.testarchitecturecomponents.data.util.StaticListLiveData
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData
import com.buntagon.testarchitecturecomponents.util.SingleLiveEvent

/**
 * View model for books list view
 * Created by t on 19/11/2017.
 */
class LibraryViewModel : ViewModel() {

    private val mRepository = BookRepository()

    val books : StaticListLiveData<Book> = mRepository.getAll()

    val saveCompleteEvent = SingleLiveEvent<Void>()
    val editEvent = SingleLiveEvent<Void>()
    val createEvent = SingleLiveEvent<Void>()

    private var selectedBook : LiveData<Book> = MutableLiveData<Book>()

    fun getSelected() : LiveData<Book> {
        return selectedBook
    }
    fun editBook(id: String) {
        selectedBook = mRepository[id].editable
        editEvent.call()
    }

    fun createBook() {
        selectedBook = MutableLiveData<Book>()
        createEvent.call()
    }

    fun delete(book: Book) {
        mRepository.delete(book)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.close()
    }

    fun saveBook(book: Book) {
        mRepository.insertOrUpdate(book)
        saveCompleteEvent.call()
        selectedBook = MutableLiveData<Book>()
    }


}