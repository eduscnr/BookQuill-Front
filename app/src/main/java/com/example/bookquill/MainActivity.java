package com.example.bookquill;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import com.example.bookquill.modelo.UsuarioDTO;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;
import com.example.bookquill.ui.ActividadIniciarSesion;
import com.example.bookquill.viewModel.viewModelFactory.ViewModelUser;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.MenuItem;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ExpandableBottomBar bottomBar;
    private static String token;
    private ApiService apiService;
    private static UsuarioDTO usuarioDTO;
    private ViewModelUser viewModelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        bottomBar = findViewById(R.id.expandable_bottom_bar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragment);
        NavController navController = navHostFragment.getNavController();
        Intent i = getIntent();
        token = i.getStringExtra("token");
        viewModelUser = new ViewModelProvider(this).get(ViewModelUser.class);
        bottomBar.setOnItemSelectedListener(new Function3<View, MenuItem, Boolean, Unit>() {
            @Override
            public Unit invoke(View view, MenuItem menuItem, Boolean aBoolean) {
                if (menuItem.getId() == R.id.menuBuscar) {
                    navController.navigate(R.id.buscar);
                } else if (menuItem.getId() == R.id.menuInicio) {
                    navController.navigate(R.id.inicio);
                } else if (menuItem.getId() == R.id.menuPerfil) {
                    navController.navigate(R.id.perfil);
                }
                return null;
            }
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                bottomBar.getMenu().select(R.id.menuInicio);
            }
        });
        apiService.obtenerInformacionUsuario(ActividadIniciarSesion.getEmailUsuairo()).enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful()) {
                    usuarioDTO = response.body();
                    viewModelUser.setUsuarioDTO(usuarioDTO);
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable throwable) {

            }
        });
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MainActivity.token = token;
    }

    public static UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }
}