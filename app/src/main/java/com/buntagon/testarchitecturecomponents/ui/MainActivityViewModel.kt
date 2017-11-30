package com.buntagon.testarchitecturecomponents.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Common
 * Created by t on 30/11/2017.
 */
class MainActivityViewModel : ViewModel() {

    val activityTitle: MutableLiveData<String> = MutableLiveData()

}