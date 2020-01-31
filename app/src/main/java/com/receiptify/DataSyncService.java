package com.receiptify;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.receiptify.data.DBViewModel;
import com.receiptify.data.Entities.Companies;

import java.io.IOException;
import java.util.List;

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




public class DataSyncService extends Service {

    private interface RESTfulServer {


        @POST("users/register")
        Call<JsonObject> register(@Body JsonObject locationPost);

        @POST("users/authenticate")
        Call<JsonObject> authenticate(@Body JsonObject locationPost);

        @GET("companies")
        Call<JsonArray> getCompanies();

        @GET("users/{id}")
        Call<JsonObject> getUsers(@Path("id") int groupId, @Header("Authorization") String authHeader);

        @PUT("users/{id}")
        Call<JsonObject> setUsers(@Path("id") int groupId, @Header("Authorization") String authHeader, @Body JsonObject updated);


    }

    private DBViewModel dBreference;
    private RESTfulServer service;
    private String token = null;
    private String password = "password";
    private String username = "Student";
    private String surname = "Jankowski";
    private String name = "Zachariasz";


    @Override
    public IBinder onBind(Intent intent) {

       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid){

        switch(intent.getAction()){
            case("initialize"):{initialize();break;}
            case("companies"):{syncCompanies();break;}
            case("receipts"):{break;}
            default:{break;}
        }


        return START_NOT_STICKY;
    }

    private void initialize(){

        dBreference = MainActivity.DBreference;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.31.82.135/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RESTfulServer.class);

    }

    private synchronized void login(){

        JsonObject j = new JsonObject();
        j.addProperty("username",username);
        j.addProperty("password",password);
        j.addProperty("FirstName",name);
        j.addProperty("LastName",surname);


        try { service.register(j).execute();
              token = service.authenticate(j).execute().body().get("token").getAsString();
        } catch (IOException e) {e.printStackTrace();}

    }

    private synchronized JsonArray Companies_server(){
        JsonArray companies =null;
        try {
            companies = service.getCompanies().execute().body();
        }catch (Exception e){e.printStackTrace();}

        return companies;
    }

    private synchronized void syncCompanies(){

        while(token==null)login();
        JsonArray companies_server = Companies_server();
        for(int i=0;i<companies_server.size();i++){
            Companies c = new Companies(companies_server.get(i).getAsJsonObject().get("id").getAsString(),companies_server.get(i).getAsJsonObject().get("name").getAsString());
            dBreference.insert(c);
        }



        Toast.makeText(this,"Finished loading companies!",Toast.LENGTH_LONG).show();
    }

}

