package com.example.fuber.tiendas;

import java.util.Objects;

public class Tienda {
    public int idTienda;
    public String direccion;
    public int idUsuario;
    public String nombre;

    public Tienda() {
    }

    public Tienda(int idTienda, String direccion, int idUsuario, String nombre) {
        this.idTienda = idTienda;
        this.direccion = direccion;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tienda tienda = (Tienda) o;
        return idTienda == tienda.idTienda && idUsuario == tienda.idUsuario && direccion.equals(tienda.direccion) && nombre.equals(tienda.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTienda, direccion, idUsuario, nombre);
    }


}
