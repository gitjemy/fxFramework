package com.jemylibs.sedb.RowTypes;

import com.jemylibs.sedb.utility.JDateTime;

import java.time.LocalDateTime;

public interface DateTime {

    default String getViewDate() {
        return JDateTime.str_date(getDateTime());
    }

    default String getViewTime() {
        return JDateTime.str_time(getDateTime());
    }

    default String getViewDateTime() {
        return JDateTime.str_date_time(getDateTime());
    }

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

}
