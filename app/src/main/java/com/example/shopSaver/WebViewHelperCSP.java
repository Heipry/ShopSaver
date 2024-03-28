package com.example.shopSaver;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewHelperCSP {

    public static void configureWebView(WebView webView, Boolean javascript) {
        WebSettings webSettings = webView.getSettings();
        // Configuración de la Content Security Policy (CSP)
        if (javascript){
            webSettings.setJavaScriptEnabled(true); // Habilita JavaScript
        }
        webSettings.setAllowContentAccess(false); // Deshabilita el acceso al contenido
        webSettings.setAllowFileAccess(false); // Deshabilita el acceso a archivos
        webSettings.setBlockNetworkLoads(false); // Permite la carga de recursos de red
        webSettings.setAllowFileAccessFromFileURLs(false); // Permite el acceso a archivos desde URL
        webSettings.setAllowUniversalAccessFromFileURLs(false); // Permite el acceso universal desde URL
        webSettings.setSupportZoom(false); // Habilita el zoom
        webSettings.setBuiltInZoomControls(false); // Habilita los controles de zoom integrados
        webSettings.setDisplayZoomControls(true); // Oculta los controles de zoom
        webSettings.setUseWideViewPort(true); // Utiliza el ancho del viewport
        webSettings.setLoadWithOverviewMode(true); // Carga con el modo de vista general
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // Habilita la apertura automática de ventanas emergentes
        webSettings.setMediaPlaybackRequiresUserGesture(true); // Requiere gesto de usuario para reproducir contenido multimedia
        webSettings.setSupportMultipleWindows(false); // Habilita el soporte para múltiples ventanas
        webSettings.setDatabaseEnabled(false); // Habilita el almacenamiento en caché de la aplicación
        webSettings.setGeolocationEnabled(false); // Habilita la geolocalización
        webSettings.setUserAgentString("custom-agent-string"); // Establece una cadena de agente de usuario personalizada
        webSettings.setTextZoom(100); // Establece el zoom de texto al 100%
        webSettings.setStandardFontFamily("sans-serif"); // Establece la familia de fuentes estándar
        webSettings.setSafeBrowsingEnabled(true); // Habilita la navegación segura en dispositivos con Android 8.0 o superior

        webSettings.setMinimumFontSize(8); // Establece el tamaño de fuente mínimo
    }

}
