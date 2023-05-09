/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import java.util.ArrayList;

/**
 *
 * @author francisco
 */
public class PapelTiempos {
    // ticket.id_ticket, sorteo.fecha, nombre  , detalle_ticket.id_numero, monto, monto_reventado 
    private int numeroTicket;
    private String fechaTicket;
    private String nombreSorteo;
    private ArrayList<String> numeros = new ArrayList<>();
    private ArrayList<Integer> montos = new ArrayList<>();
    private ArrayList<Integer> montosReventados = new ArrayList<>();

    public PapelTiempos(int numeroTicket, String fechaTicket, String nombreSorteo) {
        this.numeroTicket = numeroTicket;
        this.fechaTicket = fechaTicket;
        this.nombreSorteo = nombreSorteo;
    }

    public int getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(int numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public String getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(String fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public String getNombreSorteo() {
        return nombreSorteo;
    }

    public void setNombreSorteo(String nombreSorteo) {
        this.nombreSorteo = nombreSorteo;
    }

    public ArrayList<String> getNumeros() {
        return numeros;
    }
    

    public ArrayList<Integer> getMontosReventados() {
        return montosReventados;
    }

    
    public ArrayList<Integer> getMontos() {
        return montos;
    }
    
    public void anadirNumeros(String numero) {
        this.numeros.add(numero);
    }

    public void anadirMonto(int montos) {
        this.montos.add(montos);
    }

    public void anadirMontoReventado(int montos) {
        this.montosReventados.add(montos);
    }
    
    
    
    
    
    
    
    
    
    
}
