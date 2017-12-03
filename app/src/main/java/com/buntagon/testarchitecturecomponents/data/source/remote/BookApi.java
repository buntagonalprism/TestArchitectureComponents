package com.buntagon.testarchitecturecomponents.data.source.remote;

import com.buntagon.testarchitecturecomponents.data.model.BookDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Api for accessing books
 * Created by Alex on 20/11/2017.
 */

public interface BookApi {

    @GET("books")
    Call<List<BookDetails>> listBooks();

}
