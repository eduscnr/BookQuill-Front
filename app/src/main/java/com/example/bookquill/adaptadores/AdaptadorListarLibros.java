package com.example.bookquill.adaptadores;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.bookquill.R;
import com.example.bookquill.modelo.Libro;
import com.willy.ratingbar.ScaleRatingBar;

public class AdaptadorListarLibros extends PagingDataAdapter<Libro, AdaptadorListarLibros.LibrosViewHolder> {
    public AdaptadorListarLibros(@NonNull DiffUtil.ItemCallback<Libro> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AdaptadorListarLibros.LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listas_libros, parent, false);
        return new LibrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListarLibros.LibrosViewHolder holder, int position) {
        Libro l = getItem(position);
        if (l != null) {
            holder.textViewTitulo.setText(l.getTitulo());
            holder.textViewAutor.setText(l.getAutor());
            holder.textViewPopularidad.setText(l.getEstrellas());
            float popularidad = Float.parseFloat(l.getEstrellas());
            holder.estrellas.setRating(popularidad);
            if (l.getUrlImagen().contains("https://images/icons") || l.getUrlImagen().contains("https:/images/icons")) {
                Glide.with(holder.imageViewPortada.getContext()).load(R.drawable.sinportada).apply(RequestOptions.bitmapTransform(new RoundedCorners(35))).into(holder.imageViewPortada);
            } else {
                Glide.with(holder.imageViewPortada.getContext()).load(l.getUrlImagen()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        // Escalar la imagen al tamaño deseado
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 120, 210, false);
                        holder.imageViewPortada.setImageBitmap(scaledBitmap);
                        return true;
                    }
                }).apply(RequestOptions.bitmapTransform(new RoundedCorners(35))).into(holder.imageViewPortada);
            }
        }
    }

    public class LibrosViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitulo;
        private TextView textViewAutor;
        private TextView textViewPopularidad;
        private ImageView imageViewPortada;
        private ScaleRatingBar estrellas;

        public LibrosViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.titulo);
            textViewAutor = itemView.findViewById(R.id.autorTextView);
            textViewPopularidad = itemView.findViewById(R.id.popularidadTextView);
            imageViewPortada = itemView.findViewById(R.id.portada);
            estrellas = itemView.findViewById(R.id.popularidad);
        }
    }
}
