package com.example.shopSaver;

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

public class PopupBuscar extends AppCompatActivity {
    TextView btn_ir;
    EditText buscado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_buscar);
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics medidaVentana = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(medidaVentana);
        int ancho = medidaVentana.widthPixels;
        int alto = medidaVentana.heightPixels;
        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.85));
        int altoDP = (int) (alto / getResources().getDisplayMetrics().density);
        if (altoDP < 800) {
            getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.85));
        } else {
            getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.5));
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        buscado = findViewById(R.id.edit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        buscado.requestFocus();
        btn_ir = findViewById(R.id.txt_ir);
        btn_ir.setOnClickListener(intentBuscar);

        buscado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    intentBuscar.onClick(buscado);
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
            bundle.putString("tienda", getString(R.string.SuperName1));

        } else if (view.getId() == R.id.cardBonarea) {
            bundle.putString("tienda", getString(R.string.SuperName2));
        }else if (view.getId() == R.id.cardEroski) {
            bundle.putString("tienda", getString(R.string.SuperName3));
        }else {
            bundle.putString("tienda", getString(R.string.SuperName4));
        }
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    private final View.OnClickListener intentBuscar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int orientation = getResources().getConfiguration().orientation;
            Intent busqueda;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                busqueda = new Intent(PopupBuscar.this, ResultActivity.class);
            } else {
                busqueda = new Intent(PopupBuscar.this, ResultSwipeActivity.class);
            }
            String vBuscado = buscado.getText().toString().trim();
            busqueda.putExtra("termino", vBuscado);
            buscado.setText("");
            startActivity(busqueda);
        }
    };




}