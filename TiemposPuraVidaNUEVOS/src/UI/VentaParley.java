/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import CapaNegocios.ConexionBase;
import CapaNegocios.ImpresoraParley;
import CapaNegocios.ImpresoraParleyBorrar;
import CapaNegocios.ImpresoraParleyReimprimir;
import CapaNegocios.ImpresoraParleyReporte;
import CapaNegocios.NumeroParleySaldo;
import CapaNegocios.PapelParley;
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
public class VentaParley extends javax.swing.JFrame {

    public VentaParley() {
        initComponents();
        textFieldEnteros();
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
        }txtNum.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumeros();
                   }
                }
           }
        );
        txtNum2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                   Toolkit.getDefaultToolkit().beep();   
                   agregarNumeros();
                   }
                }
           }
        );
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
    Double balance=0.0;
    Double bHeight=0.0;
    static int numero = 0;
    static int numero2 = 0;
    static int monto = 0;
    static ArrayList<NumeroParleySaldo> listaNumerosParley = new ArrayList<NumeroParleySaldo>();
    
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
        txtNum2.addKeyListener(new KeyAdapter()
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
    
    public boolean verificarNumeros(){
        if(numero > 99 || numero2 >99){
            JOptionPane.showMessageDialog(null,"Los numeros tienen que ser"
                                                            + " menores a 100");
            return false;
        }
        return true;
    }
    
    public boolean verificarNumerosDiferentes(){
        if(numero == numero2){
            JOptionPane.showMessageDialog(null,"Los numeros deben ser diferentes"
                                                            + " entre si");
            return false;
        }
        return true;
    }
    
    public PapelParley crearTicketParley(){
        PapelParley papelParley = new PapelParley();
        int montoTotalPapel = 0;
        papelParley.setFechaTicket(fechaHoy);
        for (int i = 0; i < listaNumerosParley.size(); i++){
            NumeroParleySaldo listaNumerosVenta = listaNumerosParley.get(i);
            String numero = String.valueOf(listaNumerosVenta.getNum());
            String numero2 = String.valueOf(listaNumerosVenta.getNum2());
            int monto = listaNumerosVenta.getMonto();
            papelParley.anadirNumeros(numero);
            papelParley.anadirNumeros2(numero2);
            papelParley.anadirMonto(monto);
            montoTotalPapel += monto;
        }
        
        ConexionBase conexion  = new ConexionBase();
        conexion.crearTicketParley(fechaHoy, montoTotalPapel);
        conexion.crearDetalleParley(papelParley);
        
        return papelParley;
    }
    
    public void leer(){
            numero = Integer.parseInt(txtNum.getText());
            numero2 = Integer.parseInt(txtNum2.getText());
            monto = Integer.parseInt(txtMonto.getText());
    }
    
    public void agregarNumeros(){
        leer();
        if(verificarNumeros()&&verificarNumerosDiferentes()&&verificarMonto()){
            String textoBoton = null;
            textoBoton = (numero+"  "+numero2+"  "+monto);
            JButton boton = new JButton(textoBoton);

            NumeroParleySaldo numeroAUX = new NumeroParleySaldo(numero, numero2, monto);
            listaNumerosParley.add(numeroAUX);

            Color color = new Color(64, 224, 208);
            boton.setBackground(color);
            boton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    listaNumerosParley.remove(numeroAUX);
                    panelNumeros.remove(findComponentAt(getMousePosition()));
                    panelNumeros.updateUI();
                }});
            panelNumeros.add(boton);
            panelNumeros.updateUI();
            limpiarNumero();
        }
    }
    
    public void limpiarNumero(){
        txtNum.setText("0");
        txtNum2.setText("0");
    }
    
    public void finalizacionVenta(){
        listaNumerosParley.clear();
        txtMonto.setText("0");
        txtNum.setText("0");
        txtNum2.setText("0");
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
        txtNum2 = new javax.swing.JTextField();
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
        sorteoLabel = new javax.swing.JLabel();
        BotonReporte = new javax.swing.JButton();
        botonReimprimir = new javax.swing.JButton();
        botonBorrar = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        txtNum.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 22)); // NOI18N
        txtNum.setText("0");
        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });

        txtNum2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 22)); // NOI18N
        txtNum2.setForeground(new java.awt.Color(51, 51, 51));
        txtNum2.setText("0");
        txtNum2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNum2ActionPerformed(evt);
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
        labelReventado.setForeground(new java.awt.Color(51, 51, 51));
        labelReventado.setText(" Monto");

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel5.setText("Numero2");

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

        sorteoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        sorteoLabel.setForeground(new java.awt.Color(64, 224, 208));
        sorteoLabel.setText("Parley");

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

        jButton15.setForeground(java.awt.Color.red);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(sorteoLabel)
                        .addGap(513, 513, 513)
                        .addComponent(fechaLabel))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1097, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(botonVender)
                .addGap(109, 109, 109)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtNum2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelReventado)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonReimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(botonBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sorteoLabel)
                    .addComponent(fechaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonVender)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelReventado)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtMonto, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNum, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addComponent(txtNum2))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonReimprimir)
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonBorrar)
                                    .addComponent(BotonReporte))))))
                .addContainerGap(35, Short.MAX_VALUE))
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

    private void txtNum2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNum2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNum2ActionPerformed

    private void botonVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVenderActionPerformed
        
        if (listaNumerosParley.size() > 0){
            LocalDateTime reloj = LocalDateTime.now();
            int hora = reloj.getHour();
            int minuto = reloj.getMinute();
            if( ((hora <= 19) && (minuto < 25))||((hora < 19)&&(minuto >= 25))){
                    bHeight = Double.valueOf(listaNumerosParley.size());
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    
                    
                    PapelParley papelParley = crearTicketParley();
                    ConexionBase conexion = new ConexionBase();
                    int numeroParley = conexion.getUltimoIdParley().get(0);
                    
                    papelParley.setNumeroTicket(numeroParley);
                    pj.setPrintable(new ImpresoraParley(papelParley),getPageFormat(pj));
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
    }//GEN-LAST:event_botonVenderActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        VentaTiempos ventana = new VentaTiempos();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BotonReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteActionPerformed
         //Función por la cual se imprime el reporte por sorteo
        
        String fecha = (ahora.getDayOfMonth()+"-"+ahora.getMonthValue()+"-"+ahora.getYear());
        ConexionBase conexion  = new ConexionBase();
        ArrayList<PapelParley> listaNumeros = conexion.getReporteParley(fecha);
        PapelParley reporte = listaNumeros.get(0);
        bHeight = Double.valueOf(reporte.getNumeros().size());
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new ImpresoraParleyReporte(reporte),getPageFormat(pj));
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
        new Object[] { "Último papel de Parley vendido", "Reimprimir por codigo del papel de Parley"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<Integer> ultimoTicket = conexion.getUltimoIdParley();
            ArrayList<PapelParley> papelAux = conexion.reimprimirParley(ultimoTicket.get(0));
            PapelParley papel = papelAux.get(0);
            bHeight = Double.valueOf(papel.getNumeros().size());
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraParleyReimprimir(papel),getPageFormat(pj));
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
                    ArrayList<PapelParley> papelAux = conexion.reimprimirParley(Integer.parseInt(input));
                    PapelParley papel = papelAux.get(0);
                    bHeight = Double.valueOf(papel.getNumeros().size());
                    PrinterJob pj = PrinterJob.getPrinterJob();        
                    pj.setPrintable(new ImpresoraParleyReimprimir(papel),getPageFormat(pj));
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
        JOptionPane.showMessageDialog(null,"Funcion deshabilitada temporalmente");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void botonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarActionPerformed
        int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una opcion",
          "Eliminación de papeles",JOptionPane.YES_NO_CANCEL_OPTION,
           JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
        new Object[] { "Borrar último papel de Parley vendido", "Borrar por codigo del papel de Parley"},"opcion 1");

        if  (seleccion == 0){       //SE REIMPRIME EL ULTIMO PAPEL
            ConexionBase conexion = new ConexionBase();
            ArrayList<Integer> ultimoTicket = conexion.getUltimoIdParley();
            ArrayList<PapelParley> papelAux = conexion.reimprimirParley(ultimoTicket.get(0));
            PapelParley papel = papelAux.get(0);
            bHeight = Double.valueOf(papel.getNumeros().size());
            
            conexion.borrarParley(ultimoTicket.get(0));
            
            PrinterJob pj = PrinterJob.getPrinterJob();        
            pj.setPrintable(new ImpresoraParleyBorrar(papel),getPageFormat(pj));
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
                    ArrayList<PapelParley> papelAux = conexion.reimprimirParley(Integer.parseInt(input));
                    PapelParley papel = papelAux.get(0);
                    bHeight = Double.valueOf(papel.getNumeros().size());

                    
                    conexion.borrarParley(Integer.parseInt(input));
                    

                    PrinterJob pj = PrinterJob.getPrinterJob();        
                    pj.setPrintable(new ImpresoraParleyBorrar(papel),getPageFormat(pj));
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
            java.util.logging.Logger.getLogger(VentaParley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentaParley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentaParley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentaParley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentaParley().setVisible(true);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelReventado;
    private javax.swing.JPanel panelNumeros;
    private javax.swing.JLabel sorteoLabel;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtNum2;
    // End of variables declaration//GEN-END:variables
}
