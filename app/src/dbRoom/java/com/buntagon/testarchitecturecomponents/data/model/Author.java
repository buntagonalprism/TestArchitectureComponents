package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by t on 2/12/2017.
 */

public class Author extends AuthorDetails {



    @Relation(parentColumn = "id", entityColumn = "authorId")
    public List<Book> books;

}
