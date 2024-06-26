package com.example.shopSaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements ListaAdapter.OnItemStateChangedListener {
    private final List<ListItem> items = new ArrayList<>();
    private ListaAdapter adapter;
    private TextView totalItemsTextView;
    private TextView activeItemsTextView;


    String tiendaNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("tienda")) {
            tiendaNombre = bundle.getString("tienda");
        }else{
            tiendaNombre = "Tienda";
        }
        totalItemsTextView = findViewById(R.id.totalItemsTextView);
        activeItemsTextView = findViewById(R.id.activeItemsTextView);
        items.clear();

        if ( savedInstanceState == null && bundle!=null && bundle.containsKey("nuevoElemento")) {
            String nuevoElemento = bundle.getString("nuevoElemento");
            items.add(new ListItem(nuevoElemento, true));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (bundle != null && bundle.containsKey("tienda")) {
            tiendaNombre = bundle.getString("tienda");
        }else{
            tiendaNombre = "Tienda";
        }
        adapter = new ListaAdapter(this,items);
        recyclerView.setAdapter(adapter);
        TextView listNameTextView = findViewById(R.id.listNameTextView);
        listNameTextView.setText(getString(R.string.lista_compra_con_nombre_tienda, tiendaNombre));
        loadItemsFromDatabase(tiendaNombre);
        adapter.setItemStateChangedListener(this);

    }
    private void loadItemsFromDatabase(String tiendaNombre) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
        Cursor cursor = db.rawQuery("SELECT item, is_activo FROM " + tableName, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String itemText = cursor.getString(cursor.getColumnIndex("item"));
                @SuppressLint("Range") boolean isActivo = cursor.getInt(cursor.getColumnIndex("is_activo")) == 1;
                items.add(new ListItem(itemText, isActivo));
            }
            cursor.close();
        }
        db.close();
        updateItemCounts();
        adapter.notifyDataSetChanged();
    }
    private void saveItemsToDatabase(String tiendaNombre, List<ListItem> items) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla

        // Borrar los datos anteriores en la tabla
        db.delete(tableName, null, null);

        // Insertar los nuevos elementos en la tabla
        ContentValues values = new ContentValues();
        for (ListItem item : items) {
            values.put("item", item.getText());
            values.put("is_activo", item.getActivo() ? 1 : 0);
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
        try(DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String tableName = tiendaNombre.replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
            // Borrar los datos anteriores en la tabla
            db.delete(tableName, null, null);
            updateItemCounts();
            adapter.notifyDataSetChanged();
        }

    }
    private void updateItemCounts() {
        int totalItems = items.size();
        int activeItems = 0;
        for (ListItem item : items) {
            if (item.getActivo()) {
                activeItems++;
            }
        }
        String stringTotalesParametro = getResources().getQuantityString(R.plurals.stringTotalesParametro, totalItems, totalItems);
        totalItemsTextView.setText(stringTotalesParametro);
        activeItemsTextView.setText(getString(R.string.stringValidosParametro,activeItems));
    }
    public void onItemStateChanged() {
        // Actualizar los contadores de elementos activos

        updateItemCounts();
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

    public void returnSearch(View view) {
        // Crear un intent para volver a la Activity Inicial
        Intent intent = new Intent(ListaActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    public void returnHome(View view) {
        // Crear un intent para volver a la Activity del buscador
        Intent intent = new Intent(ListaActivity.this, PopupBuscar.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }



}
