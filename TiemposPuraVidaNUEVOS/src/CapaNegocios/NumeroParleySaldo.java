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
public class NumeroParleySaldo {
    private int num;
    private int num2;
    private int monto;

    public NumeroParleySaldo(int numero, int numero2, int monto) {
        this.num = numero;
        this.num2 = numero2;
        this.monto = monto;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }


}
