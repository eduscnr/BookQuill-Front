package com.example.bookquill.paginacion;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.bookquill.MainActivity;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaginacionFiltrodoBusqueda extends RxPagingSource<Integer, Libro> {
    private String token;
    private String filtro;
    private MutableLiveData<Integer> totalLibros;

    public PaginacionFiltrodoBusqueda(String token, String filtro) {
        this.token = token;
        this.filtro = filtro;
        totalLibros = new MutableLiveData<>();
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Libro> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Libro>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int pagina = loadParams.getKey() != null ? loadParams.getKey() : 0;
        Log.d("PRUEBA PAGINACION", filtro);
        ApiService apiService = RetrofitClient.getRetrofitToken(token).create(ApiService.class);
        return apiService.filtradoBusqueda(pagina, filtro)
                .subscribeOn(Schedulers.io())
                .map(respuestaListarLibros -> {
                    List<Libro> libros = respuestaListarLibros.getLibros();
                    totalLibros.postValue(respuestaListarLibros.getTotal());
                    return toLoadResult(libros, pagina);
                })
                .onErrorReturn(LoadResult.Error::new);
    }
    private LoadResult<Integer, Libro> toLoadResult(List<Libro> libros, int currentPage) {
        boolean hasNextPage = !libros.isEmpty();
        Integer nextPage = hasNextPage ? currentPage + 1 : null;
        return new LoadResult.Page<>(libros, null, nextPage);
    }
}
