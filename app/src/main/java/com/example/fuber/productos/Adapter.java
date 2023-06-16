package com.example.fuber.productos;

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

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Producto> productos;

    public Adapter(Context ctx, List<Producto> productos) {
        this.inflater = LayoutInflater.from(ctx);
        this.productos = productos;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_productos,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.nombreProducto.setText(productos.get(position).getTitulo());
        holder.descripcionProducto.setText(productos.get(position).getDescripcion());
        Picasso.get().load("http://themaisonbleue.com:4080/producto/" + String.valueOf(productos.get(position).idTienda) + ".jpg").into(holder.fotoProducto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoProducto;
        TextView nombreProducto, descripcionProducto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoProducto = itemView.findViewById(R.id.fotoProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            descripcionProducto = itemView.findViewById(R.id.descripcionProducto);
        }

    }
}
