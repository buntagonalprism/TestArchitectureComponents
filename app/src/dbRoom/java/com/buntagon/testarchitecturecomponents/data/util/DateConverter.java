package com.buntagon.testarchitecturecomponents.data.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by t on 25/11/2017.
 */

public class DateConverter {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

}
