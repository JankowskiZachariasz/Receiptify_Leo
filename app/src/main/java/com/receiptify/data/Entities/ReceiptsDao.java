package com.receiptify.data.Entities;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReceiptsDao {


    @Query("SELECT * from receipts")
    LiveData<List<Receipts>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Receipts word);

    @Query("DELETE FROM receipts")
    void deleteAll();

    @Delete
    void delete(Receipts word);
}
