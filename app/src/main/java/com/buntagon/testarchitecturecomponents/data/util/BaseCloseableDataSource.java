package com.buntagon.testarchitecturecomponents.data.util;

/**
 * Data sources should extend this to indicate that they can be closed
 * Created by t on 19/11/2017.
 */

public interface BaseCloseableDataSource {

    void close();

}
