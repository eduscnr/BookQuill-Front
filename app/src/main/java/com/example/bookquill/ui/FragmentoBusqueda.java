package com.example.bookquill.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.GenerosAdaptador;
import com.example.bookquill.databinding.FragmentFragmentoBusquedaBinding;
import com.example.bookquill.modelo.Genero;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentoBusqueda extends Fragment {
    private FragmentFragmentoBusquedaBinding binding;
    private ApiService apiService;
    private int[] iconos = {R.drawable.icono_ficcion,R.drawable.icono_misterio,R.drawable.icono_general,R.drawable.icono_threllier,R.drawable.icono_terror,R.drawable.icono_romance,R.drawable.icono_fantasia,R.drawable.icono_crimen};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoBusquedaBinding.inflate(inflater);
        apiService = RetrofitClient.getRetrofitToken(MainActivity.getToken()).create(ApiService.class);
        apiService.getAllGeneros().enqueue(new Callback<List<Genero>>() {
            @Override
            public void onResponse(Call<List<Genero>> call, Response<List<Genero>> response) {
                if (response.isSuccessful()){
                    mostrarGeneros(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Genero>> call, Throwable throwable) {

            }
        });
        binding.buscador.setOnClickListener(view -> {
            Intent lanzarActividadBusqueda = new Intent(requireContext(), ActividadBusqueda.class);
            startActivity(lanzarActividadBusqueda);
        });
        return binding.getRoot();
    }


    private void mostrarGeneros(List<Genero> generos) {
        GenerosAdaptador adaptador = new GenerosAdaptador(requireContext(), generos, iconos);
        for (int i = 0; i<generos.size();i++){
            View gridItemView = adaptador.getView(i, null, binding.gridLayout);
            binding.gridLayout.addView(gridItemView);
        }
    }
}