/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import CapaNegocios.ConexionBase;
import CapaNegocios.ImpresoraPega3;
import CapaNegocios.ImpresoraPega3Borrar;
import CapaNegocios.ImpresoraPega3Reimprimir;
import CapaNegocios.ImpresoraPega3Reporte;
import CapaNegocios.NombreSorteo;
import CapaNegocios.NumeroPega3Saldo;
import CapaNegocios.PapelPega3;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
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
public class VentaPega3 extends javax.swing.JFrame {

    public VentaPega3() {
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
            fechaHoy = (anno+"-"+mes+"-"+dia);
            fechaLabel.setText(fechaHoy);
        }
        txtNum.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();   
                    agregarNumeros();
                }
            }
        });
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumeros();
                   }
                }
           }
        );
    }
    
    public static String fechaHoy = "";
    LocalDateTime ahora = LocalDateTime.now();
    int anno = ahora.getYear();
    int mes = ahora.getMonthValue();
    int dia = ahora.getDayOfMonth();
    int montoTotalPantalla = 0;
    Double balance=0.0;
    Double bHeight=0.0;
    static String numero = null;
    static int monto = 0;
    static ArrayList<NumeroPega3Saldo> listaNumerosPega3 = new ArrayList<NumeroPega3Saldo>();
    
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
   
    
    public void llenarComboBox(){
        ArrayList<String> listaSorteos = new ArrayList<>();
        ConexionBase conexion = new ConexionBase();
        listaSorteos = conexion.getNombrePega3();
        for (int i = 0; i < listaSorteos.size(); i++){
                sorteoCombo.addItem(listaSorteos.get(i));;
        }
    }
    

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
    }
    
    public boolean verificarMonto(){
        if(monto<100){
            JOptionPane.showMessageDialog(null,"Ingrese una apuesta igual"
                                                            + " o mayor a 100");
            return false;
        }
        return true;
    }
    
    public boolean verificarNumero(){
        if(Integer.parseInt(numero) > 999){
            JOptionPane.showMessageDialog(null,"El numero debe estar entre el 0 y el 999");
            return false;
        }
        return true;
    }
    
    public void crearPapelPega3(String nombreSorteo, String fecha, int idNombrePega3){
        PapelPega3 papel = new PapelPega3(fecha, nombreSorteo);
        int montoTotal = 0;
        for (int i = 0; i < listaNumerosPega3.size(); i++){
            papel.anadirNumeros(listaNumerosPega3.get(i).getNum());
            papel.anadirModalidades(listaNumerosPega3.get(i).getModo());
            papel.anadirMonto(listaNumerosPega3.get(i).getMonto());
            montoTotal += listaNumerosPega3.get(i).getMonto();
        }
        ConexionBase conexion  = new ConexionBase();
        conexion.crearTicketPega3(fecha, montoTotal, idNombrePega3);
        conexion.crearDetallePega3(papel);
        bHeight = Double.valueOf(listaNumerosPega3.size());
        PrinterJob pj = PrinterJob.getPrinterJob(); 
        papel.setNumeroTicket(conexion.getUltimoPega3());
        pj.setPrintable(new ImpresoraPega3(papel),getPageFormat(pj));
        try{
            pj.print();
            finalizacionVenta();
        }
        catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    }
    
    
    public void leer(){
            numero = txtNum.getText();
            monto = Integer.parseInt(txtMonto.getText());
    }
    
    public void agregarNumeros(){
        leer();
        if(verificarNumero()&&verificarMonto()){
            ConexionBase conexion = new ConexionBase();
            String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
            String nombreSorteo = sorteoCombo.getItemAt(sorteoCombo.getSelectedIndex());;
                String modo = modoCombo.getItemAt(modoCombo.getSelectedIndex());
            int montoVendido = conexion.getMontoVendidoPega3(nombreSorteo, fecha, Integer.parseInt(numero),modo);
            int montoLimite = conexion.getLimiteMonto("Pega3");
            
            System.out.println("montoVendido: "+montoVendido);
            System.out.println("montoLimite: "+montoLimite);
            System.out.println("monto: "+monto);

            if(montoVendido+monto > montoLimite){
                int montoRestante = montoLimite-montoVendido;
                JOptionPane.showMessageDialog(null,"El monto disponible para la venta de este número con la modalidad"+modo+" es: "+montoRestante);
            }
            
            else{
                String textoBoton = null;
                textoBoton = (numero+"  "+monto+"  "+modo);
                JButton boton = new JButton(textoBoton);

                NumeroPega3Saldo numeroAUX = new NumeroPega3Saldo(numero, monto, modo);
                montoTotalPantalla += (monto);
                listaNumerosPega3.add(numeroAUX);

                Color color = new Color(135, 206, 250);
                boton.setBackground(color);
                boton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae){
                        listaNumerosPega3.remove(numeroAUX);
                        montoTotalPantalla = (montoTotalPantalla - numeroAUX.getMonto());
                        ventaTotal.setText(Integer.toString(montoTotalPantalla));
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
    
    public void agregarNumerosTraerPapel(PapelPega3 papel){
        String textoBoton = null;
        int i = 0;
        while (i < papel.getNumeros().size()){
            
            String numero = papel.getNumeros().get(i);
            int monto = papel.getMontos().get(i);
            String modo = papel.getModalidades().get(i);
            textoBoton = (numero+"  "+monto+"  "+modo);
            JButton boton = new JButton(textoBoton);

            NumeroPega3Saldo numeroAUX = new NumeroPega3Saldo(numero, monto, modo);
            montoTotalPantalla += (monto);
            listaNumerosPega3.add(numeroAUX);

            Color color = new Color(135, 206, 250);
            boton.setBackground(color);
            boton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    listaNumerosPega3.remove(numeroAUX);
                    montoTotalPantalla = (montoTotalPantalla - numeroAUX.getMonto());
                    ventaTotal.setText(Integer.toString(montoTotalPantalla));
                    panelNumeros.remove(findComponentAt(getMousePosition()));
                    panelNumeros.updateUI();
                }});
            panelNumeros.add(boton);
            panelNumeros.updateUI();
            limpiarNumero();
            ventaTotal.setText(Integer.toString(montoTotalPantalla));
            i+=1;
        }
    }
    
    public void limpiarNumero(){
        txtNum.setText("");
    }
    
    public void finalizacionVenta(){
        listaNumerosPega3.clear();
        montoTotalPantalla = 0;
        ventaTotal.setText("0");
        txtMonto.setText("");
        txtNum.setText("");
        panelNumeros.removeAll();
        panelNumeros.updateUI();
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
        jButton2 = new javax.swing.JButton();
        fechaLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelReventado = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelNumeros = new javax.swing.JPanel();
        txtMonto = new javax.swing.JTextField();
        botonVender = new javax.swing.JButton();
        sorteoLabel = new javax.swing.JLabel();
        BotonReporte = new javax.swing.JButton();
        botonReimprimir = new javax.swing.JButton();
        botonBorrar = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        modoCombo = new javax.swing.JComboBox<>();
        sorteoCombo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        ventaTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        txtNum.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
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
        labelReventado.setText(" Monto");

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

        txtMonto.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
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

        sorteoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        sorteoLabel.setForeground(new java.awt.Color(135, 206, 250));
        sorteoLabel.setText("Pega 3");

        BotonReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/area_chart_26px.png"))); // NOI18N
        BotonReporte.setText("Reporte");
        BotonReporte.setBorderPainted(false);
        BotonReporte.setContentAreaFilled(false);
        BotonReporte.setFocusPainted(false);
        BotonReporte.setFocusable(false);
        BotonReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReporteActionPerformed(evt);
            }
        });

        botonReimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/print_26px.png"))); // NOI18N
        botonReimprimir.setText("Reimprimir");
        botonReimprimir.setBorderPainted(false);
        botonReimprimir.setContentAreaFilled(false);
        botonReimprimir.setFocusPainted(false);
        botonReimprimir.setFocusable(false);
        botonReimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReimprimirActionPerformed(evt);
            }
        });

        botonBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/trash_can_26px.png"))); // NOI18N
        botonBorrar.setText("Borrar Papel");
        botonBorrar.setBorderPainted(false);
        botonBorrar.setContentAreaFilled(false);
        botonBorrar.setFocusPainted(false);
        botonBorrar.setFocusable(false);
        botonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarActionPerformed(evt);
            }
        });

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/icons/import_30px.png"))); // NOI18N
        jButton15.setText("Traer papel");
        jButton15.setBorderPainted(false);
        jButton15.setContentAreaFilled(false);
        jButton15.setFocusPainted(false);
        jButton15.setFocusable(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        modoCombo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        modoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Orden", "Desorden" }));
        modoCombo.setFocusable(false);
        modoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoComboActionPerformed(evt);
            }
        });

        sorteoCombo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        sorteoCombo.setFocusable(false);
        sorteoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sorteoComboActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Venta Total:");

        ventaTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ventaTotal.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaLabel)
                    .addComponent(sorteoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(sorteoLabel)
                        .addGap(290, 1153, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(botonVender)
                .addGap(58, 58, 58)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNum))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelReventado)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(modoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonReimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(botonBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addGap(61, 61, 61)
                .addComponent(ventaTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(sorteoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sorteoLabel)
                    .addComponent(fechaLabel))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ventaTotal))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonVender)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelReventado)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMonto)
                                .addComponent(modoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(botonReimprimir)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(botonBorrar)
                                .addComponent(BotonReporte)))))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        agregarNumeros();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void botonVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVenderActionPerformed
        
        if (listaNumerosPega3.size() > 0){
            String textoCombo = sorteoCombo.getItemAt(sorteoCombo.getSelectedIndex());
            ConexionBase conexion  = new ConexionBase();
            ArrayList<NombreSorteo> listaSorteos = conexion.getNombreCierrePega3();
            
            for (int i = 0; i < listaSorteos.size(); i++){
                if(listaSorteos.get(i).getNombre().equals(textoCombo)){
                    String horarioCierre = listaSorteos.get(i).getHora_cierre();
                    String[] partes = horarioCierre.split(":");
                    LocalDateTime reloj = LocalDateTime.now();
                    int hora = reloj.getHour();
                    int minuto = reloj.getMinute();
                    int horaCierre = Integer.parseInt(partes[0]);
                    int minCierre = Integer.parseInt(partes[1]);
                                            
                    
                    if(((hora <= horaCierre)&&(minuto < minCierre)) || ((hora < horaCierre)&&(minuto >= minCierre))){
                        
                        String fecha = fechaLabel.getText();
                        String sorteo = sorteoCombo.getItemAt(sorteoCombo.getSelectedIndex());
                        int id = listaSorteos.get(i).getId_nombre_sorteo();
                        crearPapelPega3(sorteo,fecha,id);
                        finalizacionVenta();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Sorteo Expirado");
                    }
                }
            }  
        }    
    }//GEN-LAST:event_botonVenderActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        VentaTiempos ventana = new VentaTiempos();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BotonReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteActionPerformed
         //Función por la cual se imprime el reporte por sorteo
        String nombreSorte = sorteoCombo.getItemAt(sorteoCombo.getSelectedIndex());
        ConexionBase conexion  = new ConexionBase();
        
        PapelPega3 papel = conexion.getReportePega3( nombreSorte, fechaHoy);
        bHeight = Double.valueOf(papel.getNumeros().size());
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new ImpresoraPega3Reporte(papel),getPageFormat(pj));
        try{
            pj.print();
            finalizacionVenta();
        }
        catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    }//GEN-LAST:event_BotonReporteActionPerformed

    private void botonReimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReimprimirActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Reimpresión de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Último papel de Pega3 vendido", "Reimprimir por codigo del papel de Pega3"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            int idTicket = conexion.getUltimoPega3();
            PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
            bHeight = Double.valueOf(papelPega.getNumeros().size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraPega3Reimprimir(papelPega),getPageFormat(pj));
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
                    int idTicket = Integer.parseInt(input);
                    PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
                    bHeight = Double.valueOf(papelPega.getNumeros().size());
                    PrinterJob pj = PrinterJob.getPrinterJob();        
                    pj.setPrintable(new ImpresoraPega3Reimprimir(papelPega),getPageFormat(pj));
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
    }//GEN-LAST:event_botonReimprimirActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Traer números de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Último papel de Pega3 vendido", "Traer números por codigo del papel de Pega3"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            int idTicket = conexion.getUltimoPega3();
            PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
            agregarNumerosTraerPapel(papelPega);
            
        }
        if (seleccion == 1){       //SE DI EL ULTIMO PAPEL
            String input;
            do {
                input = JOptionPane.showInputDialog("Ingrese un numero, por favor");
                if (input.matches("^[0-9]*$")) {
                    ConexionBase conexion = new ConexionBase();
                    int idTicket = Integer.parseInt(input);
                    PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
                    agregarNumerosTraerPapel(papelPega);
                } 
                else {
                    JOptionPane.showMessageDialog(null,"Intente de nuevo, por favor");
                }
            } while (!input.matches("^[0-9]*$"));
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void botonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Borrar papeles de Pega3",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Borrar último papel de Pega3 vendido", "Borrar por codigo de papel de Pega3"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            int idTicket = conexion.getUltimoPega3();
            PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
            bHeight = Double.valueOf(papelPega.getNumeros().size());
            
            conexion.borrarPega3(idTicket);
            
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraPega3Borrar(papelPega),getPageFormat(pj));
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
                    int idTicket = Integer.parseInt(input);
                    PapelPega3 papelPega = conexion.getPapelPega3(idTicket);
                    bHeight = Double.valueOf(papelPega.getNumeros().size());
                    
                    conexion.borrarPega3(idTicket);
                    
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new ImpresoraPega3Borrar(papelPega),getPageFormat(pj));
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
        
    }//GEN-LAST:event_botonBorrarActionPerformed

    private void sorteoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sorteoComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sorteoComboActionPerformed

    private void modoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modoComboActionPerformed

    }//GEN-LAST:event_modoComboActionPerformed

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
            java.util.logging.Logger.getLogger(VentaPega3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentaPega3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentaPega3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentaPega3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentaPega3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonReporte;
    private javax.swing.JButton botonBorrar;
    private javax.swing.JButton botonReimprimir;
    private javax.swing.JButton botonVender;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelReventado;
    private javax.swing.JComboBox<String> modoCombo;
    private javax.swing.JPanel panelNumeros;
    private javax.swing.JComboBox<String> sorteoCombo;
    private javax.swing.JLabel sorteoLabel;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNum;
    private javax.swing.JLabel ventaTotal;
    // End of variables declaration//GEN-END:variables
}
