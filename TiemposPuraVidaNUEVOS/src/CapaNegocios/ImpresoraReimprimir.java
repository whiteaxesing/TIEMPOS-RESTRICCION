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
public class ImpresoraReimprimir implements Printable {
    private PapelTiempos papel;

    public ImpresoraReimprimir(PapelTiempos papel) {
        this.papel = papel;
    }

    public PapelTiempos getPapel() {
        return papel;
    }

    public void setPapel(PapelTiempos papel) {
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
                g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString("         Tiempos Billonnario       ",10,y);y+=yShift;
                g2d.drawString("          RE-IMPRESION       ",10,y);y+=yShift;
                g2d.drawString("          Ticket: #"+papel.getNumeroTicket(),12,y);y+=yShift;
                g2d.drawString("        Fecha:"+papel.getFechaTicket(),12,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                
                g2d.drawString("          "+papel.getNombreSorteo(),12,y);y+=yShift;
                if(papel.getNombreSorteo().contains("Reventado")){
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("     "+"Numero"+"   "+"Monto"+"   "+"Reventado",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    int i = 0;
                    while(i < papel.getNumeros().size()){
                        String numero = papel.getNumeros().get(i);
                        int monto = papel.getMontos().get(i);
                        int montoReventado = papel.getMontosReventados().get(i);
                        montoTotal += (montoReventado+ monto);
                        
                        g2d.setFont(new Font("Monospaced",Font.BOLD,12));
                        if (papel.getNumeros().get(i).length()==1){
                            g2d.drawString("     0"+numero+"      "+monto+"     "+montoReventado,10,y);y+=yShift;
                        }
                        else{
                            g2d.drawString("      "+numero+"      "+monto+"     "+montoReventado,10,y);y+=yShift;
                        }
                        i+=1;
                    }
                    
                    g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString(" Monto Total:           "+montoTotal,10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("       Gracias por su compra         ",10,y);y+=yShift;
                    g2d.drawString("        No se paga sin papel         ",10,y);y+=yShift;
                    g2d.drawString("      Tiene 6 días para cobrar       ",10,y);y+=yShift;
                    g2d.drawString("       Se paga al primero x80        ",10,y);y+=yShift;
                    g2d.drawString("           Reventado x210            ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                }
                else{
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("       "+"Numero"+"      "+"Monto"+" ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;int i = 0;
                    while(i < papel.getNumeros().size()){
                        String numero = papel.getNumeros().get(i);
                        int monto = papel.getMontos().get(i);
                        montoTotal += monto;
                        
                        g2d.setFont(new Font("Monospaced",Font.BOLD,12));
                        if (papel.getNumeros().get(i).length()==1){
                            g2d.drawString("      0"+numero+"        "+monto,10,y);y+=yShift;
                        }
                        else{
                            g2d.drawString("       "+numero+"        "+monto,10,y);y+=yShift;
                        }
                        i+=1;
                    }
                    
                    g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString(" Monto Total:           "+montoTotal,10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("       Gracias por su compra         ",10,y);y+=yShift;
                    g2d.drawString("        No se paga sin papel         ",10,y);y+=yShift;
                    g2d.drawString("      Tiene 6 días para cobrar       ",10,y);y+=yShift;
                    g2d.drawString("       Se paga al primero x85        ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                }
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
