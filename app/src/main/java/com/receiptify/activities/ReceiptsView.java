package com.receiptify.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.receiptify.MainActivity;
import com.receiptify.R;
import com.receiptify.data.DBViewModel;
import com.receiptify.data.Entities.Receipts;

import java.util.List;

public class ReceiptsView extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        Toolbar toolbar = findViewById(R.id.toolbarReceipts);
        toolbar.setTitle("Receipts");
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ReceiptsAdapter adapter = new ReceiptsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        DBViewModel mDBViewModel = new ViewModelProvider(this).get(DBViewModel.class);


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.


        mDBViewModel.getAllReceipts().observe(this, new Observer<List<Receipts>>() {
            @Override
            public void onChanged(@Nullable final List<Receipts> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });


    }
}
