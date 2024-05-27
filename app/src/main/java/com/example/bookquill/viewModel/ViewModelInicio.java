package com.example.bookquill.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.rxjava3.PagingRx;

import com.example.bookquill.MainActivity;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.paginacion.PaginacionLibrosRecomendados;
import com.example.bookquill.paginacion.PaginacionMejoresLibros;

import io.reactivex.rxjava3.core.Flowable;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

public class ViewModelInicio extends ViewModel {
    public Flowable<PagingData<Libro>> flowableLibrosRecomendados;
    public Flowable<PagingData<Libro>> flowableMejoresLibros;
    private String token;
    private MutableLiveData<String> nombreUsuario = new MutableLiveData<>();

    public ViewModelInicio(String token) {
        this.token = token;
        initRecomendados();
        initMejores();
    }
    public ViewModelInicio(String token, MutableLiveData nombreUsuario) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        initRecomendados();
        initMejores();
    }

    public ViewModelInicio() {
        initRecomendados();
        initMejores();
    }

    private void initMejores() {
        PaginacionMejoresLibros paginacionMejoresLibros = new PaginacionMejoresLibros(MainActivity.getToken());
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ), () -> paginacionMejoresLibros
        );
        flowableMejoresLibros = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableMejoresLibros, coroutineScope);
    }

    private void initRecomendados() {
        PaginacionLibrosRecomendados paginacionLibrosRecomendados = new PaginacionLibrosRecomendados(MainActivity.getToken());
        Pager<Integer, Libro> pager = new Pager<>(
                new PagingConfig(
                        10,
                        10,
                        false,
                        10,
                        100
                ),
                () ->paginacionLibrosRecomendados
        );
        flowableLibrosRecomendados = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowableLibrosRecomendados, coroutineScope);
    }

    public MutableLiveData<String> getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(MutableLiveData<String> nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
