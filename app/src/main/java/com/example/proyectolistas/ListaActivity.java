package com.example.proyectolistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private List<String> items = new ArrayList<>(); // Obtener estos items de algún lugar
    private RecyclerView recyclerView;
    private ListaAdapter adapter;
    private TextView listNameTextView;
    String tiendaNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("nuevoElemento")) {
            String nuevoElemento = bundle.getString("nuevoElemento");
            items.add(nuevoElemento);
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        tiendaNombre = bundle.getString("tienda");
        adapter = new ListaAdapter(items, tiendaNombre);
        recyclerView.setAdapter(adapter);
        listNameTextView = findViewById(R.id.listNameTextView);
        listNameTextView.setText("Lista Compra "+tiendaNombre);
        loadItemsFromDatabase(tiendaNombre);

    }
    private void loadItemsFromDatabase(String tiendaNombre) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
        Cursor cursor = db.rawQuery("SELECT item FROM " + tableName, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item = cursor.getString(cursor.getColumnIndex("item"));
                items.add(item);
            }
            cursor.close();
        }
        db.close();
        adapter.notifyDataSetChanged();
    }
    private void saveItemsToDatabase(String tiendaNombre, List<String> items) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla

        // Borrar los datos anteriores en la tabla
        db.delete(tableName, null, null);

        // Insertar los nuevos elementos en la tabla
        ContentValues values = new ContentValues();
        for (String item : items) {
            values.put("item", item);
            db.insert(tableName, null, values);
        }
        db.close();
    }
    protected void onPause() {
        super.onPause();
        // Guardar los elementos de la lista en la base de datos cuando la actividad se pausa
        if (tiendaNombre != null) {
            saveItemsToDatabase(tiendaNombre, items);
        }
    }
    public void accionVaciar(View v){
        items.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
        // Borrar los datos anteriores en la tabla
        db.delete(tableName, null, null);
        adapter.notifyDataSetChanged();

    }
    public void returnBack(View view) {
        //onBackPressed();
        if (tiendaNombre != null) {
            saveItemsToDatabase(tiendaNombre, items);
        }
        // Crear un intent para volver a la Activity de listas
        Intent intent = new Intent(ListaActivity.this, ListasActivity.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    public void returnBuscador(View view) {
        if (tiendaNombre != null) {
            saveItemsToDatabase(tiendaNombre, items);
        }
        // Crear un intent para volver a la Activity del buscador
        Intent intent = new Intent(ListaActivity.this, PopupBuscar.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }



}
