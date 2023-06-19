package com.example.fuber.tiendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fuber.R;
import com.example.fuber.databinding.ActivityMainBinding;
import com.example.fuber.databinding.ActivityTiendasBinding;
import com.example.fuber.perfil.PerfilView;
import com.example.fuber.productos.ProductosView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TiendasView extends AppCompatActivity implements RecyclerViewInterface{

    public static final String TOKEN = "token";
    public static final String TAG = "TAG";
    public static final String ID_USUARIO = "";
    private final OkHttpClient client = new OkHttpClient();
    RecyclerView recyclerView;
    List<Tienda> tiendas;
    Adapter adapter;
    ActivityTiendasBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTiendasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.listaTiendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tiendas = new ArrayList<>();

        Button perfil = findViewById(R.id.perfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPerfilActivity(extras.getString(TOKEN), extras.getInt(ID_USUARIO));

            }
        });




        Request request = new Request.Builder()
                .url("http://themaisonbleue.com:4000/tiendas")
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
                JSONArray respuesta;
                if (response.isSuccessful()){
                    try{
                        respuesta = new JSONArray(response.body().string());

                        TiendasView.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < respuesta.length(); i++){
                                    try {
                                        JSONObject tiendaObject = respuesta.getJSONObject(i);
                                        Tienda tienda = new Tienda();
                                        tienda.setNombre(tiendaObject.getString("nombre"));
                                        tienda.setIdTienda(tiendaObject.getInt("idTienda"));
                                        tienda.setDireccion(tiendaObject.getString("direccion"));
                                        tiendas.add(tienda);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter = new Adapter(getApplicationContext(), tiendas, TiendasView.this::onItemClick, extras.getString(TOKEN));
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }

    private void openPerfilActivity(String token, int idUsuario){
        Intent intent = new Intent(this, PerfilView.class);
        intent.putExtra(PerfilView.TOKEN, token);
        intent.putExtra(PerfilView.ID_Usuario,idUsuario);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String token, int position) {

        Intent intent = new Intent(this, ProductosView.class);
        intent.putExtra(ProductosView.TOKEN, token);
        intent.putExtra(ProductosView.ID_TIENDA, tiendas.get(position).idTienda);

        startActivity(intent);


    }
}