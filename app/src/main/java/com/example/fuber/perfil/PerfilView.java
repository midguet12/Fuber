package com.example.fuber.perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fuber.R;
import com.example.fuber.productos.Producto;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PerfilView extends AppCompatActivity {

    public static final String TOKEN = "";
    public static String ID_Usuario = "";
    private final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle extras = getIntent().getExtras();

        ImageView fotoPerfil = findViewById(R.id.fotoPerfil);
        TextView nombreApellidos = findViewById(R.id.nombreApellidos);
        TextView correo = findViewById(R.id.correo);
        TextView celular = findViewById(R.id.celular);
        TextView saldo = findViewById(R.id.saldo);
        Button recargarSaldo = findViewById(R.id.recargarSaldo);

        recargarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        Request request = new Request.Builder()
                .url("http://themaisonbleue.com:4000/usuario/"+ extras.get(ID_Usuario))
                .addHeader("Authorization", "Bearer " + extras.getString(TOKEN))
                .get()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject respuesta;
                try {
                    respuesta = new JSONObject(response.body().string());
                    PerfilView.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Perfil perfil = null;
                            try {
                                perfil = new Perfil();
                                perfil.setIdUsuario(respuesta.getInt("idUsuario"));
                                perfil.setCelular(respuesta.getString("celular"));
                                perfil.setContrasena(respuesta.getString("contrasena"));
                                perfil.setCorreo(respuesta.getString("correo"));
                                perfil.setNombreApellidos(respuesta.getString("nombreApellidos"));
                                perfil.setSaldo(respuesta.getDouble("saldo"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            //Picasso.get().load("http://themaisonbleue.com:4080/usuario/5.jpg").into(fotoPerfil);
                            Picasso.get().load("http://themaisonbleue.com:4080/usuario/" + String.valueOf(extras.get(ID_Usuario))+".jpg").into(fotoPerfil);

                            nombreApellidos.setText(perfil.getNombreApellidos());
                            correo.setText(perfil.getCorreo());
                            celular.setText(perfil.getCelular());
                            saldo.setText(String.valueOf(perfil.getSaldo()));

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}