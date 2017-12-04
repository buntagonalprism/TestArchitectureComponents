package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.persistence.room.Relation;
import android.arch.persistence.room.Transaction;

import java.util.List;

/**
 * Created by t on 2/12/2017.
 */

public class AuthorWithBooks extends AuthorDetails {

    // This one to many relationship actually worked very well. By extending author details, this
    // POJO was able to pull out all of the fields from the AuthorDetails table and include them
    // in the author results, while also getting a list of all the books.

    // If we wanted just a slice of author details in this view, then instead of extending the class
    // we could instead specify a number of fields with the same name (although because the field
    // names must match that is less fun - the compiler will tell us about invalid field names if
    // we refactor, but if we add a new field to AuthorDetails then we need to remember to add it
    // here as well which would be a drag)
    @Relation(parentColumn = "id", entityColumn = "authorId")
    public List<BookDetails> books;

}
