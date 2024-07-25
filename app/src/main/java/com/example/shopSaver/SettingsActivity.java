package com.example.shopSaver;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout layout;
    String tiendaNombre1;
    private EditText editTextUsername1, editTextPassword1;
    private Button buttonLogin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        layout = findViewById(R.id.linear_card1);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING); //give transition type, you can change this based on your need
        editTextUsername1 = findViewById(R.id.editTextUsername1);
        editTextPassword1 = findViewById(R.id.editTextPassword1);
        buttonLogin1 = findViewById(R.id.buttonLogin1);
        tiendaNombre1 = getResources().getString(R.string.SuperName5).replaceAll("\\s+", ""); // Eliminar espacios en blanco para el nombre de la tabla
        loadItemsFromDatabase();
        // Set a click listener for the login button
        buttonLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextUsername1.getText().toString();
                String password = editTextPassword1.getText().toString();

                // Implement save
                DatabaseHelper dbHelper = new DatabaseHelper(SettingsActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String tableName = "Settings";

                // Insertar los nuevos elementos en la tabla
                ContentValues values = new ContentValues();
                values.put("super", tiendaNombre1);
                values.put("user", username);
                values.put("password", password);
                values.put("is_activo", 1);
                String selection = "super" + " LIKE ?";
                String[] selectionArgs = {tiendaNombre1};
                int id =  db.update(tableName, values, selection, selectionArgs);
                if (id == 0) {
                    db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                db.close();
                Toast.makeText(SettingsActivity.this, "user: "+username+" | password: "+password + " guardado", Toast.LENGTH_SHORT).show();
                layout.setVisibility(View.GONE);
            }
        });
    }
    private void loadItemsFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tableName = "Settings";
        Cursor cursor = db.rawQuery("SELECT super, user, password FROM " + tableName, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String superName = cursor.getString(cursor.getColumnIndex("super"));
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                if(superName.equals(tiendaNombre1)){
                    editTextUsername1.setText(user);
                    editTextPassword1.setText(password);
                }
            }
            cursor.close();
        }
        db.close();
    }


    public void expand(View view) { //the function that given to onclick event
        int v = (layout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        layout.setVisibility(v);
    }
    public void returnBack(View view) {
        //onBackPressed();
        finish();
    }
    public void returnSearch(View view) {
        // Crear un intent para volver a la Activity Inicial
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void returnHome(View view) {
        // Crear un intent para volver a la Activity del buscador
        Intent intent = new Intent(SettingsActivity.this, PopupBuscar.class);
        // Configurar la bandera para mantener el estado de la Activity A
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}