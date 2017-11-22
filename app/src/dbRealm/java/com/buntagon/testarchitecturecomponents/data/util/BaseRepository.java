package com.buntagon.testarchitecturecomponents.data.util;

import android.support.annotation.NonNull;

import com.buntagon.testarchitecturecomponents.data.source.remote.RetrofitHelper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Add helper methods for repositories which use realm database backend
 * Created by Alex on 19/11/2017.
 */

abstract public class BaseRepository<T extends RealmObject & SyncedItem> implements BaseDataSource<T> {

    protected Realm mRealm;
    protected Retrofit mRetrofit;
    private int requestsInProgress = 0;
    private boolean isCloseRequested = false;


    protected BaseRepository() {
        mRealm = Realm.getDefaultInstance();
        mRetrofit = RetrofitHelper.getRetrofit();
    }

    @Override
    public void insertOrUpdate(final T item) {
        executeDbTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(item);
            }
        });
    }

    @Override
    public void insertOrUpdate(final List<T> items) {
        executeDbTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(items);
            }
        });
    }

    @Override
    public void delete(final String id) {
        executeDbTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                T item = get(realm, id);
                if (item != null)
                    item.deleteFromRealm();
            }
        });
    }

    protected abstract T get(Realm realm, String id);

    protected abstract List<T> getAll(Realm realm);

    /**
     * Call a Retrofit API endpoint while managing the repository lifecycle
     * @param call Api call to execute
     * @param callback Callback to run on completion
     * @param <RESP> Data return type expected from the api call
     */
    protected <RESP> void  callApi(Call<RESP> call, final Callback<RESP> callback) {
        requestsInProgress++;
        call.enqueue(new Callback<RESP>() {
            @Override
            public void onResponse(@NonNull Call<RESP> call, @NonNull Response<RESP> response) {
                requestsInProgress--;
                callback.onResponse(call, response);
                checkClose();
            }

            @Override
            public void onFailure(@NonNull Call<RESP> call, @NonNull Throwable t) {
                requestsInProgress--;
                callback.onFailure(call, t);
                checkClose();
            }
        });
    }

    /**
     * Execute an asynchronous database transaction while managing repository lifecycle
     * @param transaction Transaction to execute
     */
    protected void executeDbTransactionAsync(Realm.Transaction transaction) {
        mRealm.executeTransactionAsync(transaction,
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                checkClose();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                checkClose();
            }
        });
    }

    private void checkClose() {
        if (isCloseRequested && requestsInProgress == 0 && !mRealm.isInTransaction()) {
            mRealm.close();
        }
    }

    public void close() {
        isCloseRequested = true;
        checkClose();
    }
}
