package com.jemylibs.data.seimpl.helpers;


import com.jemylibs.sedb.RowTypes.CreationTime;
import com.jemylibs.sedb.RowTypes.LastModified;

import java.time.LocalDateTime;

public class ZItem implements ZNamedRow, LastModified, CreationTime {
    private int id;
    private String name;
    private LocalDateTime lastModified;
    private LocalDateTime creationDateTime;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public String toString() {
        return getName();
    }
}
