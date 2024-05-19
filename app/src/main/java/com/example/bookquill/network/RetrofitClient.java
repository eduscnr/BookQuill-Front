package com.example.bookquill.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL= "http://192.168.18.5:9000/";
    private static final String BASE_URL= "https://c1b3-185-147-19-6.ngrok-free.app/";
    private static Retrofit retrofit = null;
    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofitToken(String token){
        Log.d("TOKEN EN RETROFITCLIENT: ", token);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new JwtInterceptor(token))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build();
        return retrofit;
    }
}
