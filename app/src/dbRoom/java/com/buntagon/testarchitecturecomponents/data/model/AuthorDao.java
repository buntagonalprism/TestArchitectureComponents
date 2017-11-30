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
public interface AuthorDao {
    @Query("select * from Author")
    LiveData<List<Author>> getAllAuthors();

    @Query("select * from Author where id = :id")
    LiveData<Author> getAuthor(String id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Author author);

    @Insert
    void insertAll(List<Author> authors);

    @Delete
    void delete(Author author);

    @Query("select * from Author where name like :authorName")
    LiveData<List<Author>> searchAuthors(String authorName);
}