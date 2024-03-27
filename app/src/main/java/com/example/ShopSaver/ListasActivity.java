package com.example.ShopSaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class ListasActivity extends AppCompatActivity {
    private final List<String> tiendas = Arrays.asList(getString(R.string.SuperName1), getString(R.string.SuperName2),getString(R.string.SuperName3));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TiendasAdapter adapter = new TiendasAdapter(tiendas, this);
        recyclerView.setAdapter(adapter);
    }
    public void returnBack(View view) {
        //onBackPressed();
        finish();
    }
    public void returnSearch(View view) {
        // Crear un intent para volver a la Activity Inicial
        Intent intent = new Intent(ListasActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void returnHome(View view) {
        // Crear un intent para volver a la Activity del buscador
        Intent intent = new Intent(ListasActivity.this, PopupBuscar.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}

