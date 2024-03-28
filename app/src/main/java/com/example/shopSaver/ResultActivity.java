package com.example.shopSaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class ResultActivity extends AppCompatActivity {
    String textoInicial;
    Button btnDia, btnBonarea, btnEroski;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
          Bundle busqueda = new Bundle();
          Bundle extras = getIntent().getExtras();
          if (extras != null && extras.containsKey("termino")) {
              textoInicial = extras.getString("termino");
          }else{
              textoInicial = "Item";
          }
          busqueda.putString("termino",textoInicial);
          FragmentManager fragmentManager=this.getSupportFragmentManager();
          androidx.fragment.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
          WebFragmentTienda1 fragmentInicial=new WebFragmentTienda1();
          fragmentInicial.setArguments(busqueda);
          transaction.add(R.id.frameContainer, fragmentInicial);
          transaction.commit();
    }
    public void seleccionarFragmento(View v) {

        Fragment miFragmento;
        String textoBuscado = textoInicial;
        Bundle busqueda = new Bundle();
        // Ponemos todos los botones con color desmarcado
        btnDia = findViewById(R.id.buttonDia);
        btnBonarea = findViewById(R.id.buttonBonarea);
        btnEroski = findViewById(R.id.buttonEroski);
        btnBonarea.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa));
        btnEroski.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa));
        btnDia.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa));
        // Ponemos el clickado con color marcado
        v.setBackgroundColor(ContextCompat.getColor(this, R.color.rosa2));
        busqueda.putString("termino",textoBuscado);
        if (v == btnDia) {
            miFragmento = new WebFragmentTienda1();
        } else if (v == findViewById(R.id.buttonBonarea)) {
            miFragmento = new WebFragmentTienda2();

        } else {
            miFragmento = new WebFragmentTienda3();

        }
        miFragmento.setArguments(busqueda);
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.frameContainer, miFragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void returnBack(View view) {
         //onBackPressed();
        finish();
    }
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Cambiar a la actividad vertical
            Intent intent = new Intent(this, ResultSwipeActivity.class);
            intent.putExtra("termino", textoInicial);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }
}