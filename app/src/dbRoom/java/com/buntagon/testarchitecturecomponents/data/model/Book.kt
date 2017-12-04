package com.buntagon.testarchitecturecomponents.data.model

import android.arch.persistence.room.Embedded

/**
 * Created by Alex on 3/12/2017.
 */

class BookWithAuthor : BookDetails() {
    var authorName : String = ""
}



/* Embedded implementation - Book contains BookDetails as an object
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
*/

// An aside as well about a comment from the above. There should never actually be a true one to
// one relationship in a database. If it ever did exist, that would be an indication that there
// exist two tables that can be merged, since they directly related to each other. What we are
// dealing with here instead is the reverse direction of a one to many relationship.


/* Extension implementation - Book contains all fields of book details by class extension
public class Book extends BookDetails {
    // The advantage of this implementation is that the book details are not contained in a
    // seperate object that the UI needs to dive into, all the fields regarding the BookDetails
    // are now present in the Book class, with the addition of author information
    public String authorName2;
    public String authorId2;
}
*/
/* Extension + embedding implementation - an altogether cunning method that makes it look like
encapsulation is working when its actually just playing tricks with column names. Recall that the
'embedded' annotation means that Room will attempt to find all the properties of the embedded object
in the column names of our query to map over. So as long as our query includes the right columns
the mappiing will succeed. This does require us to think a little bit more about how we load our
data, but now that I've actually tried it I'm rather fond of it. Its annoying that we can't have
column name clashes in the result set but again this would only happen in the backwards of the
one to many case, so its fairly uncommon.

Advantages of this include Book keeping all properties of BookDetails, and making it look like
AuthorWithBooks is stored as a seperate object within the book rather than a set of fields, even though thats
really what we are doing with the Embedded annotation - it adds a bit of nice code clarity.
 */
//class Book : BookDetails() {
//    @Embedded
//    var authorDetails: Author2? = null
//}
//
//class Author2 {
//    var authorName2: String? = null
//    var authorId2: String? = null
//}

// Final thing to tackle is the many to many publisher-author relationship. Thats going to need a
// join table to be defined: AuthorPublishers with some foreign key fields and additional data
// fields describing the relationship for good measure. To query we will define two POJOs,
// AuthorWithPublishers and PublisherWithAuthors - these provide the lookups in both directions,
// the first extends AuthorDetails and includes a list of related publishers, and the second is the
// opposite. For a given use-case, we should only ever want a primary list of one class hence why
// this approach should work okay.

// From a cleanliness point of view though, I'm not sure how I'd go about representing this in
// Realm unless we do the encapsulation + embedding way so the UI expects it - just gotta make sure
// it doesn't use any of the other fields