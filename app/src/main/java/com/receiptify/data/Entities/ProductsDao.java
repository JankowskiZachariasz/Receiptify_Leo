package com.receiptify.data.Entities;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ProductsDao {


    @Query("SELECT * from products")
    LiveData<List<Products>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Products word);

    @Query("DELETE FROM products")
    void deleteAll();

    @Delete
    void delete(Products word);
}
