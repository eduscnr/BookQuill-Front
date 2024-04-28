package com.example.bookquill.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionLibrosFavoritos;
import com.example.bookquill.paginacion.PaginacionLibrosLeidos;
import com.example.bookquill.paginacion.PaginacionLibrosPendientes;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelUsuario extends ViewModel {
    public Flowable<PagingData<Libro>> flowableLibrosFavoritos;
    public Flowable<PagingData<Libro>> flowableLibrosPendientes;
    public Flowable<PagingData<Libro>> flowableLibrosLeido;
    private String token;

    public ViewModelUsuario(String token) {
        this.token = token;
        init();
        initPendiente();
        initLeido();
    }

    private void init() {
        PaginacionLibrosFavoritos paginacionLibrosFavoritos = new PaginacionLibrosFavoritos(token);
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionLibrosFavoritos
        );
        flowableLibrosFavoritos = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosFavoritos, coroutineScope);
    }
    private void initPendiente(){
        PaginacionLibrosPendientes paginacionLibrosPendientes = new PaginacionLibrosPendientes(token);
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionLibrosPendientes
        );
        flowableLibrosPendientes = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosPendientes, coroutineScope);
    }
    private void initLeido(){
        PaginacionLibrosLeidos paginacionLibrosLeidos = new PaginacionLibrosLeidos(token);
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionLibrosLeidos
        );
        flowableLibrosLeido = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosLeido, coroutineScope);
    }
}
