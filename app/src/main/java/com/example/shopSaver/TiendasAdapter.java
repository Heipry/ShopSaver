package com.example.shopSaver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TiendasAdapter.java
public class TiendasAdapter extends RecyclerView.Adapter<TiendasAdapter.TiendaViewHolder> {
    private final List<String> tiendas;
    private final Context context;
    private final Map<String, Integer> tiendaImageMap = new HashMap<>();
    public TiendasAdapter(List<String> tiendas, Context context) {
        this.tiendas = tiendas;
        this.context = context;
        tiendaImageMap.put(this.context.getString(R.string.SuperName1), R.drawable.dia);
        tiendaImageMap.put(this.context.getString(R.string.SuperName2), R.drawable.bonarea);
        tiendaImageMap.put(this.context.getString(R.string.SuperName3), R.drawable.eroski);
    }

    @NonNull
    @Override
    public TiendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tienda_item, parent, false);
        return new TiendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaViewHolder holder, final int position) {
        final String tienda = tiendas.get(position);
        holder.tiendaName.setText(tienda);
        Integer imageResourceInteger = tiendaImageMap.get(tienda);
        int imageResourceId = (imageResourceInteger != null) ? imageResourceInteger : 0;

        holder.tiendaImage.setImageResource(imageResourceId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tienda", tienda);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }

    public static class TiendaViewHolder extends RecyclerView.ViewHolder {
        final TextView tiendaName;
        final ImageView tiendaImage;


        public TiendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tiendaName = itemView.findViewById(R.id.tienda_name);
            tiendaImage = itemView.findViewById(R.id.tienda_image);
        }
    }
}
