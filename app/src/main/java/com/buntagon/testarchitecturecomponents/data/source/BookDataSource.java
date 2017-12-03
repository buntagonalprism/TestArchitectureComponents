package com.buntagon.testarchitecturecomponents.data.source;

import com.buntagon.testarchitecturecomponents.data.model.BookDetails;
import com.buntagon.testarchitecturecomponents.data.util.BaseDataSource;

/**
 * Interface for data sources which expose operations on BookDetails objects
 * Created by t on 19/11/2017.
 */

public interface BookDataSource extends BaseDataSource<BookDetails> {

    void sync();

}
