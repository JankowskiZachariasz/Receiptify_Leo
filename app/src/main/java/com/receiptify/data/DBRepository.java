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

import android.app.Application;

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

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class DBRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    private ReceiptsDao receiptsDao;
    private LiveData<List<Receipts>> receipts;

    private Receipt_single_entryDao receipt_single_entryDao;
    private LiveData<List<Receipt_single_entry>> receipt_single_entries;

    private ProductsDao productsDao;
    private LiveData<List<Products>> products;

    private CompaniesDao companiesDao;
    private LiveData<List<Companies>> companies;

    // Note that in order to unit test the DBRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DBRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();

        receiptsDao = db.receiptsDao();
        receipts = receiptsDao.getAll();

        receipt_single_entryDao = db.receipt_single_entry();
        receipt_single_entries = receipt_single_entryDao.getAll();

        productsDao = db.products();
        products = productsDao.getAll();

        companiesDao = db.companies();
        companies = companiesDao.getAll();




    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    LiveData<List<Receipts>> getAllReceipts() {
        return receipts;
    }

    LiveData<List<Receipt_single_entry>> getAllSingleEntries() {
        return receipt_single_entries;
    }

    LiveData<List<Products>> getAllProducts() {
        return products;
    }

    LiveData<List<Companies>> getAllCompanies() {
        return companies;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Word word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }

    void delete(Word word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.delete(word);
        });
    }


    void insert(Receipts word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            receiptsDao.insert(word);
        });
    }

    void delete(Receipts word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            receiptsDao.delete(word);
        });
    }

    void insert(Receipt_single_entry word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            receipt_single_entryDao.insert(word);
        });
    }

    void delete(Receipt_single_entry word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            receipt_single_entryDao.delete(word);
        });
    }

    void insert(Products word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            productsDao.insert(word);
        });
    }

    void delete(Products word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            productsDao.delete(word);
        });
    }

    void insert(Companies word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            companiesDao.insert(word);
        });
    }

    void delete(Companies word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            companiesDao.delete(word);
        });
    }

}
