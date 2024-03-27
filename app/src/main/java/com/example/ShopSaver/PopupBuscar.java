package com.example.ShopSaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        buscado.requestFocus();
        btn_ir = findViewById(R.id.txt_ir);
        btn_ir.setOnClickListener(new intentBuscar());

        buscado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    new intentBuscar().onClick(buscado);
                    return true;
                }
                return false;
            }
        });
    }

    public void openList(View view) {
        Intent intent = new Intent(this, ListaActivity.class);
        Bundle bundle = new Bundle();
        if (view.getId() == R.id.cardDia) {
            bundle.putString("tienda", "Dia");

        } else if (view.getId() == R.id.cardBonarea) {
            bundle.putString("tienda", "Bonarea");
        }else  {
            bundle.putString("tienda", "Eroski");
        }
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }


    private class intentBuscar implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            int orientation = getResources().getConfiguration().orientation;
            Intent busqueda;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                 busqueda = new Intent(PopupBuscar.this, ResultActivity.class);
            } else {
                 busqueda = new Intent(PopupBuscar.this, ResultSwipeActivity.class);
            }
            String vBuscado = buscado.getText().toString();
            busqueda.putExtra("termino", vBuscado);
            buscado.setText("");
            startActivity(busqueda);
        }
    }


}