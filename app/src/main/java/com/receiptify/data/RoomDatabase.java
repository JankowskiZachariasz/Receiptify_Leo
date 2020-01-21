package com.receiptify.data;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Context;

import com.receiptify.data.Entities.Companies;
import com.receiptify.data.Entities.CompaniesDao;
import com.receiptify.data.Entities.Products;
import com.receiptify.data.Entities.ProductsDao;
import com.receiptify.data.Entities.Receipt_single_entry;
import com.receiptify.data.Entities.Receipt_single_entryDao;
import com.receiptify.data.Entities.Receipts;
import com.receiptify.data.Entities.ReceiptsDao;
import com.receiptify.data.Entities.Word;
import com.receiptify.data.Entities.WordDao;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */

@Database(entities = {Word.class, Receipts.class, Receipt_single_entry.class, Products.class, Companies.class}, version = 3, exportSchema = true)
abstract class RoomDatabase extends androidx.room.RoomDatabase {

    abstract WordDao wordDao();
    abstract ReceiptsDao receiptsDao();
    abstract Receipt_single_entryDao receipt_single_entry();
    abstract ProductsDao products();
    abstract CompaniesDao companies();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "data.db")
                            //.addCallback(sRoomDatabaseCallback)
                            .createFromAsset("data.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao dao = INSTANCE.wordDao();
                ReceiptsDao rDao = INSTANCE.receiptsDao();

                Receipt_single_entryDao rseDao = INSTANCE.receipt_single_entry();
                ProductsDao pDao = INSTANCE.products();
                CompaniesDao cDao = INSTANCE.companies();

                dao.deleteAll();
                rDao.deleteAll();

                Receipts r = new Receipts("1","12","1","1s2d2d");
                Receipts s = new Receipts("2","13","2","1s2r44");
                Receipts t = new Receipts("3","15","1","1s2g55");
                Receipts u = new Receipts("4","17","4","1s2j98");
                rDao.insert(r);
                rDao.insert(s);
                rDao.insert(t);
                rDao.insert(u);

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };
}
