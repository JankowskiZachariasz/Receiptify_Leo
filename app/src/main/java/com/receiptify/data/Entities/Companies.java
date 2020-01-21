package com.receiptify.data.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "companies")
public class Companies {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @Nullable
    @ColumnInfo(name = "name")
    private String name;

    public Companies(@NonNull String id, String name) {
        this.id = id;
        this.name=name;

    }

    @NonNull
    public String getId() {
        return this.id;
    }
    @NonNull
    public String getName() {
        return this.name;
    }

}
