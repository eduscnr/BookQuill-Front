package com.example.bookquill.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.MainActivity;
import com.example.bookquill.adaptadores.AdaptadorLibrosInicio;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.FragmentFragmentoPerfilBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.ViewModelUsuario;
import com.example.bookquill.viewModel.viewModelFactory.FactoryUsuario;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoPerfil extends Fragment implements AdaptadorLibrosInicio.OnClickLibro{

    private FragmentFragmentoPerfilBinding binding;
    private FactoryUsuario factoryUsuario;
    private ViewModelUsuario viewModelUsuario;
    private AdaptadorLibrosInicio adaptadorLibrosFavoritos;
    private AdaptadorLibrosInicio adaptadorLibrosPendiente;
    private AdaptadorLibrosInicio adaptadorLibrosLeido;
    private Disposable disposableFavorito;
    private Disposable disposablePendiente;
    private Disposable disposableLeido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoPerfilBinding.inflate(inflater);
        factoryUsuario = new FactoryUsuario(MainActivity.getToken());
        viewModelUsuario = new ViewModelProvider(this, factoryUsuario).get(ViewModelUsuario.class);
        init();
        initLeido();
        initPendiente();
        return binding.getRoot();
    }
    public void init(){
        binding.recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptadorLibrosFavoritos = new AdaptadorLibrosInicio(new ComparadorLibros(), this);
        binding.recyclerViewFavoritos.setAdapter(adaptadorLibrosFavoritos);
        disposableFavorito = viewModelUsuario.flowableLibrosFavoritos.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                binding.recyclerViewFavoritos.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorLibrosFavoritos.submitData(getLifecycle(), libroPagingData);
                    }
                }, 500);
            }
        });
    }
    public void initPendiente(){
        binding.recyclerViewPendiente.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptadorLibrosPendiente = new AdaptadorLibrosInicio(new ComparadorLibros(), this);
        binding.recyclerViewPendiente.setAdapter(adaptadorLibrosPendiente);
        disposablePendiente = viewModelUsuario.flowableLibrosPendientes.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                binding.recyclerViewPendiente.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorLibrosPendiente.submitData(getLifecycle(), libroPagingData);
                    }
                }, 500);
            }
        });
    }
    public void initLeido(){
        binding.recyclerViewLeidos.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adaptadorLibrosLeido = new AdaptadorLibrosInicio(new ComparadorLibros(), this);
        binding.recyclerViewLeidos.setAdapter(adaptadorLibrosLeido);
        disposableLeido = viewModelUsuario.flowableLibrosLeido.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                binding.recyclerViewLeidos.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorLibrosLeido.submitData(getLifecycle(), libroPagingData);
                    }
                }, 500);
            }
        });
    }
    @Override
    public void mostrarInformacionLibro(Libro l) {

    }
}