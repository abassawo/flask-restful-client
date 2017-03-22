package com.example.abass.peoplerestclient.network;


import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final long CONNECTION_TIMEOUT = 30;
    private static final String TAG = RestClient.class.getSimpleName();
    private static final String BASE_URL = "https://fathomless-castle-86565.herokuapp.com";
    private static RestClient instance;
    private final RestApi restApi;

    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public RestClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        GsonConverterFactory gsonConverter = GsonConverterFactory.create(gson);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.v(TAG, message)).setLevel(
                        HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(gsonConverter)
                .build();
        restApi = retrofit.create(RestApi.class);
    }

    public RestApi getRestApi() {
        return restApi;
    }
}
