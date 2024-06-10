package com.example.bookquill.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.AdaptadorListarLibros;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.FragmentoListarLibrosBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.SharedViewModel;
import com.example.bookquill.viewModel.ViewModelAllLibros;
import com.example.bookquill.viewModel.viewModelFactory.FactoryAllLibros;
import com.example.bookquill.viewModel.viewModelFactory.FactoryLibrosPopulares;
import com.example.bookquill.viewModel.ViewModelLibrosPopulares;
import com.google.gson.Gson;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoListarLibros extends Fragment implements AdaptadorListarLibros.OnClickLibro{
    private FragmentoListarLibrosBinding binding;
    private FactoryLibrosPopulares factoryLibrosPopulares;
    private ViewModelLibrosPopulares viewModelLibrosPopulares;
    private SharedViewModel viewModelShared;
    private RecyclerView recyclerView;
    private AdaptadorListarLibros adaptadorListarLibros;
    private Disposable disposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentoListarLibrosBinding.inflate(inflater);
        viewModelShared = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModelShared.getArgumentos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equalsIgnoreCase("Recomendados")) {
                    initAllLibros();
                } else {
                    init();
                }
            }
        });
        binding.back.setOnClickListener(view ->{
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.inicio);
        });
        return binding.getRoot();
    }

    private void initAllLibros() {
        FactoryAllLibros factoryAllLibros = new FactoryAllLibros(MainActivity.getToken());
        ViewModelAllLibros allLibros = new ViewModelProvider(this,factoryAllLibros).get(ViewModelAllLibros.class);
        AdaptadorListarLibros adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        allLibros.getTotalLibro().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.numeroLibrosTextView.setText(String.valueOf(integer));
            }
        });
        Disposable disposable = allLibros.flowableLibrosPopulares.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
            }
        });
    }

    private void init() {
        factoryLibrosPopulares = new FactoryLibrosPopulares(MainActivity.getToken());
        viewModelLibrosPopulares = new ViewModelProvider(this, factoryLibrosPopulares).get(ViewModelLibrosPopulares.class);
        adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        viewModelLibrosPopulares.getTotalLibro().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.numeroLibrosTextView.setText(String.valueOf(integer));
            }
        });
        disposable = viewModelLibrosPopulares.flowableLibrosPopulares.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                binding.recyclerViewListaLibros.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
                    }
                }, 500);
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