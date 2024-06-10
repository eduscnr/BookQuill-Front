package com.example.bookquill.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.bookquill.R;
import com.example.bookquill.modelo.Genero;

import java.util.List;

public class GenerosAdaptador extends BaseAdapter {
    private Context context;
    private List<Genero> tipos;
    private int[] iconos;

    public GenerosAdaptador(Context context, List<Genero> tipos, int[] iconos) {
        this.context = context;
        this.tipos = tipos;
        this.iconos = iconos;
    }

    @Override
    public int getCount() {
        return tipos.size();
    }

    @Override
    public Object getItem(int i) {
        return tipos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.item_generos, null);
        TextView textView = gridView.findViewById(R.id.text_genre);
        textView.setText(tipos.get(i).getTipo());
        ImageView imageView = gridView.findViewById(R.id.image_genre);
        imageView.setImageResource(iconos[i]);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,8,8,8);
        gridView.setLayoutParams(layoutParams);
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_hostfragment);
                Bundle bundle = new Bundle();
                bundle.putString("tipo", textView.getText().toString());
                navController.navigate(R.id.action_fragmento_busqueda_to_fragmento_filtrada, bundle,new NavOptions.Builder()
                        .setPopUpTo(R.id.buscar, true)
                        .build());
            }
        });
        return gridView;
    }
}
