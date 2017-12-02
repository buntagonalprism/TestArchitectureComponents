package com.buntagon.testarchitecturecomponents.data.source;

import android.arch.lifecycle.LiveData;

import com.buntagon.testarchitecturecomponents.data.model.AuthorDetails;
import com.buntagon.testarchitecturecomponents.data.util.BaseDataSource;
import com.buntagon.testarchitecturecomponents.data.util.StaticListLiveData;

import java.util.List;

/**
 * Data source for authors
 * Created by Alex on 29/11/2017.
 */

public interface AuthorDataSource extends BaseDataSource<AuthorDetails> {

    LiveData<List<String>> getAllAuthorNames();

    LiveData<List<String>> searchAuthorNames(String likeName);

}
