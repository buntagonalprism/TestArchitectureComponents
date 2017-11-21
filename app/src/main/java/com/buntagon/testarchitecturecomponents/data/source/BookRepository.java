package com.buntagon.testarchitecturecomponents.data.source;

import android.support.annotation.NonNull;

import com.buntagon.testarchitecturecomponents.data.model.Book;
import com.buntagon.testarchitecturecomponents.data.util.BaseCloseableDataSource;
import com.buntagon.testarchitecturecomponents.data.util.LiveRealmList;
import com.buntagon.testarchitecturecomponents.data.util.LiveRealmObject;
import com.buntagon.testarchitecturecomponents.data.util.StaticLiveData;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * External implementation
 * Created by t on 19/11/2017.
 */

public class BookRepository implements BookDataSource, BaseCloseableDataSource {

    private Realm mRealm;

    @Override
    public void sync() {

    }

    public BookRepository() {
        mRealm = Realm.getDefaultInstance();
//        mRealm.close();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Book book = new Book();
                book.setTitle("A Game of Thrones");
                book.setAuthor("George RR Martin");
                book.setId(UUID.randomUUID().toString());
                realm.copyToRealmOrUpdate(book);
            }
        });
    }

    @Override
    public void insertOrUpdate(final Book book) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(book);
            }
        });
    }

    @Override
    public void delete(final String id) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Book book = mRealm.where(Book.class).equalTo("id", id).findFirst();
                if (book != null) {
                    book.deleteFromRealm();
                }
            }
        });
    }

    @Override
    public StaticLiveData<Book> get(String id) {
        return new LiveRealmObject<>(mRealm.where(Book.class).equalTo("id", id).findFirst());
    }

    @Override
    public StaticLiveData<List<Book>> getAll() {
        return new LiveRealmList<>(mRealm.where(Book.class).findAll());
    }

    @Override
    public void close() {
        mRealm.close();
    }


}
