package com.example.bookquill.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JwtInterceptor implements Interceptor {
    private String token;

    public JwtInterceptor(String token) {
        this.token = token;
    }
/*
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token);

        Request request = requestBuilder.build();
        Log.d("JwtInterceptor", "Request Authorization: " + request.header("Authorization"));
        return chain.proceed(request);
    }*/
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requesBuilder = original.newBuilder().header("Authorization", "Bearer "+token);
        Request request = requesBuilder.build();
        Log.d("JwtInterceptor", "Request Authorization: " + request.header("Authorization"));
        return chain.proceed(request);
    }
}
