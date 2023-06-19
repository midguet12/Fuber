package com.example.fuber.nuevacuenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuber.R;
import com.example.fuber.nuevacuenta.confirmacion.ConfirmacionCelular;

import org.json.JSONException;
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

    private String JSON;
    private String CodigoConfirmacion;


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

                    String json = "{\"nombreApellidos\":\""+nombre+"\",\"correo\":\""+correo+"\",\"contrasena\":\""+contrasena+"\",\"celular\":\""+celular+"\"}";
                    JSON = json;
                    RequestBody bodyJson = RequestBody.create(
                            MediaType.parse("application/json"), "{}");
                    Request request = new Request.Builder()
                            .url("http://themaisonbleue.com:4000/usuariootp/+52"+celular.toString())
                            .post(bodyJson)
                            .build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()){
                                RegistroUsuario.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            CodigoConfirmacion = response.body().string();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (!CodigoConfirmacion.isEmpty()){
                                            cambiarVentana(celular,correo);
                                        }else {
                                            mostrarMensaje();
                                        }
                                    }
                                });
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
        nombreEditText.setText("");
        correoEditText.setText("");
        contrasenaEditText.setText("");
        celularEditText.setText("");
    }

    private void mostrarMensaje(){
        Toast toast = Toast.makeText(this, "No se pudo enviar, intentalo mas tarde", Toast.LENGTH_LONG);
        toast.show();
    }

    private void cambiarVentana(String numero, String correo){
        Toast toast = Toast.makeText(this, "Muy bien, confirma para terminar", Toast.LENGTH_LONG);
        toast.show();

        vaciarCampos();
        Intent intent = new Intent(this, ConfirmacionCelular.class);
        intent.putExtra("JSON_KEY",JSON);
        intent.putExtra("CODIGO_KEY",CodigoConfirmacion);
        intent.putExtra("NUMERO_KEY",numero);
        intent.putExtra("CORREO_KEY",correo);
        startActivity(intent);
    }

}