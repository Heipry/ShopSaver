package com.example.shopSaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnGuardarCredenciales;
    WebView wvLupa;
    String my_username = "";
    String my_password = "" ;
    String nombreTienda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login_lupa);
        btnGuardarCredenciales= findViewById(R.id.btn_guardar_credenciales);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wvLupa = findViewById(R.id.wv_lupa);
                String url="https://v2.lupaonline.com/logrono/customer/account/login/";
                wvLupa.loadUrl(url);
                wvLupa.getSettings().setJavaScriptEnabled(true);
                nombreTienda = (String) btnLogin.getText();
                loadItemsFromDatabase(nombreTienda);
                String js = "javascript:document.getElementById('email').value='"+my_username+"';" +"document.getElementById('pass').value='"+my_password+"';"+
                        "document.getElementById('send2').click()";

                wvLupa.setWebViewClient(new WebViewClient(){
                Integer count=0;
                    public void onPageFinished(WebView view, String url){
                        btnGuardarCredenciales.setVisibility(View.VISIBLE);
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

        btnGuardarCredenciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String js = "javascript:(function() {" +
                        "var user = document.getElementById('email') ? document.getElementById('email').value : '';" +
                        "var pass = document.getElementById('pass') ? document.getElementById('pass').value : '';" +
                        "return user + '|' + pass;" +
                        "})()";

                wvLupa.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        value = value.replaceAll("^\"|\"$", ""); // eliminar comillas
                        String[] parts = value.split("\\|");

                        if (parts.length == 2 && !parts[0].isEmpty()) {
                            my_username = parts[0];
                            my_password = parts[1];

                            String nombreTienda = btnLogin.getText().toString();
                            saveCredentialsToDatabase(nombreTienda, my_username, my_password);

                            Toast.makeText(LoginActivity.this, "Credenciales guardadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "No se pudieron recuperar los datos del formulario", Toast.LENGTH_SHORT).show();
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
    public void saveCredentialsToDatabase(String superName, String username, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Primero verificar si ya existe un registro para ese superName
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Settings WHERE super = ?", new String[]{superName});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count > 0) {
            // Actualizar registro existente
            String sqlUpdate = "UPDATE Settings SET user = ?, password = ? WHERE super = ?";
            db.execSQL(sqlUpdate, new Object[]{username, password, superName});
        } else {
            // Insertar nuevo registro
            String sqlInsert = "INSERT INTO Settings (super, user, password) VALUES (?, ?, ?)";
            db.execSQL(sqlInsert, new Object[]{superName, username, password});
        }

        db.close();
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