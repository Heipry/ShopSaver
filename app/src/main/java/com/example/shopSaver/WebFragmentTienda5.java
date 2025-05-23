package com.example.shopSaver;

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
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class WebFragmentTienda5 extends Fragment {

    private String busqueda;
    private String nombreTienda;

    public WebFragmentTienda5() {
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
        return inflater.inflate(R.layout.fragment_web_tienda5, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "https://v2.lupaonline.com/logrono/catalogsearch/result/?q="+busqueda;
        nombreTienda = getString(R.string.SuperName5);
        WebView web = view.findViewById(R.id.webViewTienda5);
        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.swipeRefreshTienda5);
        if (web != null && swipeRefresh != null) {
            WebViewHelperCSP.configureWebView(web, true, true);
            // Evitar abrir browser al usar buscador
            web.setWebViewClient(new WebViewClient() {

                @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                    web.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    swipeRefresh.setRefreshing(false); // Oculta el indicador de refresh
                }
            });
            swipeRefresh.setOnRefreshListener(() -> {
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                Toast.makeText(requireContext(), "Limpiando cookies y recargando...", Toast.LENGTH_SHORT).show();
                web.reload();
            });
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
            Log.e("WebFragmentTienda5", "El WebView es nulo");
        }
    }
}