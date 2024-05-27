package com.example.bookquill.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.AdaptadorResenia;
import com.example.bookquill.comparadores.ComparadorResenia;
import com.example.bookquill.databinding.ActividadMasInformacionBinding;
import com.example.bookquill.modelo.DetalleResenia;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.network.ApiService;
import com.example.bookquill.network.RetrofitClient;
import com.example.bookquill.viewModel.ViewModelMasInformacion;
import com.example.bookquill.viewModel.viewModelFactory.FactoryMasInformacion;
import com.google.gson.Gson;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadMasInformacion extends AppCompatActivity {
    private ActividadMasInformacionBinding binding;
    private boolean fav = false;
    private boolean pendiente = false;
    private boolean leido = false;
    private ApiService apiService;
    private Libro libro;
    private RecyclerView listaReseniaRecyclerView;
    private ViewModelMasInformacion viewModelMasInformacion;
    private FactoryMasInformacion factoryMasInformacion;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActividadMasInformacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Gson gson = new Gson();
        libro = gson.fromJson(getIntent().getStringExtra("libro"), Libro.class);
        Log.d("TOKEN", MainActivity.getToken());
        factoryMasInformacion = new FactoryMasInformacion(MainActivity.getToken(), libro.getIdLibro());
        viewModelMasInformacion = new ViewModelProvider(this, factoryMasInformacion).get(ViewModelMasInformacion.class);
        apiService = RetrofitClient.getRetrofitToken(MainActivity.getToken()).create(ApiService.class);
        if (libro.getUrlImagen().contains("https://images/icons") || libro.getUrlImagen().contains("https:/images/icons")) {
            Glide.with(binding.portada.getContext()).load(R.drawable.sinportada).apply(RequestOptions.bitmapTransform(new RoundedCorners(35))).into(binding.portada);
        } else {
            Glide.with(this).load(libro.getUrlImagen()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    // Escalar la imagen al tamaño
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 128, 238, false);
                    binding.portada.setImageBitmap(scaledBitmap);
                    return true;
                }
            }).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(binding.portada);
        }
        binding.titulo.setText(libro.getTitulo());
        binding.descripcion.setText(libro.getDescripcion());
        binding.inforAutor.setText(libro.getAutor());
        float popularidad = Float.parseFloat(libro.getEstrellas());
        binding.popularidad.setRating(popularidad);
        binding.imageViewFav.setOnClickListener(view -> {
            if(!fav){
                binding.imageViewFav.setImageResource(R.drawable.icons8_favoritos_48);
                fav = true;
                Log.d("PRUEBA", "" + libro.getIdLibro());
                apiService.insertaLibroFavorito(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro favorito insertado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                binding.imageViewFav.setImageResource(R.drawable.icons8_favoritos_32);
                apiService.eliminarFavorito(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro favorito eliminado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
                fav = false;
            }
        });
        ValueAnimator animator = ValueAnimator.ofFloat(0, popularidad);
        animator.setDuration(4000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                binding.popularidad.setRating(animatedValue);
            }
        });
        animator.start();
        apiService.esFavorito(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    boolean favorito = response.body();
                    if(favorito){
                        binding.imageViewFav.setImageResource(R.drawable.icons8_favoritos_48);
                        fav = favorito;
                    }else{
                        binding.imageViewFav.setImageResource(R.drawable.icons8_favoritos_32);
                        fav = favorito;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

            }
        });
        binding.salirInformacion.setOnClickListener(view -> {
            finish();
        });
        inicializarRecyclerView();
        insertarPendiente();
        insertarLibroLeido();
        esPendiente();
        esLeido();
    }
    private void inicializarRecyclerView(){
        listaReseniaRecyclerView = binding.resenias;
        AdaptadorResenia adaptadorResenia = new AdaptadorResenia(new ComparadorResenia());
        listaReseniaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaReseniaRecyclerView.setAdapter(adaptadorResenia);
        disposable = viewModelMasInformacion.flowableDetalleResenia.subscribe(new Consumer<PagingData<DetalleResenia>>() {
            @Override
            public void accept(PagingData<DetalleResenia> detalleReseniaPagingData) throws Throwable {
                listaReseniaRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adaptadorResenia.submitData(getLifecycle(), detalleReseniaPagingData);
                    }
                }, 500);
            }
        });
    }
    private void insertarPendiente(){
        binding.pendiente.setOnClickListener(view -> {
            if(!pendiente){
                pendiente = true;
                binding.pendiente.setImageResource(R.drawable.pendienteactivado);
                apiService.insertarLibrosPendientes(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro pendiente insertado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                pendiente = false;
                binding.pendiente.setImageResource(R.drawable.pendiente);
                apiService.eliminarPendiete(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro pendiente eliminado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void insertarLibroLeido(){
        binding.leido.setOnClickListener(view -> {
            if(!leido){
                leido = true;
                binding.leido.setImageResource(R.drawable.icons8_ojo_64);
                apiService.insertarLibroLeido(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro leido insertado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                leido = false;
                binding.leido.setImageResource(R.drawable.icons8_ojo_cerrado_50);
                apiService.eliminarLeido(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ActividadMasInformacion.this, "Libro leido eliminado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void esPendiente(){
        apiService.esPendiente(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    boolean esPendiente = response.body();
                    if(esPendiente){
                        binding.pendiente.setImageResource(R.drawable.pendienteactivado);
                        pendiente = esPendiente;
                    }else{
                        binding.pendiente.setImageResource(R.drawable.pendiente);
                        pendiente = esPendiente;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void esLeido(){
        apiService.esLido(libro.getIdLibro(), MainActivity.getUsuarioDTO().getIdUsuario()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    boolean esLeido = response.body();
                    if(esLeido){
                        binding.leido.setImageResource(R.drawable.icons8_ojo_64);
                        leido = esLeido;
                    }else{
                        binding.leido.setImageResource(R.drawable.icons8_ojo_cerrado_50);
                        leido = esLeido;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                Toast.makeText(ActividadMasInformacion.this, "Error de red " + throwable, Toast.LENGTH_SHORT).show();
            }
        });
    }
}