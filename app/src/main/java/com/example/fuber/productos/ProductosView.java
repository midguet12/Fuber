package com.example.fuber.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fuber.R;
import com.example.fuber.databinding.ActivityProductosViewBinding;
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

public class ProductosView extends AppCompatActivity {

    public static final String TOKEN ="token";
    public static final String TAG = "TAG";
    public static final String ID_TIENDA = "idTienda";

    private final OkHttpClient client = new OkHttpClient();
    RecyclerView recyclerView;
    List<Producto> productos;
    Adapter adapter;
    ActivityProductosViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductosViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.listaProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productos = new ArrayList<>();

        Button perfil = findViewById(R.id.perfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast funciona = Toast.makeText(getApplicationContext(), "Funciona", Toast.LENGTH_SHORT);
                funciona.show();

            }
        });

        Request request = new Request.Builder()
                .url("http://themaisonbleue.com:4000/productos/"+ extras.get(ID_TIENDA))
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
                    try {
                        respuesta = new JSONArray(response.body().string());

                        ProductosView.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0; i < respuesta.length(); i++){
                                    try {
                                        JSONObject productoObject = respuesta.getJSONObject(i);
                                        Producto producto = new Producto();
                                        producto.setIdProducto(productoObject.getInt("idProducto"));
                                        producto.setDescripcion(productoObject.getString("descripcion"));
                                        producto.setExistencia(productoObject.getInt("existencia"));
                                        producto.setIdTienda(productoObject.getInt("idTienda"));
                                        producto.setPrecio(productoObject.getDouble("precio"));
                                        producto.setTitulo(productoObject.getString("titulo"));
                                        productos.add(producto);
                                        Log.d(TAG, productoObject.toString());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                adapter = new Adapter(getApplicationContext(), productos);
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