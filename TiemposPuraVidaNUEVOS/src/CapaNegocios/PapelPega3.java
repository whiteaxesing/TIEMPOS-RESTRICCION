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
public class PapelPega3 {
    private int numeroTicket;
    private String fechaTicket;
    private String nombreSorteo;
    private ArrayList<String> numeros = new ArrayList<>();
    private ArrayList<String> modalidades = new ArrayList<>();
    private ArrayList<Integer> montos = new ArrayList<>();

    public PapelPega3(String fechaTicket, String nombreSorteo) {
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
    
    public ArrayList<Integer> getMontos() {
        return montos;
    }
    
    public void anadirNumeros(String numero) {
        if (numero.length()==1){
            numero = ("00"+numero);
            this.numeros.add(numero);
            return;
        }
        if (numero.length()==2){
            numero = ("0"+numero);
            this.numeros.add(numero);
            return;
        }
        this.numeros.add(numero);
    }

    public void anadirModalidades(String modalidades) {
        this.modalidades.add(modalidades);
    }
    
    public void anadirMonto(int montos) {
        this.montos.add(montos);
    }

    public ArrayList<String> getModalidades() {
        return modalidades;
    }
    
    

    
    
    
    
    
    
    
    
    
}
