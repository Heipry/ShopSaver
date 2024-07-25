package com.example.shopSaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    WebView wvLupa;
    String my_username = "";
    String my_password = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login_lupa);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wvLupa = findViewById(R.id.wv_lupa);
                String url="https://www.lupaonline.com/logrono/customer/account/login/";
                wvLupa.loadUrl(url);
                wvLupa.getSettings().setJavaScriptEnabled(true);
                String nombreTienda = (String) btnLogin.getText();
                loadItemsFromDatabase(nombreTienda);
                String js = "javascript:document.getElementById('email').value='"+my_username+"';" +"document.getElementById('pass').value='"+my_password+"';"+
                        "document.getElementById('send2').click()";

                wvLupa.setWebViewClient(new WebViewClient(){
                Integer count=0;
                    public void onPageFinished(WebView view, String url){

                        if (count==0){
                            view.evaluateJavascript(js, new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    Toast.makeText(LoginActivity.this, "Por favor, comprueba que el login funcionó", Toast.LENGTH_SHORT).show();
                                    count++;

                                }
                            });
                        }


                    }

                });
            }
        });
    }
    public void returnBack(View view) {
        //onBackPressed();
        finish();
    }
    public void returnSearch(View view) {
        // Crear un intent para volver a la Activity Inicial
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void returnHome(View view) {
        // Crear un intent para volver a la Activity del buscador
        Intent intent = new Intent(LoginActivity.this, PopupBuscar.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    private void loadItemsFromDatabase(String tienda) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = "Settings";
        Cursor cursor = db.rawQuery("SELECT super, user, password FROM " + tableName, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String superName = cursor.getString(cursor.getColumnIndex("super"));
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                if(superName.equals(tienda)){
                    my_username= user;
                    my_password = password;
                }
            }
            cursor.close();
        }
        db.close();
    }
}