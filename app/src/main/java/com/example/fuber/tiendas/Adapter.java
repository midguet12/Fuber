package com.example.fuber.tiendas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuber.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    LayoutInflater inflater;
    List<Tienda> tiendas;
    String token;
    public Adapter(Context ctx, List<Tienda> tiendas, RecyclerViewInterface recyclerViewInterface, String token){
        this.inflater = LayoutInflater.from(ctx);
        this.tiendas = tiendas;
        this.recyclerViewInterface = recyclerViewInterface;
        this.token = token;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tienda,parent, false);
        return new ViewHolder(view, token, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.nombreTienda.setText(tiendas.get(position).getNombre());
        holder.direccionTienda.setText(tiendas.get(position).getDireccion());
        Picasso.get().load("http://themaisonbleue.com:4080/tienda/" + String.valueOf(tiendas.get(position).idTienda) + ".jpg").into(holder.fotoTienda);
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoTienda;
        TextView nombreTienda, direccionTienda;

        public ViewHolder(@NonNull View itemView, String token, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            fotoTienda = itemView.findViewById(R.id.fotoTienda);
            nombreTienda = itemView.findViewById(R.id.nombreTienda);
            direccionTienda = itemView.findViewById(R.id.direccionTienda);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(token, position);
                        }
                    }
                }
            });

        }
    }

}
