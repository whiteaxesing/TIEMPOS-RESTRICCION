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
public class Sorteo {
    private int id_sorteo;
    private int id_nombre_sorteo;
    private String nombre;
    private String fecha;

    public Sorteo(int id_sorteo, int id_nombre_sorteo, String nombre, String fecha) {
        this.id_sorteo = id_sorteo;
        this.id_nombre_sorteo = id_nombre_sorteo;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public int getId_sorteo() {
        return id_sorteo;
    }

    public void setId_sorteo(int id_sorteo) {
        this.id_sorteo = id_sorteo;
    }

    public int getId_nombre_sorteo() {
        return id_nombre_sorteo;
    }

    public void setId_nombre_sorteo(int id_nombre_sorteo) {
        this.id_nombre_sorteo = id_nombre_sorteo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
}
