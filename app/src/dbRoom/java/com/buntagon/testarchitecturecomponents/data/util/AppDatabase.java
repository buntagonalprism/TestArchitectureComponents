package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.buntagon.testarchitecturecomponents.data.model.AuthorDao;
import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails;
import com.buntagon.testarchitecturecomponents.data.model.BookDetails;
import com.buntagon.testarchitecturecomponents.data.model.BookDao;

/**
 *
 * Created by t on 25/11/2017.
 */
@Database(entities = {BookDetails.class, AuthorDetails.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
    public abstract AuthorDao authorDao();
}
