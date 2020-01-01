package com.jemylibs.sedb.RowTypes;

import com.jemylibs.sedb.utility.JDateTime;

import java.time.LocalDateTime;

public interface CreationTime {

    default String getViewCreationDate() {
        return JDateTime.str_date(getCreationDateTime());
    }

    default String getViewCreationTime() {
        return JDateTime.str_time(getCreationDateTime());
    }

    default String getViewCreationDateTime() {
        return JDateTime.str_date_time(getCreationDateTime());
    }

    LocalDateTime getCreationDateTime();

    void setCreationDateTime(LocalDateTime creationDateTime);

}
