package com.example.bookquill.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.databinding.ActivityActividadIniciarSesionBinding;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

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
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class ActividadIniciarSesion extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button iniciarSesion;
    private Button registrar;
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
        registrar = findViewById(R.id.btnRegistrar);
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        Map<String, String> credenciales = new HashMap<>();
        iniciarSesion.setOnClickListener(view -> {
            String emailIntroducido = email.getText().toString();
            String passwordIntroducido = password.getText().toString();
            credenciales.put("username", emailIntroducido);
            credenciales.put("password", passwordIntroducido);
            View viewIn = getCurrentFocus();
            if (viewIn!= null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewIn.getWindowToken(), 0);
            }
            if (!emailIntroducido.equals("") && !passwordIntroducido.equals("")) {
                apiService.iniciarSesion(credenciales).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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
                        MotionToast.Companion.createColorToast(ActividadIniciarSesion.this,
                                "Error",
                                "El usuario no existe",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(ActividadIniciarSesion.this, R.font.okta_neue_regular));
                    }
                });
            } else {
                MotionToast.Companion.createColorToast(ActividadIniciarSesion.this,
                        "Error",
                        "El campo email y el campo contraseña no se pueden dejar vacios",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(ActividadIniciarSesion.this, R.font.okta_neue_regular));
            }
        });
        registrar.setOnClickListener(view -> {
            Intent i = new Intent(ActividadIniciarSesion.this, ActividadRegistro.class);
            startActivity(i);
            finish();
        });
    }

    public static String getEmailUsuairo() {
        return emailUsuairo;
    }
}