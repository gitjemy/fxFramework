package com.jemylibs.sedb.RowTypes;

import com.jemylibs.sedb.utility.JDateTime;

import java.time.LocalDateTime;

public interface LastModified {

    default String getLastModifiedDate() {
        return JDateTime.str_date(getLastModified());
    }

    default String getLastModifiedTime() {
        return JDateTime.str_time(getLastModified());
    }

    default String getLastModifiedDateTime() {
        return JDateTime.str_date_time(getLastModified());
    }

    LocalDateTime getLastModified();

    void setLastModified(LocalDateTime lastModified);
}
