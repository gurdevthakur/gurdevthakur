package com.example.loginwithsqllite;

import android.app.Activity;
import android.os.Bundle;

import com.example.loginwithsqllite.database.DatabaseHelper;

public class SignUpActivity extends Activity {
    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupmodulefragment);

    }
}
