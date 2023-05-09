/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

/**
 *
 * @author francisco
 */
public class Usuario {
    private int id_usuario;
    private String nombre;
    private String contrasena;

    public Usuario(int idUsuario, String nombre, String contrasena) {
        this.id_usuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
    
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
}
