package com.example.fuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmacionCelular extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    public static String JSON_KEY;
    public static String CODIGO_KEY;
    private static String CELULAR;
    private static String CORREO;
    private EditText numeroEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_celular);

        Button btnRegistrar = (Button) findViewById(R.id.buttonConfirmar);
        numeroEditText = (EditText) findViewById(R.id.NumeroConfirmacion);
        TextView probarCorreo = (TextView) findViewById(R.id.probarOtroCorreo);
        TextView aviso = (TextView) findViewById(R.id.textView_avisoMensaje);

        Bundle extras = getIntent().getExtras();
        JSON_KEY = extras.getString("JSON_KEY");
        CODIGO_KEY = extras.getString("CODIGO_KEY");
        CELULAR = extras.getString("NUMERO_KEY");
        CORREO = extras.getString("CORREO_KEY");

        String nuevoAviso = aviso.getText().toString().replace("##########", CELULAR);
        aviso.setText(nuevoAviso);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!numeroEditText.getText().toString().trim().isEmpty()){
                    String codigo = numeroEditText.getText().toString().trim();
                    if (codigo.equals(CODIGO_KEY)){
                        RegistrarUsuario();
                    } else {
                        numeroEditText.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "Codigo incorrecto", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Codigo vacio, introduce el codigo", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void RegistrarUsuario(){
        RequestBody bodyJson = RequestBody.create(
                MediaType.parse("application/json"), JSON_KEY);

        Request request = new Request.Builder()
                .url("http://themaisonbleue.com:4000/usuario")
                .post(bodyJson)
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
                    try{
                        respuesta = new JSONObject(response.body().string());
                        ConfirmacionCelular.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                numeroEditText.setText("");
                                Toast toast = Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG);
                                toast.show();

                                IrVentanaConfirmacion();

                            }
                        });
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }else {
                    mostrarMensaje();
                }
            }
        });
    }
    private void mostrarMensaje(){
        numeroEditText.setText("");
        Toast toast = Toast.makeText(this, "No se pudo crear, intentalo mas tarde", Toast.LENGTH_LONG);
        toast.show();
    }

    private void IrVentanaConfirmacion(){
        Intent intent = new Intent(this, CuentaConfirmada.class);
        startActivity(intent);
    }
}