package com.buntagon.testarchitecturecomponents.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.buntagon.testarchitecturecomponents.data.model.Book;
import com.buntagon.testarchitecturecomponents.data.model.BookFields;
import com.buntagon.testarchitecturecomponents.data.source.remote.BookApi;
import com.buntagon.testarchitecturecomponents.data.util.BaseRepository;
import com.buntagon.testarchitecturecomponents.data.util.LiveRealmList;
import com.buntagon.testarchitecturecomponents.data.util.LiveRealmObject;
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * External implementation
 * Created by t on 19/11/2017.
 */

public class BookRepository extends BaseRepository<Book> implements BookDataSource {

    private BookApi bookApi;

    public BookRepository() {
        bookApi = mRetrofit.create(BookApi.class);
        if (getAll().getValue() != null && getAll().getValue().size() == 0) {
            seedData();
        }
    }

    private void seedData() {
        Book book = new Book();
        book.setTitle("A Game of Thrones");
        book.setDescription("The first epic book about people wanting to sit on a spiky chair");
        book.setAuthor("George RR Martin");
        Book book2 = new Book();
        book2.setTitle("A Storm of Swords");
        book2.setDescription("The weather gets a little dangerous when it literally starts raining weapons");
        book2.setAuthor("George RR Martin");
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        insertOrUpdate(books);
    }


    @Override
    public void sync() {
        throw new UnsupportedOperationException("Unimplemented method");
    }

    @Override
    protected Book get(Realm realm, String id) {
        return realm.where(Book.class).equalTo(BookFields.ID, id).findFirst();
    }

    @Override
    protected RealmResults<Book> getAll(Realm realm) {
        return realm.where(Book.class).findAll();
    }

    @Override
    public StaticLiveData<Book> get(String id) {
        refreshBooks();
        return new LiveRealmObject<>(get(mRealm, id));
    }

    @Override
    public StaticLiveData<List<Book>> getAll() {
        refreshBooks();
        return new LiveRealmList<>(getAll(mRealm));
    }


    private void refreshBooks() {
        callApi(bookApi.listBooks(), new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                insertOrUpdate(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                // TODO: error handling?
                Log.e("TEST_ERR", t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }


}
