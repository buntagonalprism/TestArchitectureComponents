package com.buntagon.testarchitecturecomponents

import android.app.Application
import android.arch.persistence.room.Room
import com.buntagon.testarchitecturecomponents.data.util.AppDatabase


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
                AppDatabase::class.java, "database-name").build()
    }

}