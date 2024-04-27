package com.example.bookquill.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.MainActivity;
import com.example.bookquill.adaptadores.AdaptadorListarLibros;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.FragmentoListarLibrosBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.viewModelFactory.FactoryLibrosPopulares;
import com.example.bookquill.viewModel.ViewModelLibrosPopulares;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoListarLibros extends Fragment {
    private FragmentoListarLibrosBinding binding;
    private FactoryLibrosPopulares factoryLibrosPopulares;
    private ViewModelLibrosPopulares viewModelLibrosPopulares;
    private RecyclerView recyclerView;
    private AdaptadorListarLibros adaptadorListarLibros;
    private Disposable disposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentoListarLibrosBinding.inflate(inflater);
        factoryLibrosPopulares = new FactoryLibrosPopulares(MainActivity.getToken());
        viewModelLibrosPopulares = new ViewModelProvider(this, factoryLibrosPopulares).get(ViewModelLibrosPopulares.class);
        init();
        return binding.getRoot();
    }

    private void init() {
        adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros());
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
}