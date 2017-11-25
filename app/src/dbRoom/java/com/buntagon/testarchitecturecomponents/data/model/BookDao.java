package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Interface data access object for working with Book objects
 * Created by t on 25/11/2017.
 */
@Dao
public interface BookDao {
    @Query("select * from Book")
    LiveData<List<Book>> getAllBooks();

    @Query("select * from Book where id = :id")
    LiveData<Book> getBook(String id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Insert
    void insertAll(List<Book> books);

    @Delete
    void delete(Book book);
}