package com.example.loginwithsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginwithsqllite.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(this);
        SQLiteDatabase sb = helper.getWritableDatabase();
        helper.onCreate(sb);

        if (savedInstanceState == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}