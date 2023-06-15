package com.example.fuber.tiendas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fuber.R;

public class Tiendas extends AppCompatActivity {

    public static final String TOKEN = "token";
    private final String TAG ="DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendas);

        Bundle extras = getIntent().getExtras();
        String token = extras.getString(TOKEN);
    }
}