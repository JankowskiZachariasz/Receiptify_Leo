package com.receiptify.data.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "receipts")
public class Receipts {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @Nullable
    @ColumnInfo(name = "date")
    private String date;
    @Nullable
    @ColumnInfo(name = "company")
    private String company;
    @Nullable
    @ColumnInfo(name = "photo_name")
    private String photo_name;

    public Receipts(@NonNull String id, String date, String company, String photo_name) {
        this.id = id;
        this.date=date;
        this.company=company;
        this.photo_name=photo_name;
    }

    @NonNull
    public String getId() {
        return this.id;
    }
    @NonNull
    public String getDate() {
        return this.date;
    }
    @NonNull
    public String getCompany() {
        return this.company;
    }
    @NonNull
    public String getPhoto_name() {
        return this.photo_name;
    }
}
