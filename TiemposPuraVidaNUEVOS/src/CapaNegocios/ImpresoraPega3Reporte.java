/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;

/**
 *
 * @author francisco
 */
public class ImpresoraPega3Reporte implements Printable {
    private PapelPega3 papel;

    public ImpresoraPega3Reporte(PapelPega3 papel) {
        this.papel = papel;
    }

    public PapelPega3 getPapel() {
        return papel;
    }

    public void setPapel(PapelPega3 papel) {
        this.papel = papel;
    }
    
    public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
    throws PrinterException {   
        int result = NO_SUCH_PAGE;


        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;                    
            double width = pageFormat.getImageableWidth();                               
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
            int montoTotal = 0;

            try{
                int y=20;
                int yShift = 10;
                g2d.setFont(new Font("Monospaced",Font.BOLD,9));
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString("           Tiempos Billonnario       ",10,y);y+=yShift;
                g2d.drawString("           REPORTE PEGA3            ",10,y);y+=yShift;
                g2d.drawString("          Fecha:"+papel.getFechaTicket(),12,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString("          "+ papel.getNombreSorteo(),12,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString("     Numero    Monto    "+"Modalidad"+" ",10,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;int i = 0;
                while(i < papel.getNumeros().size()){
                    String numero = papel.getNumeros().get(i);
                    String modalidad = papel.getModalidades().get(i);
                    int monto = papel.getMontos().get(i);
                    montoTotal += monto;
                    g2d.drawString("     "+numero+"       "+monto+"      "+modalidad,10,y);y+=yShift;
                    i+=1;
                }
                
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString(" Monto Total:          "+montoTotal,10,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                result = PAGE_EXISTS;    
                return result;  
            }
            catch(Exception e){
                e.printStackTrace();
            }
            result = PAGE_EXISTS;    
        }
    return result;    
    }
}
