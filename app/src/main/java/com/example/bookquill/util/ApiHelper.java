package com.example.bookquill.util;

import android.util.Log;
import android.widget.TextView;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApiHelper {
    private String token;

    public ApiHelper(String token) {
        this.token = token;
    }

    public void obtenerLibros(TextView textView){
        ApiService apiService = RetrofitClient.getRetrofitToken(token).create(ApiService.class);
        apiService.getLibros(0).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Libro>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Libro> libros) {
                        Log.d("Prueba", ""+libros);
                        textView.setText(" "+ libros);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Error", ""+e);
                    }
                });
    }
}
