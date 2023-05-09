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
import java.util.ArrayList;

/**
 *
 * @author francisco
 */
public class ImpresoraReporteGeneral implements Printable {
    private ArrayList<reporteJuegosTotal> reporteGeneral;
    private String reporteTipo;

    public ImpresoraReporteGeneral(ArrayList<reporteJuegosTotal> papel, String reporteTipo) {
        this.reporteGeneral = papel;
        this.reporteTipo = reporteTipo;
    }

    public ArrayList<reporteJuegosTotal> getReporteGeneral() {
        return reporteGeneral;
    }

    public void setReporteGeneral(ArrayList<reporteJuegosTotal> reporteGeneral) {
        this.reporteGeneral = reporteGeneral;
    }

    public String getReporteTipo() {
        return reporteTipo;
    }

    public void setReporteTipo(String reporteTipo) {
        this.reporteTipo = reporteTipo;
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
                g2d.drawString("         "+reporteTipo,10,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                int i = 0;
                while(i < reporteGeneral.size()){
                    String nombre = reporteGeneral.get(i).getNombreSorteo();
                    int monto = reporteGeneral.get(i).getMonto();
                    int montoReventado = reporteGeneral.get(i).getMontoReventado();
                    montoTotal += (monto + montoReventado);
                    g2d.drawString("  "+nombre+"           ",10,y);y+=yShift;
                    if(montoReventado == 0){
                        g2d.drawString("                      "+monto+"   ",10,y);y+=yShift;
                    }
                    else{
                        g2d.drawString("                      "+monto+"  "+montoReventado,10,y);y+=yShift;
                    }
                    i+=1;
                }
                
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString(" Monto Total:             "+montoTotal,10,y);y+=yShift;
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
