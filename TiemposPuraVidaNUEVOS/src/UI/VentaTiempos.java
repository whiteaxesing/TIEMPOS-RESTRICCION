/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import CapaNegocios.ConexionBase;
import CapaNegocios.ImpresoraBorrarPapel;
import CapaNegocios.ImpresoraReimprimir;
import CapaNegocios.ImpresoraReporteGeneral;
import CapaNegocios.NombreSorteo;
import CapaNegocios.NumeroSaldo;
import CapaNegocios.PapelTiempos;
import CapaNegocios.Sorteo;
import CapaNegocios.reporteJuegosTotal;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 *
 * @author francisco
 */
public class VentaTiempos extends javax.swing.JFrame {

    
    public VentaTiempos() {
        initComponents();
        textFieldEnteros();
        llenarComboBox();
        this.getContentPane().setBackground(Color.white);
        String dianuevo = Integer.toString(dia);
        if (dia < 10){
            dianuevo = String.format("%02d", dia);
        }
        
        if (mes < 10){
            String mesTexto = String.format("%02d", mes);
            fechaHoy = (anno+"-"+mesTexto+"-"+dianuevo);
            fechaLabel.setText(fechaHoy);
        }
        else{
            fechaHoy = (anno+"-"+mes+"-"+dianuevo);
            fechaLabel.setText(fechaHoy);
        }
        txtNum.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumero();
                   }
                }
           }
        );
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumero();
                   }
                }
           }
        );
        txtReventado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumero();
                   }
                }
           }
        );
        txtNum.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_MULTIPLY) {
                    Toolkit.getDefaultToolkit().beep();
                    venta();
                   }
                }
           }
        );
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_MULTIPLY) {
                    Toolkit.getDefaultToolkit().beep();
                    venta();   
                   }
                }
           }
        );
        txtReventado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_MULTIPLY) {
                    Toolkit.getDefaultToolkit().beep();
                    venta(); 
                   }
                }
           }
        );
    }
    
    public void llenarComboBox(){
        ArrayList<String> listaSorteos = new ArrayList<>();
        ConexionBase conexion = new ConexionBase();
        listaSorteos = conexion.getNombreSorteos();
        for (int i = 0; i < listaSorteos.size(); i++){
                SorteoCombo.addItem(listaSorteos.get(i));;
        }
    }
    
    public void crearTicket(){
        String textoCombo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());
        
        String fecha = fechaLabel.getText();
        for (int i = 0; i < listaNumeros.size(); i++){
                numeroPapel.add(String.valueOf(listaNumeros.get(i).getNum()));
                montoPapel.add(String.valueOf(listaNumeros.get(i).getMonto()));
                if(textoCombo.contains("Reventado")){
                    reventadoPapel.add(String.valueOf(listaNumeros.get(i).getMontoReventado()));
                    montoTotalPapel += (listaNumeros.get(i).getMontoReventado());
                }
                montoTotalPapel += (listaNumeros.get(i).getMonto());
        }
        
        ConexionBase conexion  = new ConexionBase();
        ArrayList<Sorteo> listaSorteos = conexion.getSorteos();
        for (int i = 0; i < listaSorteos.size(); i++){
            if((listaSorteos.get(i).getNombre().equals(textoCombo)) && (listaSorteos.get(i).getFecha().equals(fecha))){
                conexion.crearTicket(listaSorteos.get(i).getId_sorteo(),
                                    montoTotalPapel, nombreCliente,fechaHoy);
                for (int j = 0; j < listaNumeros.size(); j++ ){
                    if(textoCombo.contains("Reventado"))
                        conexion.crearDetalleTicket(listaNumeros.get(j).getNum(),
                                            listaNumeros.get(j).getMonto(),
                                            listaNumeros.get(j).getMontoReventado());
                    else
                        conexion.crearDetalleTicket(listaNumeros.get(j).getNum(),
                                            listaNumeros.get(j).getMonto(),0);
                }

            }
        }
    }
    
    public static String nombreCliente = "";
    public static String fechaHoy = "";
    ArrayList<String> numeroPapel = new ArrayList<>();
    ArrayList<String> montoPapel = new ArrayList<>();
    ArrayList<String> reventadoPapel = new ArrayList<>();
    int montoTotalPapel = 0;
    int montoTotalPantalla = 0;
    LocalDateTime ahora = LocalDateTime.now();
    int anno = ahora.getYear();
    int mes = ahora.getMonthValue();
    int dia = ahora.getDayOfMonth();
    

    
    //------------------Codigo impresora POS------------------------------------
    Double balance=0.0;
    Double bHeight=0.0;
    
    public PageFormat getPageFormat(PrinterJob pj){
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double bodyHeight = bHeight;  
    double headerHeight = 5.0;                  
    double footerHeight = 5.0;        
    double width = cm_to_pp(8); 
    double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(0,10,width,height - cm_to_pp(1));  
            
    pf.setOrientation(PageFormat.PORTRAIT);  
    pf.setPaper(paper);    

    return pf;
    }
   
    protected static double cm_to_pp(double cm){            
	        return toPPI(cm * 0.393600787);            
    }
    
    protected static double toPPI(double inch){            
	        return inch * 72d;            
    }
    
    public class FacturaTiempos implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
        throws PrinterException {   
            int r= numeroPapel.size();
            int result = NO_SUCH_PAGE;
            

            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;                    
                double width = pageFormat.getImageableWidth();                               
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

            // FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try{
                    int y=20;
                    int yShift = 10;
                    int headerRectHeight=15;
                    // int headerRectHeighta=40;
                    LocalDateTime ahora = LocalDateTime.now();
                    int año = ahora.getYear();
                    int mes = ahora.getMonthValue();
                    int dia = ahora.getDayOfMonth();
                    int hora = ahora.getHour();
                    int minutos = ahora.getMinute();
                    int segundos = ahora.getSecond();
                    String fecha = (dia+"-"+mes+"-"+año);
                    String tiempo = (hora+":"+minutos+":"+segundos);
                    String nombreSorteo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());;
                    ConexionBase conexion = new ConexionBase();
                    ArrayList<Integer> tickets = conexion.getUltimoIdTicket();;
                    int numTicket = tickets.get(0);
                    g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("         Tiempos Billonnario       ",10,y);y+=yShift;
                    g2d.drawString("          Ticket: #"+numTicket,12,y);y+=yShift;
                    g2d.drawString("          "+fecha+" "+tiempo,12,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("          "+nombreSorteo,12,y);y+=yShift;
                    if(nombreSorteo.contains("Reventado")){
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString("     "+"Numero"+"   "+"Monto"+"   "+"Reventado",10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.setFont(new Font("Monospaced",Font.BOLD,12));
                        for(int s=0; s<r; s++){
                            if (numeroPapel.get(s).length()==1){
                                g2d.drawString("     0"+numeroPapel.get(s)+"      "+montoPapel.get(s)+"     "+reventadoPapel.get(s),10,y);y+=yShift;
                            }
                            else{
                                g2d.drawString("     "+numeroPapel.get(s)+"      "+montoPapel.get(s)+"     "+reventadoPapel.get(s),10,y);y+=yShift;
                            }
                        }
                        g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString(" Monto Total:        "+montoTotalPapel,10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString("       Gracias por su compra         ",10,y);y+=yShift;
                        g2d.drawString("        No se paga sin papel         ",10,y);y+=yShift;
                        g2d.drawString("      Tiene 6 días para cobrar       ",10,y);y+=yShift;
                        g2d.drawString("       Se paga al primero x85        ",10,y);y+=yShift;
                        g2d.drawString("           Reventado x210            ",10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    }
                    else{
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString("       "+"Numero"+"      "+"Monto"+" ",10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.setFont(new Font("Monospaced",Font.BOLD,12));
                        for(int s=0; s<r; s++){
                            if (numeroPapel.get(s).length()==1){
                                g2d.drawString("      0"+numeroPapel.get(s)+"        "+montoPapel.get(s),10,y);y+=yShift;
                            }
                            else{
                                g2d.drawString("      "+numeroPapel.get(s)+"        "+montoPapel.get(s),10,y);y+=yShift;
                            }
                        }
                        g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString(" Monto Total:          "+montoTotalPapel,10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString("       Gracias por su compra         ",10,y);y+=yShift;
                        g2d.drawString("        No se paga sin papel         ",10,y);y+=yShift;
                        g2d.drawString("      Tiene 6 días para cobrar       ",10,y);y+=yShift;
                        g2d.drawString("       Se paga al primero x90        ",10,y);y+=yShift;
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
    
    public class ReporteTiempos implements Printable {
        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
        throws PrinterException {
            int result = NO_SUCH_PAGE;
            

            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;                    
                double width = pageFormat.getImageableWidth();                               
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

            // FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try{
                    int y=20;
                    int yShift = 10;
                    int headerRectHeight=15;
                    // int headerRectHeighta=40;
                    LocalDateTime ahora = LocalDateTime.now();
                    String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
                    String nombreSorteo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());;
                    ConexionBase conexion  = new ConexionBase();
                    ArrayList<NumeroSaldo> listaSorteos = conexion.getReporteSorteo(nombreSorteo, fecha);
                    int i = 0;
                    int montoTotal = 0;
                    int montoTotalReventado = 0;
                    
                    
                    g2d.setFont(new Font("Monospaced",Font.BOLD,9));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("           Tiempos Billonnario       ",10,y);y+=yShift;
                    g2d.drawString("        Reporte: "+nombreSorteo,10,y);y+=yShift;
                    g2d.drawString("            "+fecha,12,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    if(nombreSorteo.contains("Reventado")){
                        g2d.drawString("        "+"Numero"+"   "+"Monto"+"   "+"Reventado",10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        while(i < listaSorteos.size()) {
                            NumeroSaldo numero = listaSorteos.get(i);
                            if (numero.getNum()<10){
                                g2d.drawString("          0"+numero.getNum()+"     "+numero.getMonto()+"     "+numero.getMontoReventado(),12,y);y+=yShift;
                            }
                            else{
                               g2d.drawString("          "+numero.getNum()+"     "+numero.getMonto()+"     "+numero.getMontoReventado(),12,y);y+=yShift;
                            }      
                            i+=1;
                            montoTotal += numero.getMonto();
                            montoTotalReventado += numero.getMontoReventado();
                        }
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString(" Monto Total:           "+montoTotal+"   ",10,y);y+=yShift;
                        g2d.drawString(" Reventado Total:       "+montoTotalReventado+"   ",10,y);y+=yShift;
                        g2d.drawString(" Venta Total:           "+(montoTotal+montoTotalReventado)+"   ",10,y);y+=yShift;
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    }
                    else{
                        System.out.println(listaSorteos.size());
                        if(listaSorteos.size()%2 != 0){
                            NumeroSaldo numeroAUX = new NumeroSaldo(0, 0, 0);
                            listaSorteos.add(numeroAUX);
                        }
                        while(i < listaSorteos.size()) {
                            NumeroSaldo numero = listaSorteos.get(i);
                            String numeroImprimirUno = String.valueOf(numero.getNum());
                            int montoImprimirUno = numero.getMonto();
                            if (numeroImprimirUno.length()==1){
                                numeroImprimirUno = ("0"+numeroImprimirUno);
                            }
                            i+=1;
                            NumeroSaldo numero2 = listaSorteos.get(i);
                            String numeroImprimirDos = String.valueOf(numero2.getNum());
                            int montoImprimirDos = numero2.getMonto();
                            if (numeroImprimirDos.length()==1){
                                numeroImprimirDos = ("0"+numeroImprimirDos);
                            }
                            if (numeroImprimirDos.equals("00") && montoImprimirDos==0){
                                g2d.drawString("     "+numeroImprimirUno+"     "+montoImprimirUno,12,y);y+=yShift;
                            }
                            else{
                                g2d.drawString("     "+numeroImprimirUno+"     "+montoImprimirUno+"     "+numeroImprimirDos+"     "+montoImprimirDos,12,y);y+=yShift;
                            }
                            i+=1;
                            montoTotal += montoImprimirUno+montoImprimirDos;
                        }
                        g2d.drawString("-------------------------------------",12,y);y+=yShift;
                        g2d.drawString(" Monto Total:           "+montoTotal+"   ",10,y);y+=yShift;
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
    
    //---------------------Finalización Codigo ImpresoraReimprimir------------------------
    
    
    static int numero = 0;
    static int monto = 0;
    static int montoReventado = 0;
    static ArrayList<NumeroSaldo> listaNumeros = new ArrayList<NumeroSaldo>();
    
    
    public void textFieldEnteros(){
        txtNum.addKeyListener(new KeyAdapter()
        {
           public void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verificar si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != '\b' /*corresponde a BACK_SPACE*/))
              {
                 e.consume();  // ignorar el evento de teclado
              }
           }
        });
        txtMonto.addKeyListener(new KeyAdapter()
        {
           public void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verificar si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != '\b' /*corresponde a BACK_SPACE*/))
              {
                 e.consume();  // ignorar el evento de teclado
              }
           }
        });
        txtReventado.addKeyListener(new KeyAdapter()
        {
           public void keyTyped(KeyEvent e)
           {
              char caracter = e.getKeyChar();

              // Verificar si la tecla pulsada no es un digito
              if(((caracter < '0') ||
                 (caracter > '9')) &&
                 (caracter != '\b' /*corresponde a BACK_SPACE*/))
              {
                 e.consume();  // ignorar el evento de teclado
              }
           }
        });
    }
    
    public boolean verificarMonto(){
        if(monto<100){
            JOptionPane.showMessageDialog(null,"Ingrese una apuesta igual"
                                                            + " o mayor a 100");
            return false;
        }
        return true;
    }
    
    public boolean verificarMontoYMontoReventado(){
        if(montoReventado > monto){
            JOptionPane.showMessageDialog(null,"El reventado debe ser igual"
                                                + " o mayor al monto apostado e"
                                                + "igual o mayor que 100");
                    return false;
        }
        return true;
    }
    
    public void leer(){
            numero = Integer.parseInt(txtNum.getText());
            monto = Integer.parseInt(txtMonto.getText());
            montoReventado = Integer.parseInt(txtReventado.getText());
    }
    
    public void limpiarNumero(){
        txtNum.setText("0");
    }
    
    public void finalizacionVenta(){
        montoTotalPantalla = 0;
        ventaTotal.setText("0");
        txtMonto.setText("0");
        txtReventado.setText("0");
        numeroPapel.clear();
        montoPapel.clear();
        reventadoPapel.clear();
        montoTotalPapel = 0;
        listaNumeros.clear();
        panelNumeros.removeAll();
        panelNumeros.updateUI();
    }
    
    public void agregarNumero(){
        leer();
        if(numero > 99)
            JOptionPane.showMessageDialog(null,"Digite un numero de 0 a 99");
        else{
            if(verificarMonto()&&verificarMontoYMontoReventado()){
                ConexionBase conexion = new ConexionBase();
                String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
                String nombreSorteo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());;
                int montoVendido = conexion.getMontoVendidoTiempos(nombreSorteo, fecha, numero);
                int montoLimite = conexion.getLimiteMonto("Tiempos");
                
                if(montoVendido+monto > montoLimite){
                    int montoRestante = montoLimite-montoVendido;
                    JOptionPane.showMessageDialog(null,"El monto disponible para la venta de este número es: "+montoRestante);
                }
                else{
                    String textoBoton = null;
                    if(nombreSorteo.contains("Reventado"))
                        textoBoton = (numero+"  "+monto+"  "+montoReventado);
                    else
                        textoBoton= (numero+"  "+monto);
                    JButton boton = new JButton(textoBoton);
                    NumeroSaldo nuevoNumero = new NumeroSaldo(numero, monto, montoReventado);
                    montoTotalPantalla += (monto + montoReventado);
                    listaNumeros.add(nuevoNumero);
                    Color color = new Color(0, 255, 177);
                    boton.setBackground(color);
                    /* Este el código para que los botones generados por el sistema o el
                     * numero puedan responder a la accion "click" por parte del
                     * del numero.
                     */
                    boton.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            montoTotalPantalla = (montoTotalPantalla - nuevoNumero.getMonto());
                            montoTotalPantalla = (montoTotalPantalla - nuevoNumero.getMontoReventado());
                            ventaTotal.setText(Integer.toString(montoTotalPantalla));
                            listaNumeros.remove(nuevoNumero);
                            panelNumeros.remove(findComponentAt(getMousePosition()));
                            panelNumeros.updateUI();
                        }});
                    panelNumeros.add(boton);
                    panelNumeros.updateUI();
                    limpiarNumero();
                    ventaTotal.setText(Integer.toString(montoTotalPantalla));
                }
            }
        }
    }
    
        public void agregarNumeroTraerPapel(PapelTiempos papel){
            int i = 0;
            String textoCombo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());
            while(i <  papel.getNumeros().size()){
                String textoBoton = null;
                int numero = Integer.parseInt(papel.getNumeros().get(i));
                int monto = papel.getMontos().get(i);
                int montoReventado = papel.getMontosReventados().get(i);
                JButton boton = new JButton(textoBoton);
                if(textoCombo.contains("Reventado")){
                    textoBoton = (numero+"  "+monto+"  "+montoReventado);
                    boton = new JButton(textoBoton);
                    NumeroSaldo nuevoNumero = new NumeroSaldo(numero, monto, montoReventado);
                    montoTotalPantalla += (monto + montoReventado);
                    listaNumeros.add(nuevoNumero);
                    Color color = new Color(0, 255, 177);
                    boton.setBackground(color);
                    /* Este el código para que los botones generados por el sistema o el
                     * numero puedan responder a la accion "click" por parte del
                     * del numero.
                     */
                    boton.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            montoTotalPantalla = (montoTotalPantalla - nuevoNumero.getMonto());
                            montoTotalPantalla = (montoTotalPantalla - nuevoNumero.getMontoReventado());
                            ventaTotal.setText(Integer.toString(montoTotalPantalla));
                            listaNumeros.remove(nuevoNumero);
                            panelNumeros.remove(findComponentAt(getMousePosition()));
                            panelNumeros.updateUI();
                        }});
                    panelNumeros.add(boton);
                    panelNumeros.updateUI();
                    limpiarNumero();
                    ventaTotal.setText(Integer.toString(montoTotalPantalla));
                    
                }
                else{
                    textoBoton= (numero+"  "+monto);
                    boton = new JButton(textoBoton);
                    NumeroSaldo nuevoNumero = new NumeroSaldo(numero, monto, 0);
                    montoTotalPantalla += (monto);
                    listaNumeros.add(nuevoNumero);
                    Color color = new Color(0, 255, 177);
                    boton.setBackground(color);
                    /* Este el código para que los botones generados por el sistema o el
                     * numero puedan responder a la accion "click" por parte del
                     * del numero.
                     */
                    boton.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            montoTotalPantalla = (montoTotalPantalla - nuevoNumero.getMonto());
                            ventaTotal.setText(Integer.toString(montoTotalPantalla));
                            listaNumeros.remove(nuevoNumero);
                            panelNumeros.remove(findComponentAt(getMousePosition()));
                            panelNumeros.updateUI();
                        }});
                    panelNumeros.add(boton);
                    panelNumeros.updateUI();
                    limpiarNumero();
                    ventaTotal.setText(Integer.toString(montoTotalPantalla));
                }
                i+=1;
            }
    }
    
    public void venta(){
        if (listaNumeros.size() > 0){
            LocalDateTime reloj = LocalDateTime.now();
            int hora = reloj.getHour();
            int minuto = reloj.getMinute();
            String textoCombo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());
            ConexionBase conexion  = new ConexionBase();
            ArrayList<NombreSorteo> listaSorteos = conexion.getFechaCierreSorteos();
            
            for (int i = 0; i < listaSorteos.size(); i++){
                if(listaSorteos.get(i).getNombre().equals(textoCombo)){
                    String horarioCierre = listaSorteos.get(i).getHora_cierre();
                    String[] partes = horarioCierre.split(":");
                    int horaCierre = Integer.parseInt(partes[0]);
                    int minCierre = Integer.parseInt(partes[1]);
                    if(((hora <= horaCierre)&&(minuto < minCierre)) || ((hora < horaCierre)&&(minuto >= minCierre))){
                        crearTicket();
                        bHeight = Double.valueOf(numeroPapel.size());
                        PrinterJob pj = PrinterJob.getPrinterJob();        
                        pj.setPrintable(new FacturaTiempos(),getPageFormat(pj));
                        try{
                            pj.print();
                            finalizacionVenta();
                        }
                        catch (PrinterException ex) {
                                 ex.printStackTrace();
                        }
                        
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Sorteo Expirado");
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNum = new javax.swing.JTextField();
        txtReventado = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        fechaLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelReventado = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelNumeros = new javax.swing.JPanel();
        txtMonto = new javax.swing.JTextField();
        botonVender = new javax.swing.JButton();
        SorteoCombo = new javax.swing.JComboBox<>();
        sorteoLabel = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        BotonReimpresion = new javax.swing.JButton();
        BotonBorrarPapel = new javax.swing.JButton();
        botonTraerPapel = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ventaTotal = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNum.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 22)); // NOI18N
        txtNum.setText("0");
        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });

        txtReventado.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 22)); // NOI18N
        txtReventado.setForeground(new java.awt.Color(255, 0, 51));
        txtReventado.setText("0");
        txtReventado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReventadoActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/left_64px.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        fechaLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        fechaLabel.setText("Fecha");

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel3.setText("Numero");

        labelReventado.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        labelReventado.setForeground(new java.awt.Color(255, 0, 51));
        labelReventado.setText("Reventado");

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel5.setText("Monto");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/add_new_104px.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        panelNumeros.setLayout(new java.awt.GridLayout(0, 5));
        jScrollPane2.setViewportView(panelNumeros);

        txtMonto.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 22)); // NOI18N
        txtMonto.setText("0");
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });

        botonVender.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        botonVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/money_52px.png"))); // NOI18N
        botonVender.setText("Vender");
        botonVender.setBorderPainted(false);
        botonVender.setContentAreaFilled(false);
        botonVender.setDefaultCapable(false);
        botonVender.setFocusPainted(false);
        botonVender.setFocusable(false);
        botonVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVenderActionPerformed(evt);
            }
        });

        SorteoCombo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        SorteoCombo.setFocusable(false);
        SorteoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SorteoComboActionPerformed(evt);
            }
        });

        sorteoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        sorteoLabel.setForeground(new java.awt.Color(0, 255, 127));
        sorteoLabel.setText("Nombre sorteo");

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/area_chart_26px.png"))); // NOI18N
        jButton11.setText("Reporte");
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setFocusPainted(false);
        jButton11.setFocusable(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        BotonReimpresion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BotonReimpresion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/print_26px.png"))); // NOI18N
        BotonReimpresion.setText("Reimprimir");
        BotonReimpresion.setBorderPainted(false);
        BotonReimpresion.setContentAreaFilled(false);
        BotonReimpresion.setFocusPainted(false);
        BotonReimpresion.setFocusable(false);
        BotonReimpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReimpresionActionPerformed(evt);
            }
        });

        BotonBorrarPapel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BotonBorrarPapel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/trash_can_26px.png"))); // NOI18N
        BotonBorrarPapel.setText("Borrar Papel");
        BotonBorrarPapel.setBorderPainted(false);
        BotonBorrarPapel.setContentAreaFilled(false);
        BotonBorrarPapel.setFocusPainted(false);
        BotonBorrarPapel.setFocusable(false);
        BotonBorrarPapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBorrarPapelActionPerformed(evt);
            }
        });

        botonTraerPapel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        botonTraerPapel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/import_30px.png"))); // NOI18N
        botonTraerPapel.setText("Traer papel");
        botonTraerPapel.setBorderPainted(false);
        botonTraerPapel.setContentAreaFilled(false);
        botonTraerPapel.setFocusPainted(false);
        botonTraerPapel.setFocusable(false);
        botonTraerPapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTraerPapelActionPerformed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/import_30px.png"))); // NOI18N
        jButton16.setText("Parley");
        jButton16.setBorderPainted(false);
        jButton16.setContentAreaFilled(false);
        jButton16.setFocusPainted(false);
        jButton16.setFocusable(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Venta Total:");

        ventaTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ventaTotal.setText("0");

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/area_chart_26px.png"))); // NOI18N
        jButton12.setText("Reporte GENERAL");
        jButton12.setBorderPainted(false);
        jButton12.setContentAreaFilled(false);
        jButton12.setFocusPainted(false);
        jButton12.setFocusable(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonVender)
                        .addGap(109, 109, 109)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtReventado, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelReventado))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonReimpresion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonTraerPapel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotonBorrarPapel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel1)
                                .addGap(61, 61, 61)
                                .addComponent(ventaTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton12))
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(sorteoLabel))
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(554, 554, 554)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaLabel)
                                    .addComponent(SorteoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(95, 95, 95))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(SorteoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sorteoLabel)
                    .addComponent(fechaLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ventaTotal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton12)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(botonVender))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelReventado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtReventado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtNum)
                                            .addComponent(txtMonto))))
                                .addGap(15, 15, 15)))
                        .addGap(47, 47, 47))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonTraerPapel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonReimpresion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11)
                            .addComponent(BotonBorrarPapel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        agregarNumero();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtReventadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReventadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReventadoActionPerformed

    private void botonVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVenderActionPerformed
        venta();
    }//GEN-LAST:event_botonVenderActionPerformed

    private void SorteoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SorteoComboActionPerformed
        Object ob = SorteoCombo.getSelectedItem();
        sorteoLabel.setText(ob.toString());
        String textoCombo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());
        if(textoCombo.contains("Reventado")){
            labelReventado.setVisible(true);
            txtReventado.setVisible(true);
        }
        else{
            labelReventado.setVisible(false);
            txtReventado.setVisible(false);
            txtReventado.setText("0");
        }
    }//GEN-LAST:event_SorteoComboActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //Función por la cual se imprime el reporte por sorteo
        
        String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
        String nombreSorteo = SorteoCombo.getItemAt(SorteoCombo.getSelectedIndex());;
        ConexionBase conexion  = new ConexionBase();
        ArrayList<NumeroSaldo> listaSorteos = conexion.getReporteSorteo(nombreSorteo, fecha);
        bHeight = Double.valueOf(listaSorteos.size());
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new ReporteTiempos(),getPageFormat(pj));
        try{
            pj.print();
            finalizacionVenta();
        }
        catch (PrinterException ex) {
                 ex.printStackTrace();
        }
            
    }//GEN-LAST:event_jButton11ActionPerformed

    private void BotonReimpresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReimpresionActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Reimpresión de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Último papel vendido", "Reimprimir por codigo del papel"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<Integer> ultimoTicket = conexion.getUltimoIdTicket();
            ArrayList<PapelTiempos> papelAux = conexion.reimprimir(ultimoTicket.get(0));
            PapelTiempos papel = papelAux.get(0);
            bHeight = Double.valueOf(papel.getNumeros().size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraReimprimir(papel),getPageFormat(pj));
            try{
                pj.print();
                finalizacionVenta();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
            }  
        }
        
        if (seleccion == 1){       //SE DI EL ULTIMO PAPEL
            String input;
            do {
                input = JOptionPane.showInputDialog("Ingrese un numero, por favor");
                if (input.matches("^[0-9]*$")) {
                    ConexionBase conexion = new ConexionBase();
                    ArrayList<PapelTiempos> papelAux = conexion.reimprimir(Integer.parseInt(input));
                    PapelTiempos papel = papelAux.get(0);
                    bHeight = Double.valueOf(papel.getNumeros().size());
                    PrinterJob pj = PrinterJob.getPrinterJob();        
                    pj.setPrintable(new ImpresoraReimprimir(papel),getPageFormat(pj));
                    try{
                        pj.print();
                        finalizacionVenta();
                    }
                    catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                } 
                else {
                    JOptionPane.showMessageDialog(null,"Intente de nuevo, por favor");
                }
            } while (!input.matches("^[0-9]*$"));
        }
    }//GEN-LAST:event_BotonReimpresionActionPerformed

    private void botonTraerPapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTraerPapelActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Traer números de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Último papel vendido", "Traer números por codigo del papel"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<Integer> ultimoTicket = conexion.getUltimoIdTicket();
            ArrayList<PapelTiempos> papelAux = conexion.reimprimir(ultimoTicket.get(0));
            PapelTiempos papel = papelAux.get(0);
            
            agregarNumeroTraerPapel(papel);
        }
        
        if (seleccion == 1){       //SE DI EL ULTIMO PAPEL
            String input;
            do {
                input = JOptionPane.showInputDialog("Ingrese un numero, por favor");
                if (input.matches("^[0-9]*$")) {
                    ConexionBase conexion = new ConexionBase();
                    ArrayList<PapelTiempos> papelAux = conexion.reimprimir(Integer.parseInt(input));
                    PapelTiempos papel = papelAux.get(0);
            
                    agregarNumeroTraerPapel(papel);
                } 
                else {
                    JOptionPane.showMessageDialog(null,"Intente de nuevo, por favor");
                }
            } while (!input.matches("^[0-9]*$"));
        }
    }//GEN-LAST:event_botonTraerPapelActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        VentaParley ventanaVentaParley = new VentaParley();
        ventanaVentaParley.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void BotonBorrarPapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBorrarPapelActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Eliminación de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Borrar último papel vendido", "Borrar por codigo del papel"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<Integer> ultimoTicket = conexion.getUltimoIdTicket();
            ArrayList<PapelTiempos> papelAux = conexion.reimprimir(ultimoTicket.get(0));
            PapelTiempos papel = papelAux.get(0);
            bHeight = Double.valueOf(papel.getNumeros().size());
            conexion.borrarTicket(ultimoTicket.get(0));
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraBorrarPapel(papel),getPageFormat(pj));
            try{
                pj.print();
                finalizacionVenta();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
            }  
        }
        
        if (seleccion == 1){       //SE DI EL ULTIMO PAPEL
            String input;
            do {
                input = JOptionPane.showInputDialog("Ingrese un numero, por favor");
                if (input.matches("^[0-9]*$")) {
                    ConexionBase conexion = new ConexionBase();
                    ArrayList<PapelTiempos> papelAux = conexion.reimprimir(Integer.parseInt(input));
                    PapelTiempos papel = papelAux.get(0);
                    bHeight = Double.valueOf(papel.getNumeros().size());
                    conexion.borrarTicket(Integer.parseInt(input));
                    PrinterJob pj = PrinterJob.getPrinterJob();        
                    pj.setPrintable(new ImpresoraBorrarPapel(papel),getPageFormat(pj));
                    try{
                        pj.print();
                        finalizacionVenta();
                    }
                    catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                } 
                else {
                    JOptionPane.showMessageDialog(null,"Intente de nuevo, por favor");
                }
            } while (!input.matches("^[0-9]*$"));
        }
    }//GEN-LAST:event_BotonBorrarPapelActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Reporte General",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Reporte General Diario", "Reporte Mañana", "Reporte Noche"},"opcion 1");
        String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<reporteJuegosTotal> listaReportes = conexion.getReporteTotal(fecha);

            bHeight = Double.valueOf(listaReportes.size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraReporteGeneral(listaReportes, "REPORTE GENERAL DIARIO"),getPageFormat(pj));
            try{
                pj.print();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
        
        if (seleccion == 1){       //SE DI EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<reporteJuegosTotal> listaReportes = conexion.getReporteDia(fecha);
            bHeight = Double.valueOf(listaReportes.size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraReporteGeneral(listaReportes, "REPORTE MAÑANA"),getPageFormat(pj));
            try{
                pj.print();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
        
        if (seleccion == 2){       //SE DI EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<reporteJuegosTotal> listaReportes = conexion.getReporteNoche(fecha);
            bHeight = Double.valueOf(listaReportes.size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraReporteGeneral(listaReportes,"REPORTE NOCHE"),getPageFormat(pj));
            try{
                pj.print();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
        
        
        
        
        
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentaTiempos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonBorrarPapel;
    private javax.swing.JButton BotonReimpresion;
    private javax.swing.JComboBox<String> SorteoCombo;
    private javax.swing.JButton botonTraerPapel;
    private javax.swing.JButton botonVender;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelReventado;
    private javax.swing.JPanel panelNumeros;
    private javax.swing.JLabel sorteoLabel;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtReventado;
    private javax.swing.JLabel ventaTotal;
    // End of variables declaration//GEN-END:variables
}
