package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.buntagon.testarchitecturecomponents.data.model.Book;
import com.buntagon.testarchitecturecomponents.data.model.BookDao;

/**
 *
 * Created by t on 25/11/2017.
 */
@Database(entities = {Book.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
