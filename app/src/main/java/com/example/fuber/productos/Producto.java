package com.example.fuber.productos;

import java.util.Objects;

public class Producto {
    public int idProducto;
    public String descripcion;
    public int existencia;
    public int idTienda;
    public Double precio;
    public String titulo;

    public Producto() {
    }

    public Producto(int idProducto, String descripcion, int existencia, int idTienda, Double precio, String titulo) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.idTienda = idTienda;
        this.precio = precio;
        this.titulo = titulo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto == producto.idProducto && existencia == producto.existencia && idTienda == producto.idTienda && descripcion.equals(producto.descripcion) && precio.equals(producto.precio) && titulo.equals(producto.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, descripcion, existencia, idTienda, precio, titulo);
    }
}
