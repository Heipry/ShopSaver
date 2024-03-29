package com.example.shopSaver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private SQLiteDatabase db;
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
        int[] numItems = obtenerTotalItems(tienda);

        holder.numValidos.setText(context.getString(R.string.stringValidosParametro, numItems[1]));
        holder.numTotales.setText(context.getResources().getQuantityString(R.plurals.stringTotalesParametro, numItems[0], numItems[0]));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tienda", tienda);
                intent.putExtras(bundle);
                //Cerramos activity, asi al volver recargaremos contadores
                ((Activity) context).finish();
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
        final TextView numValidos;
        final TextView numTotales;
        final ImageView tiendaImage;


        public TiendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tiendaName = itemView.findViewById(R.id.tienda_name);
            tiendaImage = itemView.findViewById(R.id.tienda_image);
            numValidos = itemView.findViewById(R.id.txt_numValidos);
            numTotales =itemView.findViewById(R.id.txt_numTotales);
        }
    }
    // Método para obtener el contador total de elementos para una tienda dada
    @SuppressLint("Range")
    private int[] obtenerTotalItems(String tienda) {

        int[] contadores = new int[2];
        contadores[0]=0;
        contadores[1]=0;
        try (DatabaseHelper dbHelper = new DatabaseHelper(this.context)) {
            db = dbHelper.getReadableDatabase();
            String tableName = tienda.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
            Cursor cursor = db.rawQuery("SELECT item, is_activo FROM " + tableName, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contadores[0]++;
                    if (cursor.getInt(cursor.getColumnIndex("is_activo")) == 1){
                        contadores[1]++;
                    }
                }
                cursor.close();
            }
        }
        db.close();
    return contadores;
    }


}
