package com.example.bookquill.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookquill.R;
import com.example.bookquill.modelo.DetalleResenia;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaptadorResenia extends PagingDataAdapter<DetalleResenia, AdaptadorResenia.DetalleReseniaViewHolder> {
    public AdaptadorResenia(@NonNull DiffUtil.ItemCallback<DetalleResenia> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AdaptadorResenia.DetalleReseniaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resenia, parent, false);
        return new DetalleReseniaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorResenia.DetalleReseniaViewHolder holder, int position) {
        DetalleResenia d = getItem(position);
        if(d != null){
            holder.nombreUsuario.setText(d.getNombreUsuario());
            holder.descripcionResenia.setText(d.getDescripcionResenia());
            Date date = d.getFechaResenia();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            holder.fechaResenia.setText(sdf.format(date));
        }

    }

    public class DetalleReseniaViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreUsuario;
        private TextView fechaResenia;
        private TextView descripcionResenia;
        public DetalleReseniaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreUsuario = itemView.findViewById(R.id.usuarioResenia);
            fechaResenia = itemView.findViewById(R.id.fechaPublicada);
            descripcionResenia = itemView.findViewById(R.id.descripcionResenia);
        }
    }
}
