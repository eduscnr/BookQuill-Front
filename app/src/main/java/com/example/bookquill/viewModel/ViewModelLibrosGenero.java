package com.example.bookquill.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionLibrosGenero;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelLibrosGenero extends ViewModel {
    private String token;
    private String tipo;
    public Flowable<PagingData<Libro>> flowableLibrosGenero;
    private PaginacionLibrosGenero paginacionLibrosGenero;

    public ViewModelLibrosGenero(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
        init();
    }

    private void init() {
        paginacionLibrosGenero = new PaginacionLibrosGenero(token, tipo);
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), ()-> paginacionLibrosGenero
        );
        flowableLibrosGenero = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosGenero, coroutineScope);
    }
    public LiveData<Integer> getTotalLibros(){
        return paginacionLibrosGenero.getTotal();
    }
}
