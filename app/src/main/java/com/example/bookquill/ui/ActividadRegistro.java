package com.example.bookquill.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bookquill.R;
import com.example.bookquill.databinding.ActivityActividadRegistroBinding;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ActividadRegistro extends AppCompatActivity {
    private ActivityActividadRegistroBinding binding;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        binding.btnRegistrar4.setOnClickListener(view ->{
            String email = binding.editTextEmailRg.getText().toString();
            String password = binding.editTextPassRg.getText().toString();
            String nombreUsuario = binding.editTextNombreRg.getText().toString();
            apiService.registrar(email, password, nombreUsuario).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ResponseBody>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ResponseBody responseBody) {
                    Intent i = new Intent(ActividadRegistro.this, ActividadIniciarSesion.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("Error", "El error ", e);
                }
            });
        });
    }
}