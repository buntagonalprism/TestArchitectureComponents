package com.buntagon.testarchitecturecomponents.data.model;

import android.arch.persistence.room.Embedded;

/**
 * Created by Alex on 3/12/2017.
 */

public class Book {

    // The Embedded annotation puts all of the fields of BookDetails automatically as columns in
    // our SQLite equivalent query. This means however that we cannot embed both BookDetails and
    // AuthorDetails completely, as their default field names will have clashes on common column
    // names like "id". The workaround is to only select the columns we actually need from both
    // tables, and if there are still any conflicts, rename them with "select col as col2" in the
    // SQL query in our DAO

    // The principle with one-to-one relationships here is not to get nested objects but to
    // basically write a view on top of our data which joins two tables together.
    // Note that the relationship author->book is actually many->one, but from the books perspective
    // therefore it is one to one, hence treating it as such here
    @Embedded
    public BookDetails bookDetails;

    public String authorName2;
    public String authorId2;
}
