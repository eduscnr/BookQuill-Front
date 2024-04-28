package com.example.bookquill.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionFiltrodoBusqueda;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelActividadBuscador extends ViewModel {
    private String token;
    private String filtro;
    public Flowable<PagingData<Libro>> flowableFiltroBusqueda;

    public ViewModelActividadBuscador(String token, String filtro) {
        this.token = token;
        this.filtro = filtro;
    }
    public void init(String filtroNuevo){
        Log.d("PRUEBA VIEWMODEL EJECUTANDOSE", filtroNuevo);
        PaginacionFiltrodoBusqueda paginacionFiltrodoBusqueda = new PaginacionFiltrodoBusqueda(token, filtroNuevo);
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), ()-> paginacionFiltrodoBusqueda
        );
        flowableFiltroBusqueda = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableFiltroBusqueda, coroutineScope);
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}
