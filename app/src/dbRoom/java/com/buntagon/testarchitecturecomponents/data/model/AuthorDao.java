package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Interface data access object for working with BookDetails objects
 * Created by t on 25/11/2017.
 */
@Dao
public interface AuthorDao {
    @Query("select * from AuthorDetails")
    LiveData<List<AuthorDetails>> getAllAuthorDetails();

    @Query("select * from AuthorDetails where id = :id")
    LiveData<AuthorDetails> getAuthorDetails(String id);

    @Query("select * from AuthorDetails where id = :id")
    LiveData<Author> getAuthor(String id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(AuthorDetails author);

    @Insert
    void insertAll(List<AuthorDetails> authors);

    @Delete
    void delete(AuthorDetails author);

    @Query("select * from AuthorDetails where name like :authorName")
    LiveData<List<AuthorDetails>> searchAuthors(String authorName);

    @Query("select name from AuthorDetails")
    LiveData<List<String>> allAuthorNames();
}