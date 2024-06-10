package com.example.bookquill.paginacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.bookquill.modelo.Libro;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaginacionLibrosRecomendados extends RxPagingSource<Integer, Libro> {
    private String token;
    public PaginacionLibrosRecomendados() {
    }

    public PaginacionLibrosRecomendados(String token) {
        this.token = token;
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
        return apiService.getLibrosRandom()
                .subscribeOn(Schedulers.io())
                .map(libros -> {
                    return new LoadResult.Page<>(
                            libros,
                            null,
                            null
                    );
                });
    }
}
