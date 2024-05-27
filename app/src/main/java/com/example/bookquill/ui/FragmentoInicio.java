package com.example.bookquill.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.AdaptadorLibrosInicio;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.FragmentFragmentoInicioBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.viewModelFactory.FactoryInicio;
import com.example.bookquill.viewModel.ViewModelInicio;
import com.google.gson.Gson;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoInicio extends Fragment implements AdaptadorLibrosInicio.OnClickLibro {
    private FragmentFragmentoInicioBinding binding;
    private ViewModelInicio viewModelInicio;
    private FactoryInicio modelFactory;
    private AdaptadorLibrosInicio adaptadorLibrosRecomendados;
    private AdaptadorLibrosInicio adaptadorMejoresLibros;
    private Disposable disposableRecomendados;
    private Disposable disposableMejores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoInicioBinding.inflate(inflater);
        modelFactory = new FactoryInicio(MainActivity.getToken());
        viewModelInicio = new ViewModelProvider(this, modelFactory).get(ViewModelInicio.class);
        initViewRecomendados();
        initViewMejores();
        binding.textViewMejoresLibros.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_hostfragment);
            navController.navigate(R.id.action_fragmento_inicio_to_fragmento_lstar_libros, null,new NavOptions.Builder()
                    .setPopUpTo(R.id.inicio, true)
                    .build());

        });
        return binding.getRoot();
    }

    private void initViewMejores() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewTheBest.setLayoutManager(linearLayoutManager);
        adaptadorMejoresLibros = new AdaptadorLibrosInicio(new ComparadorLibros(), this);
        binding.recyclerViewTheBest.setAdapter(adaptadorMejoresLibros);
        disposableMejores = viewModelInicio.flowableMejoresLibros.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorMejoresLibros.submitData(getLifecycle(), libroPagingData);
            }
        });
    }

    private void initViewRecomendados() {
        binding.recyclerViewRecomendados.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
        adaptadorLibrosRecomendados = new AdaptadorLibrosInicio(new ComparadorLibros(), this);
        binding.recyclerViewRecomendados.setAdapter(adaptadorLibrosRecomendados);
        disposableRecomendados = viewModelInicio.flowableLibrosRecomendados.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorLibrosRecomendados.submitData(getLifecycle(), libroPagingData);
            }
        });
    }

    @Override
    public void mostrarInformacionLibro(Libro l) {
        Gson gson = new Gson();
        String libroJson = gson.toJson(l);
        Intent i = new Intent(requireContext(), ActividadMasInformacion.class);
        i.putExtra("libro", libroJson);
        startActivity(i);
    }
}