package com.example.proyectolistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> tiendas = Arrays.asList("Dia", "Bonarea", "Eroski");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TiendasAdapter adapter = new TiendasAdapter(tiendas, this);
        recyclerView.setAdapter(adapter);
    }
    public void returnBack(View view) {
        //onBackPressed();
        finish();
    }
}

