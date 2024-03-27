package com.example.ShopSaver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.PopupMenu;
import android.widget.Toast;


public class WebFragmentBonarea extends Fragment {




    // TODO: Rename and change types of parameters
    private String busqueda;

    public WebFragmentBonarea() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        busqueda = bundle.getString("termino");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_bonarea, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "https://www.bonarea-online.com/es/shop/find?searchWords="+busqueda;
        WebView web = getView().findViewById(R.id.webViewBonarea);
        if (web != null) {
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl(url);
        } else {
            Log.e("WebFragmentBonarea", "El WebView es nulo");
        }
        web.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                int centerX = 100;
                int centerY = 100;
                PopupMenu webViewContextMenu = new PopupMenu(getContext(), view);
                webViewContextMenu.getMenuInflater().inflate(R.menu.add_to_list, webViewContextMenu.getMenu());

                webViewContextMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.add_to_list) {
                            Toast.makeText(view.getContext(), busqueda + " añadido a Bonarea", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), ListaActivity.class);
                            // Crear un Bundle para enviar datos
                            Bundle bundle = new Bundle();
                            bundle.putString("nuevoElemento", busqueda);
                            bundle.putString("tienda", getString(R.string.SuperName2));
                            // Agregar el Bundle al Intent
                            intent.putExtras(bundle);
                            // Iniciar ListaActivity
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

                webViewContextMenu.show();
                return true;

            }
        });
    }
}