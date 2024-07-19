package com.example.shopSaver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.webkit.CookieManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupMenu;
import android.widget.Toast;

public class WebFragmentTienda4 extends Fragment {

    private String busqueda;
    private String nombreTienda;

    public WebFragmentTienda4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        busqueda = bundle != null ? bundle.getString("termino") : "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_tienda4, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "https://tienda.mercadona.es/search-results?query="+busqueda;
        nombreTienda = getString(R.string.SuperName4);
        WebView web = view.findViewById(R.id.webViewTienda4);
        if (web != null) {
            // Si la web no se visualizase correctamente sin js habría que activar la siguiente linea. sin js es más seguro
            // La web no se visualiza correctamente sin js (no se ven las imágenes)). sin js sería más seguro y quitariamos el aviso de cookies
            //Vamos a habilitar JS cargando CSP
            WebViewHelperCSP.configureWebView(web, true, true);
            // Permitir el uso de cookies
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            // Configurar la cookie
            String cookieString = "_mo_da={\"warehouse\":\"4671\",\"postalCode\":\"26580\"}; Domain=tienda.mercadona.es";
            cookieManager.setCookie("https://tienda.mercadona.es", cookieString);
            // Codigo para Eliminar barra lateral
            web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // Ejecutar JavaScript para ocultar el elemento con clase grid-layout__sidebar
                    web.loadUrl("javascript:(function() { " +
                            "var element = document.getElementsByClassName('grid-layout__sidebar')[0];" +
                            "if (element) {" +
                            "element.style.display='none';" +
                            "}" +
                            "document.body.style.zoom = '0.9';" +  // Ajustar el zoom de la página
                            "})()");
                }
            });


        // Configurar el WebViewClient para abrir las URLs en el WebView
            web.loadUrl(url);

            web.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu webViewContextMenu = new PopupMenu(getContext(), view);
                    webViewContextMenu.getMenuInflater().inflate(R.menu.add_to_list, webViewContextMenu.getMenu());

                    webViewContextMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.add_to_list) {
                                Toast.makeText(view.getContext(), getString(R.string.AddString, busqueda, nombreTienda), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ListaActivity.class);
                                // Crear un Bundle para enviar datos
                                Bundle bundle = new Bundle();
                                bundle.putString("nuevoElemento", busqueda);
                                bundle.putString("tienda", nombreTienda);
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
        } else {
            Log.e("WebFragmentTienda4", "El WebView es nulo");
        }
    }
}