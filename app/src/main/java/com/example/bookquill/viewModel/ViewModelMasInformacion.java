package com.example.bookquill.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.modelo.DetalleResenia;
import com.example.bookquill.paginacion.PaginacionDetalleResenia;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelMasInformacion extends ViewModel {
    private String token;
    private int idLibro;
    public Flowable<PagingData<DetalleResenia>> flowableDetalleResenia;

    public ViewModelMasInformacion(String token, int idLibro) {
        this.token = token;
        this.idLibro = idLibro;
        init();
    }
    private void init(){
        PaginacionDetalleResenia paginacionDetalleResenia = new PaginacionDetalleResenia(token, idLibro);
        Pager<Integer, DetalleResenia> pager = new Pager<>(
          new PagingConfig(
            10,
            10,
            false,
            10,
            100
          ), () -> paginacionDetalleResenia
        );
        flowableDetalleResenia = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableDetalleResenia, coroutineScope);
    }
}
