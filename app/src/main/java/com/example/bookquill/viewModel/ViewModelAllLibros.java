package com.example.bookquill.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionAllLibros;
import com.example.bookquill.paginacion.PaginacionListaMejoresLibros;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;


public class ViewModelAllLibros extends ViewModel {
    private String token;
    public Flowable<PagingData<Libro>> flowableLibrosPopulares;
    private PaginacionAllLibros paginacionAllLibros;
    public ViewModelAllLibros(String token) {
        this.token = token;
        init();
    }

    private void init() {
        paginacionAllLibros = new PaginacionAllLibros(token);
        Pager<Integer,Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionAllLibros
        );
        flowableLibrosPopulares = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosPopulares, coroutineScope);
    }
    public LiveData<Integer> getTotalLibro(){
        return paginacionAllLibros.getTotalLibros();
    }
}
