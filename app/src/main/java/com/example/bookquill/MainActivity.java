package com.example.bookquill;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.window.OnBackAnimationCallback;

import com.example.bookquill.adaptadores.AdaptadorLibrosInicio;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.modelo.UsuarioDTO;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;
import com.example.bookquill.ui.ActividadIniciarSesion;
import com.example.bookquill.util.ApiHelper;
import com.example.bookquill.viewModel.ViewModelInicio;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.MenuItem;
import io.reactivex.rxjava3.disposables.Disposable;
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
        bottomBar.setOnItemSelectedListener(new Function3<View, MenuItem, Boolean, Unit>() {
            @Override
            public Unit invoke(View view, MenuItem menuItem, Boolean aBoolean) {
                if(menuItem.getId() == R.id.menuBuscar){
                    navController.navigate(R.id.buscar);
                } else if (menuItem.getId()==R.id.menuInicio) {
                    navController.navigate(R.id.inicio);
                } else if (menuItem.getId()==R.id.menuPerfil) {
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
                if(response.isSuccessful()){
                   usuarioDTO = response.body();
                   Log.d("PRUEBA USUARIO", usuarioDTO.toString());
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