package com.buntagon.testarchitecturecomponents

import android.app.Application
import io.realm.Realm

/**
 * Custom application to allow initialisation methods
 * Created by t on 17/11/2017.
 */
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}