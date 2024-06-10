package com.example.bookquill.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.bookquill.databinding.FragmentFragmentoFiltrarBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.viewModelFactory.FactoryLibrosGenero;
import com.example.bookquill.viewModel.ViewModelLibrosGenero;
import com.google.gson.Gson;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoFiltrar extends Fragment implements AdaptadorListarLibros.OnClickLibro{
    private FragmentFragmentoFiltrarBinding binding;
    private String tipo;
    private Disposable disposable;
    private RecyclerView recyclerView;
    private AdaptadorListarLibros adaptadorListarLibros;
    private FactoryLibrosGenero factoryLibrosGenero;
    private ViewModelLibrosGenero viewModelLibrosGenero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoFiltrarBinding.inflate(inflater);
        tipo = getArguments().getString("tipo");
        factoryLibrosGenero = new FactoryLibrosGenero(MainActivity.getToken(), tipo);
        viewModelLibrosGenero = new ViewModelProvider(this, factoryLibrosGenero).get(ViewModelLibrosGenero.class);
        init();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.buscar);
            }
        });
        return binding.getRoot();
    }

    private void init() {
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        viewModelLibrosGenero.getTotalLibros().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.numeroLibrosTextView.setText(String.valueOf(integer));
            }
        });
        disposable = viewModelLibrosGenero.flowableLibrosGenero.subscribe(new Consumer<PagingData<Libro>>() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.buscar);
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