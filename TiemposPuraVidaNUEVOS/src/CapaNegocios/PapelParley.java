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
public class PapelParley {
    private int numeroParley;
    private String fechaTicket;
    private ArrayList<String> numeros = new ArrayList<>();
    private ArrayList<String> numeros2 = new ArrayList<>();
    private ArrayList<Integer> montos = new ArrayList<>();


    public int getNumeroTicket() {
        return numeroParley;
    }

    public void setNumeroTicket(int numeroTicket) {
        this.numeroParley = numeroTicket;
    }

    public String getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(String fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public ArrayList<String> getNumeros() {
        return numeros;
    }
    
        public ArrayList<String> getNumeros2() {
        return numeros2;
    }
    
    public ArrayList<Integer> getMontos() {
        return montos;
    }
    
    public void anadirNumeros(String numero) {
        if (numero.length()==1){
            numero = ("0"+numero);
        }
        this.numeros.add(numero);
    }
    
     public void anadirNumeros2(String numero2) {
        if (numero2.length()==1){
            numero2 = ("0"+numero2);
        }
        this.numeros2.add(numero2);
    }

    public void anadirMonto(int montos) {
        this.montos.add(montos);
    }

   
    
    
    
    
    
    
    
    
    
    
}
