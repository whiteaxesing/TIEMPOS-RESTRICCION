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
public class NumeroSaldo {
    private int num;
    private int monto;
    private int montoReventado;

    public NumeroSaldo(int num, int monto, int montoReventado) {
        this.num = num;
        this.monto = monto;
        this.montoReventado = montoReventado;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getMontoReventado() {
        return montoReventado;
    }

    public void setMontoReventado(int montoReventado) {
        this.montoReventado = montoReventado;
    }
    
    
}
