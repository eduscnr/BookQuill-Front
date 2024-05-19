package com.example.bookquill.paginacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.bookquill.modelo.DetalleResenia;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaginacionDetalleResenia extends RxPagingSource<Integer, DetalleResenia> {
    private String token;
    private int idLibro;

    public PaginacionDetalleResenia() {
    }

    public PaginacionDetalleResenia(String token, int idLibro) {
        this.token = token;
        this.idLibro = idLibro;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, DetalleResenia> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, DetalleResenia>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        ApiService apiService = RetrofitClient.getRetrofitToken(token).create(ApiService.class);
        int pagina = loadParams.getKey() != null ? loadParams.getKey() : 0;
        return apiService.mostrarResenias(pagina, idLibro)
                .subscribeOn(Schedulers.io())
                .map(detalle -> toLoadResult(detalle, pagina))
                .onErrorReturn(LoadResult.Error::new);
    }
    private LoadResult<Integer, DetalleResenia> toLoadResult(List<DetalleResenia> detalles, int currentPage) {
        boolean hasNextPage = !detalles.isEmpty();
        Integer nextPage = hasNextPage ? currentPage + 1 : null;
        return new LoadResult.Page<>(detalles, null, nextPage);
    }
}
