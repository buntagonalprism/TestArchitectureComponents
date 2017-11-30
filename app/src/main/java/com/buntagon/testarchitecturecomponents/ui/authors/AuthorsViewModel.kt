package com.buntagon.testarchitecturecomponents.ui.authors

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.buntagon.testarchitecturecomponents.data.model.Author

/**
 * View model for the authors fragment
 * Created by Alex on 29/11/2017.
 */
class AuthorsViewModel : ViewModel() {

    val authors : LiveData<MutableList<Author>>

    init {
        val authorsList = ArrayList<Author>()
        val author1 = Author()
        author1.name = "J.K. Rowling"
        author1.age = 36
        authorsList.add(author1)
        val author2 = Author()
        author2.name = "Eoin Colfter"
        author2.age = 28
        authorsList.add(author2)
        authors = MutableLiveData<MutableList<Author>>()
        authors.value = authorsList
    }

}