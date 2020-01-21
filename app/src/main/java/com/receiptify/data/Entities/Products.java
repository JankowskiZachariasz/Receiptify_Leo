package com.receiptify.data.Entities;

import androidx.annotation.FloatRange;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "products")
public class Products {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @Nullable
    @ColumnInfo(name = "name")
    private String name;
    @Nullable
    @ColumnInfo(name = "aVG_price")
    private Float aVG_price;
    @Nullable
    @ColumnInfo(name = "aVG_price_mass")
    private Float aVG_price_mass;
    @Nullable
    @ColumnInfo(name = "fats")
    private Float fats;
    @Nullable
    @ColumnInfo(name = "carbohydrates")
    private Float carbohydrates;
    @Nullable
    @ColumnInfo(name = "calories")
    private Float calories;

    public Products(@NonNull String id, String name, Float aVG_price, Float aVG_price_mass, Float fats, Float carbohydrates, Float calories) {
        this.id = id;
        this.name = name;
        this.aVG_price=aVG_price;
        this. aVG_price_mass = aVG_price_mass;
        this.fats=fats;
        this.carbohydrates=carbohydrates;
        this.calories=calories;
    }

    @NonNull
    public String getId() {
        return this.id;
    }
    @NonNull
    public String getName() {
        return this.name;
    }
    @NonNull
    public Float getAVG_price() {
        return this.aVG_price;
    }
    @NonNull
    public Float getAVG_price_mass() {
        return this.aVG_price_mass;
    }
    @NonNull
    public Float getFats() {
        return this.fats;
    }
    @NonNull
    public Float getCarbohydrates() {
        return this.carbohydrates;
    }
    @NonNull
    public Float getCalories() {
        return this.fats;
    }

}
