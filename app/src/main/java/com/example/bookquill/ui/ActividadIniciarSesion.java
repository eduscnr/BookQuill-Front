package com.example.bookquill.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.databinding.ActivityActividadIniciarSesionBinding;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;
import com.example.bookquill.util.ApiHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadIniciarSesion extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button iniciarSesion;
    private ApiService apiService;
    private TextView textView;
    private static String emailUsuairo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_iniciar_sesion);
        email = findViewById(R.id.editTextEmailIn);
        password = findViewById(R.id.editTextPassIn);
        iniciarSesion = findViewById(R.id.btnInicioSesion);
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        Map<String, String> credenciales = new HashMap<>();
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailIntroducido = email.getText().toString();
                String passwordIntroducido = password.getText().toString();
                credenciales.put("username", emailIntroducido);
                credenciales.put("password", passwordIntroducido);
                apiService.iniciarSesion(credenciales).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Aquí puedes manejar la suscripción si es necesario
                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String tokenFinal = responseBody.string();
                            emailUsuairo = emailIntroducido;
                            Intent i = new Intent(ActividadIniciarSesion.this, MainActivity.class);
                            i.putExtra("token", tokenFinal);
                            startActivity(i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Response", "Error: ", e);
                        // Aquí puedes manejar el error
                    }
                });
            }
        });
    }

    public static String getEmailUsuairo() {
        return emailUsuairo;
    }
}