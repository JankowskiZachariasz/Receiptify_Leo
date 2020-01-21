package com.receiptify.data.Entities;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CompaniesDao {


    @Query("SELECT * from companies")
    LiveData<List<Companies>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Companies word);

    @Query("DELETE FROM companies")
    void deleteAll();

    @Delete
    void delete(Companies word);
}
