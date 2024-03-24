package com.example.ShopSaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class PopupBuscar extends AppCompatActivity {
    TextView btn_ir;
    EditText buscado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_buscar);
        DisplayMetrics medidaVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaVentana);
        int ancho = medidaVentana.widthPixels;
        int alto = medidaVentana.heightPixels;
        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.5));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        buscado = findViewById(R.id.edit);
        btn_ir = findViewById(R.id.txt_ir);
        btn_ir.setOnClickListener(new intentBuscar());
        buscado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    // Aquí ejecutas la acción deseada
                    new intentBuscar().onClick(buscado);
                    return true;
                }
                return false;
            }
        });


    }
    private class intentBuscar implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent busqueda =new Intent(PopupBuscar.this, ResultActivity.class);
            String vBuscado = buscado.getText().toString();
            busqueda.putExtra("termino", vBuscado);
            startActivity(busqueda);
        }
    }
}