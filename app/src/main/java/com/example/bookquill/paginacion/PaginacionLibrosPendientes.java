package com.example.bookquill.paginacion;

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

public class PaginacionLibrosPendientes extends RxPagingSource<Integer, Libro> {
    private String token;
    private MutableLiveData<Integer> totalLibros;

    public PaginacionLibrosPendientes(String token) {
        this.token = token;
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
        ApiService apiService = RetrofitClient.getRetrofitToken(token).create(ApiService.class);
        int pagina = loadParams.getKey() != null ? loadParams.getKey() : 0;
        return apiService.obtenerLibrosPendientes(pagina, MainActivity.getUsuarioDTO().getIdUsuario())
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
