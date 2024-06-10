package com.example.bookquill.comparadores;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.bookquill.modelo.DetalleResenia;
import com.example.bookquill.modelo.Libro;

public class ComparadorResenia extends DiffUtil.ItemCallback<DetalleResenia>{
    @Override
    public boolean areItemsTheSame(@NonNull DetalleResenia oldItem, @NonNull DetalleResenia newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull DetalleResenia oldItem, @NonNull DetalleResenia newItem) {
        return oldItem.equals(newItem);
    }
}
