package com.buntagon.testarchitecturecomponents.ui.library

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.Book
import com.buntagon.testarchitecturecomponents.data.source.BookRepository
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData

/**
 * View model for books list view
 * Created by t on 19/11/2017.
 */
class LibraryViewModel : ViewModel() {

    private val mRepository = BookRepository()

    val books : StaticLiveData<MutableList<Book>> = mRepository.all

    private var selectedBook = MutableLiveData<Book>()

    fun getSelected() : MutableLiveData<Book> {
        return selectedBook
    }
    fun setSelected(id: String) {
        selectedBook = mRepository.get(id).editable
    }


    override fun onCleared() {
        super.onCleared()
        mRepository.close()
    }

    fun saveBook(book: Book) {
        mRepository.insertOrUpdate(book)
    }
}