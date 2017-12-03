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
public interface BookDao {
    @Query("select * from BookDetails order by title")
    LiveData<List<BookDetails>> getAllBooks();

    @Query("select * from BookDetails where id = :id")
    LiveData<BookDetails> getBook(String id);

    @Query("select BookDetails.*, AuthorDetails.id as authorId2, AuthorDetails.name as authorName2 from BookDetails join AuthorDetails on BookDetails.authorId = AuthorDetails.id where BookDetails.id = :id ")
    LiveData<Book> getBookWithAuthor(String id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(BookDetails book);

    @Insert
    void insertAll(List<BookDetails> books);

    @Delete
    void delete(BookDetails book);

    @Query("select * from BookDetails where title like :title")
    LiveData<List<BookDetails>> searchBooks(String title);


}