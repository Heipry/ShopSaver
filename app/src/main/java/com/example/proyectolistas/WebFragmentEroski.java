package com.example.proyectolistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebFragmentEroski#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragmentEroski extends Fragment {

    private String busqueda;
    public WebFragmentEroski() {
        // Required empty public constructor
    }


    public static WebFragmentEroski newInstance() {
        WebFragmentEroski fragment = new WebFragmentEroski();
        return fragment;
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
        return inflater.inflate(R.layout.fragment_web_eroski, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "https://supermercado.eroski.es/es/search/results/?q="+busqueda;
        WebView web = getView().findViewById(R.id.webViewEroski);
        if (web != null) {
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl(url);
        } else {
            Log.e("WebFragmentBonarea", "El WebView es nulo");
        }
    }
}