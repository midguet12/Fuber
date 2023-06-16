package com.example.fuber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CuentaConfirmada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_confirmada);

        TextView irMenuTextView = (TextView) findViewById(R.id.irMenuTextView);

        irMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrAlInicio();
            }
        });
    }

    private void IrAlInicio(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}