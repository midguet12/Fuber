package com.example.fuber.tiendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fuber.R;
import com.example.fuber.databinding.ActivityMainBinding;
import com.example.fuber.databinding.ActivityTiendasBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TiendasView extends AppCompatActivity {

    public static final String TOKEN = "token";
    public static final String TAG = "TAG";
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
                Toast funciona = Toast.makeText(getApplicationContext(), "Funciona", Toast.LENGTH_SHORT);
                funciona.show();

            }
        });




        Request request = new Request.Builder()
                .url("http://192.168.1.76:4000/tiendas")
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
                                //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                adapter = new Adapter(getApplicationContext(), tiendas);
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
}