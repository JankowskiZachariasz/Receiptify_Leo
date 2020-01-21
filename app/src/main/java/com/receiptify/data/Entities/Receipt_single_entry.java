package com.receiptify.data.Entities;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "receipts_single_entry", primaryKeys = {"id_receipt","id_product"})
public class Receipt_single_entry {


    @NonNull
    @ColumnInfo(name = "id_receipt")
    private String id_receipt;
    @NonNull
    @ColumnInfo(name = "id_product")
    private String id_product;
    @Nullable
    @ColumnInfo(name = "quantity")
    private Float quantity;
    @Nullable
    @ColumnInfo(name = "mass")
    private Float mass;

    public Receipt_single_entry(@NonNull String id_receipt, String id_product, Float quantity, Float mass) {
        this.id_receipt = id_receipt;
        this.id_product=id_product;
        this.quantity=quantity;
        this.mass=mass;
    }

    @NonNull
    public String getId_receipt() {
        return this.id_receipt;
    }
    @NonNull
    public String getId_product() {
        return this.id_product;
    }
    @NonNull
    public Float getQuantity() {
        return this.quantity;
    }
    @NonNull
    public Float getMass() {
        return this.mass;
    }
}
