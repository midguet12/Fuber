package com.example.fuber;

import org.json.*;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuber.tiendas.TiendasView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final String TAG ="DEMO";


    ImageView imageView;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.logo);
        drawable = getResources().getDrawable(R.drawable.fuber);
        imageView.setImageDrawable(drawable);

        Button iniciarSesion = (Button) findViewById(R.id.iniciarSesion);
        TextView noTienesCuenta = (TextView) findViewById(R.id.noTienesCuentaTextView);
        EditText contrasenaEditText = (EditText) findViewById(R.id.contrasenaEditText);
        EditText usuarioEditText = (EditText) findViewById(R.id.usuarioEditText);
        //String token = "";





        iniciarSesion.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                String usuario = usuarioEditText.getText().toString();
                String contrasena = contrasenaEditText.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.1.76:4000/usuario/iniciarsesion/" + usuario.toString() + "&" + contrasena.toString())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        JSONObject respuesta;

                        if(response.isSuccessful()){
                            try {
                                respuesta = new JSONObject(response.body().string());
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            String token = respuesta.getString("token");
                                            if (token.equals("no")){
                                                Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                                            } else {
                                                openResultActivity(token);
                                            }


                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        noTienesCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Funciona");
            }
        });

    }
    private void openResultActivity(String token) {
        Intent intent = new Intent(this, TiendasView.class);
        intent.putExtra(TiendasView.TOKEN, token);
        startActivity(intent);

    }

}