package com.example.bookquill.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.AdaptadorListarLibros;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.ActivityActividadBusquedaBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.ViewModelActividadBuscador;
import com.example.bookquill.viewModel.viewModelFactory.FactoryActividadBuscador;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class ActividadBusqueda extends AppCompatActivity {
    private ActivityActividadBusquedaBinding binding;
    private ViewModelActividadBuscador viewModelActividadBuscador;
    private FactoryActividadBuscador factoryActividadBuscador;
    private AdaptadorListarLibros adaptadorListarLibros;
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buscador.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(binding.buscador, InputMethodManager.SHOW_IMPLICIT);
        }
        binding.buscador.selectAll();
        adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros());
        binding.recyclerViewResultado.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewResultado.setAdapter(adaptadorListarLibros);
        binding.buscador.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Log.d("SEONEDITOR", v.getText().toString());
                    init(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    private void init(String filtro){
        factoryActividadBuscador = new FactoryActividadBuscador(MainActivity.getToken(), filtro);
        viewModelActividadBuscador = new ViewModelProvider(ActividadBusqueda.this, factoryActividadBuscador).get(ViewModelActividadBuscador.class);
        viewModelActividadBuscador.init(filtro);
        adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros());
        binding.recyclerViewResultado.setHasFixedSize(true);
        binding.recyclerViewResultado.setAdapter(adaptadorListarLibros);

        disposable = viewModelActividadBuscador.flowableFiltroBusqueda.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                binding.recyclerViewResultado.post(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
                    }
                });
            }
        });
    }

}