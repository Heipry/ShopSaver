package com.example.proyectolistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class ResultActivity extends AppCompatActivity {
    String textoInicial;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
          Bundle busqueda = new Bundle();
          textoInicial = getIntent().getExtras().getString("termino");
          busqueda.putString("termino",textoInicial);

          FragmentManager fragmentManager=this.getSupportFragmentManager();
          androidx.fragment.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
          WebFragmentDia fragmentInicial=new WebFragmentDia();
          fragmentInicial.setArguments(busqueda);
          transaction.add(R.id.frameContainer, fragmentInicial);
          transaction.commit();
    }
    public void seleccionarFragmento(View v) {

        Fragment miFragmento;
        String textoBuscado = textoInicial;
        Bundle busqueda = new Bundle();
        busqueda.putString("termino",textoBuscado);
        if (v == findViewById(R.id.buttonDia)) {
            miFragmento = new WebFragmentDia();

        } else if (v == findViewById(R.id.buttonBonarea)) {
            miFragmento = new WebFragmentBonarea();

        } else {
            miFragmento = new WebFragmentEroski();

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
}