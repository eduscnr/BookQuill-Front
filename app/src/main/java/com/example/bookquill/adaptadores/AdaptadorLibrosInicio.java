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
import androidx.cardview.widget.CardView;
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

public class AdaptadorLibrosInicio extends PagingDataAdapter<Libro, AdaptadorLibrosInicio.LibrosViewHolder> {
    public interface OnClickLibro {
        void mostrarInformacionLibro(Libro l);
    }

    private OnClickLibro onClickLibro;
    public AdaptadorLibrosInicio(@NonNull DiffUtil.ItemCallback<Libro> diffCallback, OnClickLibro onClickLibro) {
        super(diffCallback);
        this.onClickLibro = onClickLibro;
    }

    @NonNull
    @Override
    public AdaptadorLibrosInicio.LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libros_recomendados, parent, false);
        return new LibrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorLibrosInicio.LibrosViewHolder holder, int position) {
        Libro l = getItem(position);
        if (l != null) {
            holder.textViewTitulo.setText(l.getTitulo());
            holder.textViewAutor.setText(l.getAutor());
            holder.textViewEstrellas.setText(l.getEstrellas());
            if (l.getUrlImagen().contains("https://images/icons") || l.getUrlImagen().contains("https:/images/icons")) {
                Glide.with(holder.imageViewPortada.getContext())
                        .load(R.drawable.sinportada)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(35)))
                        .into(holder.imageViewPortada);
            } else {
                Glide.with(holder.imageViewPortada.getContext())
                        .load(l.getUrlImagen())
                        .listener(new RequestListener<Drawable>() {
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
                        })
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(35)))
                        .placeholder(R.drawable.ic_imagen)
                        .into(holder.imageViewPortada);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickLibro.mostrarInformacionLibro(l);
                }
            });
        }
    }

    public class LibrosViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPortada;
        private TextView textViewTitulo;
        private TextView textViewAutor;
        private TextView textViewEstrellas;
        private CardView cardView;
        public LibrosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPortada = itemView.findViewById(R.id.portada);
            textViewTitulo = itemView.findViewById(R.id.titulo);
            textViewAutor = itemView.findViewById(R.id.autor);
            textViewEstrellas = itemView.findViewById(R.id.estrellas);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
