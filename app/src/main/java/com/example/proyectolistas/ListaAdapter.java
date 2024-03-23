package com.example.proyectolistas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// ListaAdapter.java
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ItemViewHolder> {
    private List<String> items;
    private String tiendaNombre;

    public ListaAdapter(List<String> items, String tiendaNombre) {
        this.items = items;
        this.tiendaNombre = tiendaNombre;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.itemName.setText(items.get(position));
        // Aquí puedes configurar acciones como editar o borrar el elemento
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}
