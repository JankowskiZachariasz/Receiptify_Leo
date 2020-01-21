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
import com.receiptify.data.Entities.Products;
import com.receiptify.data.Entities.Receipt_single_entry;
import com.receiptify.data.Entities.Receipts;
import com.receiptify.data.Entities.Word;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class DBViewModel extends AndroidViewModel {

    private DBRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Word>> mAllWords;
    private LiveData<List<Receipts>> receipts;
    private LiveData<List<Receipt_single_entry>> receipt_single_entries;
    private LiveData<List<Products>> products;
    private LiveData<List<Companies>> companies;

    public DBViewModel(Application application) {
        super(application);
        mRepository = new DBRepository(application);
        mAllWords = mRepository.getAllWords();
        receipts = mRepository.getAllReceipts();
        receipt_single_entries = mRepository.getAllSingleEntries();
        products = mRepository.getAllProducts();
        companies = mRepository.getAllCompanies();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public LiveData<List<Receipts>> getAllReceipts() { return receipts;}
    public LiveData<List<Receipt_single_entry>> getAllSingleEntries() { return receipt_single_entries; }
    public LiveData<List<Products>> getAllProducts() {
        return products;
    }
    public LiveData<List<Companies>> getAllCompanies() { return companies; }

    public void insert(Word word) {
        mRepository.insert(word);
    }
    public void delete(Word word) {
        mRepository.delete(word);
    }

    public void insert(Receipts word) {
        mRepository.insert(word);
    }
    public void delete(Receipts word) {
        mRepository.delete(word);
    }

    public void insert(Receipt_single_entry word) {
        mRepository.insert(word);
    }
    public void delete(Receipt_single_entry word) {
        mRepository.delete(word);
    }

    public void insert(Products word) {
        mRepository.insert(word);
    }
    public void delete(Products word) {
        mRepository.delete(word);
    }

    public void insert(Companies word) {
        mRepository.insert(word);
    }
    public void delete(Companies word) {
        mRepository.delete(word);
    }
}
