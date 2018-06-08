package com.example.aneudy.myapplication.NET;

/**
 * Created by aneudy on 23/1/2017.
 */

import android.util.Log;

import com.example.aneudy.myapplication.Data.DataGeneral_config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    //public static final String BASE_URL = DataGeneral_config.HOST_NAME+":"+DataGeneral_config.PORT+"/juan/v1/";
    //public static final String BASE_URL="http://10.0.0.22/juan/v1/";
    //public static final String BASE_URL="http://analisisdeportivo.com.do/jhostest/Test/v2/";
    public static final String BASE_URL="http://18.191.43.91/openbravo/v1/";

    private static Retrofit retrofit = null;


    /*public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Log.d("getClient",retrofit.toString());
        }
        return retrofit;
    }*/

    public static ApiInterface getClient(){

        OkHttpClient.Builder httpClient  = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.MINUTES);
        httpClient.readTimeout(5,TimeUnit.MINUTES);
        httpClient.retryOnConnectionFailure(true);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addNetworkInterceptor(loggingInterceptor);

        OkHttpClient client =httpClient.build();


        Retrofit retrofit = new Retrofit
                .Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.e("params retrofit",client.toString());
        return retrofit.create(ApiInterface.class);
    }

}
