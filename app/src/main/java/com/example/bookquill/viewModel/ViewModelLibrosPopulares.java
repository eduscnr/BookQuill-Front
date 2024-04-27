package com.example.bookquill.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionListaMejoresLibros;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelLibrosPopulares extends ViewModel {
    private String token;
    public Flowable<PagingData<Libro>> flowableLibrosPopulares;
    private PaginacionListaMejoresLibros paginacionListaMejoresLibros;

    public ViewModelLibrosPopulares() {
    }

    public ViewModelLibrosPopulares(String token) {
        this.token = token;
        init();
    }
    public void init(){
        paginacionListaMejoresLibros = new PaginacionListaMejoresLibros(token);
        Pager<Integer,Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionListaMejoresLibros
        );
        flowableLibrosPopulares = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosPopulares, coroutineScope);
    }
    public LiveData<Integer> getTotalLibro(){
        return paginacionListaMejoresLibros.getTotalLibros();
    }
}
