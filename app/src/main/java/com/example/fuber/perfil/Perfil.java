package com.example.fuber.perfil;

public class Perfil {
    public int idUsuario;
    public String celular;
    public String contrasena;
    public String correo;
    public String nombreApellidos;
    public double saldo;

    public Perfil() {
    }

    public Perfil(int idUsuario, String celular, String contrasena, String correo, String nombreApellidos, double saldo) {
        this.idUsuario = idUsuario;
        this.celular = celular;
        this.contrasena = contrasena;
        this.correo = correo;
        this.nombreApellidos = nombreApellidos;
        this.saldo = saldo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
