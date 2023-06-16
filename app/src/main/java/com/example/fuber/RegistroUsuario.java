package com.example.fuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistroUsuario extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    private EditText nombreEditText;
    private EditText correoEditText;
    private EditText celularEditText;
    private EditText  contrasenaEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombreEditText = (EditText) findViewById(R.id.nombreEditText);
        correoEditText = (EditText) findViewById(R.id.correoEditText);
        celularEditText = (EditText) findViewById(R.id.celularEditText);
        contrasenaEditText = (EditText) findViewById(R.id.contrasenaEditText);

        Button btnRegistrar = (Button) findViewById(R.id.buttonRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CamposVacios()){
                    String nombre = nombreEditText.getText().toString();
                    String correo = correoEditText.getText().toString();
                    String celular = celularEditText.getText().toString();
                    String contrasena = contrasenaEditText.getText().toString();


                    RequestBody body = new FormBody.Builder()
                            .add("nombreApellidos",nombre)
                            .add("correo",correo)
                            .add("contrasena",contrasena)
                            .add("celular",celular)
                            .build();



                    Request request = new Request.Builder()
                            .url("http://themaisonbleue.com:4000/usuario")
                            .post(body)
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
                                    RegistroUsuario.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("->>" + response.body().toString());
                                            Toast toast = Toast.makeText(getApplicationContext(), "Se registró " +nombre +response.code(), Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    });
                                }catch (Exception exception){
                                    exception.printStackTrace();
                                }
                            }else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Hubo un error " + response.code(), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });


    }

    private boolean CamposVacios(){
        Boolean estanVacios = true;
        if (nombreEditText.getText().toString().trim().isEmpty() ||
        correoEditText.getText().toString().trim().isEmpty() ||
        celularEditText.getText().toString().trim().isEmpty() ||
        contrasenaEditText.getText().toString().trim().isEmpty()){

            Toast toast = Toast.makeText(this, R.string.llenar_campos, Toast.LENGTH_LONG);
            toast.show();
        }else {
            estanVacios = false;
        }
        return estanVacios;
    }

    private void vaciarCampos(){

    }

}