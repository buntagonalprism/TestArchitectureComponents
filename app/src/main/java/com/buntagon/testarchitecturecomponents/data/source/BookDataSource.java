package com.buntagon.testarchitecturecomponents.data.source;

import com.buntagon.testarchitecturecomponents.data.model.Book;
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData;

import java.util.List;

/**
 * Interface for data sources which expose operations on Book objects
 * Created by t on 19/11/2017.
 */

public interface BookDataSource {

    void sync();

    void insertOrUpdate(Book book);

    void delete(String id);

    StaticLiveData<Book> get(String id);

    StaticLiveData<List<Book>> getAll();

}
