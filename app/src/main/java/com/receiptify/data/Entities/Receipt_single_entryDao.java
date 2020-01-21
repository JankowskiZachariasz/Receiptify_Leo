
package com.receiptify.data.Entities;


        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;
        import java.util.List;

@Dao
public interface Receipt_single_entryDao {


    @Query("SELECT * from receipts_single_entry")
    LiveData<List<Receipt_single_entry>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Receipt_single_entry word);

    @Query("DELETE FROM receipts_single_entry")
    void deleteAll();

    @Delete
    void delete(Receipt_single_entry word);
}
