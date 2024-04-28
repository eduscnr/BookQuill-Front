package com.example.bookquill.network;

import com.example.bookquill.modelo.DetalleResenia;
import com.example.bookquill.modelo.Genero;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.modelo.RespuestaListaLibrosGenero;
import com.example.bookquill.modelo.RespuestaListarLibros;
import com.example.bookquill.modelo.UsuarioDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/libros/listar")
    Single<List<Libro>> getLibros(@Query("page") int page);
    @GET("/libros/top10")
    Single<List<Libro>> getLibrosTop10();
    @GET("/libros/random")
    Single<List<Libro>>getLibrosRandom();
    @GET("/libros/generos")
    Call<List<Genero>> getAllGeneros();
    @GET("/libros/{id}")
    Call<Libro> getLibroById(@Path("id") int id);
    @POST("auth/iniciarSesion")
    Single<ResponseBody> iniciarSesion(@Body Map<String, String> credenciales);
    @POST("auth/usuario")
    Call<UsuarioDTO> obtenerInformacionUsuario(@Query("nombreUsuario") String nombreUsuario);
    @GET("/libros/populares")
    Single<RespuestaListarLibros> librosPopulares(@Query("page") int page);
    @GET("/libros/tipoGenero")
    Single<RespuestaListaLibrosGenero> librosPorGenero(@Query("page") int page, @Query("tipo") String tipo);
    @GET("/libros/libroFavorito")
    Call<Void> insertaLibroFavorito(@Query("idLibro") int idLibros, @Query("idUsuario") int idUsuario);
    @GET("/libros/eliminarFav")
    Call<Void> eliminarFavorito(@Query("idLibro") int idLibros, @Query("idUsuario") int idUsuario);
    @GET("/libros/esFavoritos")
    Call<Boolean> esFavorito(@Query("idLibro") int idLibros, @Query("idUsuario") int idUsuario);
    @GET("/libros/mostrarResenia")
    Single<List<DetalleResenia>> mostrarResenias(@Query("page") int page, @Query("idLibro") int idLibro);
    @POST("/libros/insertarLibroPendiente")
    Call<Void>insertarLibrosPendientes(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);
    @POST("/libros/insertarLibroLeido")
    Call<Void>insertarLibroLeido(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);
    @POST("/libros/eliminarPendiente")
    Call<Void> eliminarPendiete(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);
    @GET("/libros/esPendiente")
    Call<Boolean> esPendiente(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);
    @POST("/libros/eliminarLeido")
    Call<Void> eliminarLeido(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);
    @GET("/libros/esLeido")
    Call<Boolean> esLido(@Query("idLibro") int idLibro, @Query("idUsuario") int idUsuario);

    //Endpoints para las lista de libros favoritos, pendiente y leidos
    @GET("/listarLibros/obtenerLibrosFavoritos")
    Single<RespuestaListarLibros> obtenerLibrosFavoritos(@Query("page") int page, @Query("idUsuario") int idUsuario);
    @GET("/listarLibros/obtenerLibrosPendientes")
    Single<RespuestaListarLibros> obtenerLibrosPendientes(@Query("page") int page, @Query("idUsuario") int idUsuario);
    @GET("/listarLibros/obtenerLibrosLeidos")
    Single<RespuestaListarLibros> obtenerLibrosLeidos(@Query("page") int page, @Query("idUsuario") int idUsuario);
}
