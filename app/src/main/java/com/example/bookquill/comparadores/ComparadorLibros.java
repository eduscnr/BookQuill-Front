package com.example.bookquill.comparadores;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.bookquill.modelo.Libro;

public class ComparadorLibros extends DiffUtil.ItemCallback<Libro>{
    @Override
    public boolean areItemsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
        return oldItem.getIdLibro().equals(newItem.getIdLibro());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
        return oldItem.getIdLibro().equals(newItem.getIdLibro());
    }
}
