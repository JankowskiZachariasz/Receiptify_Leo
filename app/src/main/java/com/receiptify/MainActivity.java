package com.receiptify;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.getbase.floatingactionbutton.FloatingActionButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.receiptify.activities.AddReceipt;
import com.receiptify.activities.Products;
import com.receiptify.activities.ReceiptsView;
import com.receiptify.activities.Settings;
import com.receiptify.activities.Statistics;
import com.receiptify.data.DBViewModel;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.graphics.Color.argb;

public class MainActivity extends AppCompatActivity {

    private static String CLOUD_VISION_API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static DBViewModel DBreference;

    public static TextView mImageDetails;
    private ImageView mMainImage;



    //private ReceiptsViewModel DBreference;

    private DataSyncService dataSyncService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CLOUD_VISION_API_KEY = getString(R.string.CLOUD_VISION_API_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createFabMenu();
        DBreference = new DBViewModel(this.getApplication());
        startService(new Intent(this,DataSyncService.class).setAction("initialize"));


       {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .permitNetwork()
                    .permitCustomSlowCalls()
                    .permitAll()// or .detectAll() for all detectable problems
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()

                    .build());
        }


        mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);



        // Update the cached copy of the words to the TextView
        DBreference.getAllCompanies().observe(this, words -> {

            String s="";
            for(int i=0;i<words.size();i++)
                 s += words.get(i).getName()+" ";
            mImageDetails.setText(s);

        });

        buttons();



    }



    void buttons(){
        {
            Button a = findViewById(R.id.receipts);
            a.setOnClickListener(this::goReceipts);
        }
        {
            Button a = findViewById(R.id.products);
            a.setOnClickListener(this::goProducts);
        }
        {
            Button a = findViewById(R.id.settings);
            a.setOnClickListener(this::goSettings);
        }
        {
            Button a = findViewById(R.id.statistics);
            a.setOnClickListener(this::goStatistics);
        }
    }

    void goReceipts(View view) {
        Intent a = new Intent(this,ReceiptsView.class);
        startActivity(a);


    }
    void goStatistics(View view) {
        Intent a = new Intent(this, Statistics.class);
        startActivity(a);

    }
    void goSettings(View view) {
        Intent a = new Intent(this, Settings.class);
        startActivity(a);

    }
    void goProducts(View view) {
        Intent a = new Intent(this, Products.class);
        startActivity(a);

    }


    void createFabMenu(){

        final FloatingActionsMenu menuMultipleActions = findViewById(R.id.multiple_actions);

        FloatingActionButton takePhoto = new FloatingActionButton(getBaseContext());
        takePhoto.setColorNormal(argb(255,255,0,0));
        takePhoto.setTitle("take a photo");
        takePhoto.setOnClickListener(v -> {

            Intent a = new Intent(this, AddReceipt.class).setAction("take a photo");
            startActivity(a);
            menuMultipleActions.collapse();});


        FloatingActionButton loadPhoto = new FloatingActionButton(getBaseContext());
        loadPhoto.setColorNormal(argb(255,0,255,0));
        loadPhoto.setTitle("point app to an existing photo from the phone's storage");
        loadPhoto.setOnClickListener(v -> {


            Intent a = new Intent(this, AddReceipt.class).setAction("gallery");
            startActivity(a);
            menuMultipleActions.collapse();});

        FloatingActionButton addDB = new FloatingActionButton(getBaseContext());
        addDB.setColorNormal(argb(255,0,0,255));
        addDB.setTitle("addDB");
        addDB.setOnClickListener(v -> {



            startService(new Intent(this,DataSyncService.class).setAction("companies"));

            //login();


        });



        menuMultipleActions.addButton(takePhoto);
        menuMultipleActions.addButton(loadPhoto);
        menuMultipleActions.addButton(addDB);

    }

}
