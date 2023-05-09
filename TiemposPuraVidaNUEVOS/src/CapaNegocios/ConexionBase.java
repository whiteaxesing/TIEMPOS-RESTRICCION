/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author francisco
 */
public class ConexionBase {
    public void conectarBase() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            c.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
      System.out.println("Opened database successfully");
    }
    
    public ArrayList<Usuario> getUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            String sql = "SELECT id_usuario, nombre, contrasena FROM usuario";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id = resultadoComando.getInt("id_usuario");
                String nombre = resultadoComando.getString("nombre");
                String contrasena = resultadoComando.getString("contrasena");
                Usuario usuario = new Usuario(id, nombre, contrasena);
                usuarios.add(usuario);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return usuarios;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<Sorteo> getSorteos(){
        ArrayList<Sorteo> listaSorteos = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM sorteo INNER JOIN nombre_sorteo ON sorteo.id_nombre_sorteo = nombre_sorteo.id_nombre_sorteo;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_sorteo = resultadoComando.getInt("id_sorteo");
                int id_nombre_sorteo = resultadoComando.getInt("id_nombre_sorteo");
                String nombre = resultadoComando.getString("nombre");
                String fecha = resultadoComando.getString("fecha");
                Sorteo sorteo = new Sorteo(id_sorteo, id_nombre_sorteo, nombre, fecha);
                listaSorteos.add(sorteo);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaSorteos;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<NombreSorteo> getFechaCierreSorteos(){
        ArrayList<NombreSorteo> listaSorteos = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM nombre_sorteo";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_nombre_sorteo = resultadoComando.getInt("id_nombre_sorteo");
                String nombre = resultadoComando.getString("nombre");
                String hora_cierre = resultadoComando.getString("hora_cierre");
                NombreSorteo sorteo = new NombreSorteo(id_nombre_sorteo,nombre, hora_cierre);
                listaSorteos.add(sorteo);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaSorteos;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<Integer> getUltimoIdTicket(){
        ArrayList<Integer> listaSorteos = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_ticket FROM ticket ORDER BY id_ticket DESC LIMIT 1";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_nombre_sorteo = resultadoComando.getInt("id_ticket");
                listaSorteos.add(id_nombre_sorteo);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaSorteos;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
     
    public ArrayList<String> getNombreSorteos(){
        ArrayList<String> listaNombreSorteo = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM nombre_sorteo";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String nombre = resultadoComando.getString("nombre");
                listaNombreSorteo.add(nombre);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNombreSorteo;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<String> getIDNombreSorteos(){
        ArrayList<String> listaNombreSorteo = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM nombre_sorteo";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String nombre = resultadoComando.getString("id_nombre_sorteo");
                listaNombreSorteo.add(nombre);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNombreSorteo;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void crearTicket(int id_sorteo,int monto_total, String cliente, String fecha){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            String sql = "INSERT INTO ticket(id_sorteo,monto_total,cliente,fecha) Values ("+id_sorteo+","+monto_total+",'"+cliente+"','"+fecha+"');";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public void crearDetalleTicket(int numero, int monto, int montoReventado){
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        int id_ticket = 0;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_ticket FROM ticket ORDER BY id_ticket DESC LIMIT 1;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                id_ticket = resultadoComando.getInt("id_ticket");
            }
            comando.close();
            
            
            comando2 = conexion.createStatement();
            sql = "INSERT INTO detalle_ticket(id_ticket, id_numero, monto, monto_Reventado) "
                    + "VALUES ("+id_ticket+","+numero+","+monto+","+montoReventado+");";
            comando2.executeUpdate(sql);
            comando2.close();

            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    public void crearSorteo(int id_nombre_sorteo, String fecha){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            //String sql = "INSERT INTO sorteo(id_nombre_sorteo,fecha) Values ("+id_nombre_sorteo+",'"+fecha+"');";
            
            String sql = "INSERT INTO sorteo(id_nombre_sorteo,fecha)"
                    +"SELECT "+id_nombre_sorteo+",'"+fecha+"'" +
                    "WHERE NOT EXISTS(SELECT 1 FROM sorteo WHERE"
                    + " id_nombre_sorteo = "+id_nombre_sorteo+
                    " AND fecha = '"+fecha+"');;";
            
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public void crearNombreSorteo(String nombre_sorteo, String hora){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            String sql = "INSERT INTO nombre_sorteo(nombre,hora sorteo) Values ('"+nombre_sorteo+",'"+hora+"');";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public void borrarNombreSorteo(int id_nombre_sorteo) {
        Connection conexion = null;
        Statement comando = null;
        
        //DELETE FROM sorteo WHERE id_nombre_sorteo = 1;
        //DELETE FROM nombre_sorteo WHERE id_nombre_sorteo = 1;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "DELETE FROM sorteo WHERE id_nombre_sorteo = "+id_nombre_sorteo+";                                              ";
            comando.executeUpdate(sql);
            comando.close();
            
            comando = conexion.createStatement();
            sql = "DELETE FROM nombre_sorteo WHERE id_nombre_sorteo = "+id_nombre_sorteo+";                                              ";
            comando.executeUpdate(sql);
            comando.close();
            
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public ArrayList<NumeroSaldo> getReporteSorteo(String nombreSorteo, String fechaSorteo){
        ArrayList<NumeroSaldo> listaNumeros = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_numero, SUM(monto) AS monto, SUM(monto_reventado) AS monto_reventado FROM ticket \n" +
                "	INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                "	INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                "	INNER JOIN detalle_ticket ON ticket.id_ticket = detalle_ticket.id_ticket\n" +
                "	WHERE sorteo.fecha = '"+fechaSorteo+"'AND nombre = '"+nombreSorteo+"'\n" +
                "	GROUP BY id_numero";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int numero = resultadoComando.getInt("id_numero");
                int monto = resultadoComando.getInt("monto");
                int montoReventado = resultadoComando.getInt("monto_reventado");
                NumeroSaldo sorteo = new NumeroSaldo(numero, monto, montoReventado);
                listaNumeros.add(sorteo);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNumeros;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
      
    public void borrarTicket(int numeroTicket) {
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "DELETE FROM detalle_ticket WHERE id_ticket ="+numeroTicket+";"
                    +    "DELETE FROM ticket WHERE id_ticket =("+numeroTicket+");";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public ArrayList<PapelTiempos> reimprimir(int idTicket){
        ArrayList<PapelTiempos> papel = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();

            String sql = "select ticket.id_ticket, sorteo.fecha,nombre  from ticket \n" +
                	"	INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                	"	INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                	"where ticket.id_ticket = "+idTicket+";";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_ticket = resultadoComando.getInt("id_ticket");
                String fecha = resultadoComando.getString("fecha");
                String nombreSorteo = resultadoComando.getString("nombre");
                PapelTiempos detallePapel = new PapelTiempos(id_ticket, fecha, nombreSorteo);
                papel.add(detallePapel);
            }
            resultadoComando.close();
            comando.close();
            comando2 = conexion.createStatement();
            sql = "select ticket.id_ticket, sorteo.fecha,nombre  , detalle_ticket.id_numero, monto, monto_reventado from ticket \n" +
                "	INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                "	INNER JOIN detalle_ticket ON detalle_ticket.id_ticket = ticket.id_ticket\n" +
                "	INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                "where ticket.id_ticket = "+idTicket+";";
            
            resultadoComando = comando2.executeQuery(sql);while(resultadoComando.next()) {String numero = resultadoComando.getString("id_numero");
                int monto = resultadoComando.getInt("monto");
                int montoReventado = resultadoComando.getInt("monto_reventado");
                papel.get(0).anadirNumeros(numero);
                papel.get(0).anadirMonto(monto);
                papel.get(0).anadirMontoReventado(montoReventado);
            }
            comando2.close();
            conexion.close();
            return papel;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void llenarNumero(){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            int i = 0;
            while (i < 100){
                String sql = "INSERT INTO numero(id_numero, numero) Values ("+i+", "+i+");";
                comando.executeUpdate(sql);
                i += 1;
            }
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    public void crearTicketParley( String fecha, int monto_total){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            String sql = "INSERT INTO papel_parley(fecha,monto_total) Values ('"+fecha+"',"+monto_total+");";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public void crearDetalleParley(PapelParley parleyPapel){
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        int id_papel_parley = 0;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_papel_parley FROM papel_parley ORDER BY id_papel_parley DESC LIMIT 1;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                id_papel_parley = resultadoComando.getInt("id_papel_parley");
            }
            comando.close();
            
            
            comando2 = conexion.createStatement();
            for (int i = 0; i < parleyPapel.getNumeros().size(); i++){
                String numero = parleyPapel.getNumeros().get(i);
                String numero2 = parleyPapel.getNumeros2().get(i);
                int monto = parleyPapel.getMontos().get(i);
                sql = "INSERT INTO detalle_parley(id_papel_parley, numero, numero2, monto) "
                    + "VALUES ("+id_papel_parley+","+numero+","+numero2+","+monto+");";
                comando2.executeUpdate(sql);
            }
            comando2.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    public ArrayList<PapelParley> getReporteParley(String fechaSorteo){
        ArrayList<PapelParley> listaNumeros = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT fecha, d.numero, d.numero2, sum(monto) as monto_total \n" +
                "       FROM papel_parley as p                     \n" +
                "	INNER JOIN detalle_parley AS d              \n" +
                "	ON p.id_papel_parley = d.id_papel_parley    \n" +
                "	WHERE fecha = '"+fechaSorteo+"'      \n" +
                "	GROUP BY fecha,d.numero, d.numero2;";
            
            
            PapelParley sorteo = new PapelParley();
            sorteo.setFechaTicket(fechaSorteo);
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String numero = resultadoComando.getString("numero");
                String numero2 = resultadoComando.getString("numero2");
                int monto = resultadoComando.getInt("monto_total");
                sorteo.anadirNumeros(numero);
                sorteo.anadirNumeros2(numero2);
                sorteo.anadirMonto(monto);
            }
            listaNumeros.add(sorteo);
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNumeros;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<Integer> getUltimoIdParley(){
        ArrayList<Integer> listaSorteos = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_papel_parley FROM papel_parley ORDER BY id_papel_parley DESC LIMIT 1;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_papel_parley = resultadoComando.getInt("id_papel_parley");
                listaSorteos.add(id_papel_parley);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaSorteos;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<PapelParley> reimprimirParley(int idTicket){
        ArrayList<PapelParley> papel = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            
            String sql = "SELECT fecha,p.id_papel_parley, numero, numero2, monto \n" +
                	"	FROM papel_parley as p \n" +
                	"	INNER JOIN detalle_parley \n" +
                	"	ON p.id_papel_parley = detalle_parley.id_papel_parley\n" +
                	"where p.id_papel_parley  = "+idTicket+";";
            
            ResultSet resultadoComando = comando.executeQuery(sql);
            PapelParley detallePapel = new PapelParley();
            while(resultadoComando.next()) {
                int id_ticket = resultadoComando.getInt("id_papel_parley");
                String fecha = resultadoComando.getString("fecha");
                String numero = resultadoComando.getString("numero");
                String numero2 = resultadoComando.getString("numero2");
                int monto = resultadoComando.getInt("monto");
                detallePapel.setNumeroTicket(id_ticket);
                detallePapel.setFechaTicket(fecha);
                detallePapel.anadirNumeros(numero);
                detallePapel.anadirNumeros2(numero2);
                detallePapel.anadirMonto(monto);
                
            }
            papel.add(detallePapel);
            resultadoComando.close();
            comando.close();
            conexion.close();
            return papel;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void borrarParley(int numeroParley) {
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "DELETE FROM detalle_parley WHERE id_papel_parley ="+numeroParley+";"
                    +    "DELETE FROM papel_parley WHERE id_papel_parley =("+numeroParley+");";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public ArrayList<String> getNombrePega3(){
        ArrayList<String> listaNombreSorteo = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM nombre_pega3;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String nombre = resultadoComando.getString("nombre");
                listaNombreSorteo.add(nombre);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNombreSorteo;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<NombreSorteo> getNombreCierrePega3(){
        ArrayList<NombreSorteo> listaSorteos = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT * FROM nombre_pega3";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                int id_nombre_sorteo = resultadoComando.getInt("id_nombre_pega3");
                String nombre = resultadoComando.getString("nombre");
                String hora_cierre = resultadoComando.getString("hora_cierre");
                NombreSorteo sorteo = new NombreSorteo(id_nombre_sorteo,nombre, hora_cierre);
                listaSorteos.add(sorteo);
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaSorteos;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void crearTicketPega3( String fecha, int monto_total, int id_nombre_pega3){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            comando = conexion.createStatement();
            String sql = "INSERT INTO papel_pega3(fecha, monto_total,id_nombre_pega3) VALUES ('"+fecha+"',"+monto_total+","+id_nombre_pega3+");";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public int getUltimoPega3(){
        Connection conexion = null;
        Statement comando = null;
        int id_papel_pega3 = 0;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_papel_pega3 FROM papel_pega3 ORDER BY id_papel_pega3 DESC LIMIT 1;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                id_papel_pega3 = resultadoComando.getInt("id_papel_pega3");
            }
            comando.close();
            conexion.close();
            return(id_papel_pega3);
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return(0);
        }
    }
    
    public void crearDetallePega3(PapelPega3 papelPega3){
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        int id_papel_pega3 = 0;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT id_papel_pega3 FROM papel_pega3 ORDER BY id_papel_pega3 DESC LIMIT 1;";
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                id_papel_pega3 = resultadoComando.getInt("id_papel_pega3");
            }
            comando.close();
            comando2 = conexion.createStatement();
            for (int i = 0; i < papelPega3.getNumeros().size(); i++){
                String numero = papelPega3.getNumeros().get(i);
                int monto = papelPega3.getMontos().get(i);
                String modalidad = papelPega3.getModalidades().get(i);
                sql = "INSERT INTO detalle_pega3(id_papel_pega3, numero, monto, modalidad) "
                    + "VALUES ("+id_papel_pega3+",'"+numero+"',"+monto+",'"+modalidad+"');";
                comando2.executeUpdate(sql);
            }
            comando2.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    public PapelPega3 getPapelPega3(int idTicket){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            
            String sql = "SELECT p.id_papel_pega3, nombre, fecha,numero, monto, modalidad FROM papel_pega3 as p\n" +
                        "	INNER JOIN detalle_pega3 as d\n" +
                        "		ON p.id_papel_pega3 = d.id_papel_pega3\n" +
                        "	INNER JOIN nombre_pega3 as n\n" +
                        "		ON p.id_nombre_pega3 = n.id_nombre_pega3\n" +
                        "	WHERE p.id_papel_pega3 = "+idTicket+";";
            ResultSet resultadoComando = comando.executeQuery(sql);
            PapelPega3 papel = new PapelPega3("a", "a");
            while(resultadoComando.next()) {
                papel.setNumeroTicket(idTicket);
                papel.setFechaTicket(resultadoComando.getString("fecha"));
                papel.setNombreSorteo(resultadoComando.getString("nombre"));
                papel.anadirNumeros(resultadoComando.getString("numero"));
                papel.anadirMonto(resultadoComando.getInt("monto"));
                papel.anadirModalidades(resultadoComando.getString("modalidad"));
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return papel;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void borrarPega3(int idPega3) {
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            

            comando = conexion.createStatement();
            String sql = "DELETE FROM detalle_pega3 WHERE id_papel_pega3 ="+idPega3+";"
                    +    "DELETE FROM papel_pega3 WHERE id_papel_pega3 =("+idPega3+");";
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos" + "\n"+ 
                                    e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
      }
    }
    
    public PapelPega3 getReportePega3(String nombreSorteo, String fechaSorteo){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT numero, sum(monto) as monto, modalidad  FROM papel_pega3 AS p \n" +
                "       INNER JOIN detalle_pega3 AS d                      \n" +
                "       	ON p.id_papel_pega3 = d.id_papel_pega3     \n" +
                "       INNER JOIN nombre_pega3 AS n                      \n" +
                "               ON p.id_nombre_pega3 = n.id_nombre_pega3                     \n" +
                "       WHERE nombre = '"+nombreSorteo+"' and fecha = '"+fechaSorteo+
                "' Group by numero, modalidad;";
            PapelPega3 sorteo = new PapelPega3(fechaSorteo, nombreSorteo);
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String numero = resultadoComando.getString("numero");
                int monto = resultadoComando.getInt("monto");
                String modalidad = resultadoComando.getString("modalidad");
                sorteo.anadirNumeros(numero);
                sorteo.anadirModalidades(modalidad);
                sorteo.anadirMonto(monto);
            }
            resultadoComando.close();
            comando.close();
            
            
            conexion.close();
            return sorteo;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<reporteJuegosTotal> getReporteTotal(String fechaSorteo){
    Connection conexion = null;
    Statement comando = null;
    Statement comando2 = null;
    Statement comando3 = null;
    ArrayList<reporteJuegosTotal> listaReportes = new ArrayList<>();
    try {
        Class.forName("org.postgresql.Driver");
        conexion = DriverManager
                .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                "postgres", "MIlan123");

        comando = conexion.createStatement();
        String sql = "SELECT nombre, SUM(monto) AS monto_total ,SUM(monto_Reventado) AS monto_reventado_total FROM ticket \n" +
                    "        INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                    "        INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                    "        INNER JOIN detalle_ticket ON ticket.id_ticket = detalle_ticket.id_ticket\n" +
                    "        WHERE sorteo.fecha = '"+fechaSorteo+"'\n" +
                    "GROUP BY nombre \n" +
                    "ORDER BY nombre DESC;";


        ResultSet resultadoComando = comando.executeQuery(sql);
        while(resultadoComando.next()) {
            reporteJuegosTotal reporte = new reporteJuegosTotal();
            reporte.setNombreSorteo(resultadoComando.getString("nombre"));
            reporte.setMonto(resultadoComando.getInt("monto_total"));
            reporte.setMontoReventado(resultadoComando.getInt("monto_reventado_total"));
            listaReportes.add(reporte);
        }
        resultadoComando.close();
        comando.close();

        comando2 = conexion.createStatement();
        sql = "SELECT nombre, Sum(monto) as monto_total  FROM papel_pega3 AS p \n" +
        "                   INNER JOIN detalle_pega3 AS d ON p.id_papel_pega3 = d.id_papel_pega3     \n" +
        "                   INNER JOIN nombre_pega3 AS n ON p.id_nombre_pega3 = n.id_nombre_pega3                     \n" +
        "                   WHERE fecha = '"+fechaSorteo+"' \n" +
        "               Group by nombre \n" +
        "               Order by nombre DESC;";
        ResultSet resultadoComando2 = comando2.executeQuery(sql);
        while(resultadoComando2.next()) {
            reporteJuegosTotal reporte = new reporteJuegosTotal();
            reporte.setNombreSorteo(resultadoComando2.getString("nombre"));
            reporte.setMonto(resultadoComando2.getInt("monto_total"));
            reporte.setMontoReventado(0);
            listaReportes.add(reporte);
        }
        resultadoComando2.close();
        comando2.close();


        comando3 = conexion.createStatement();
        sql = "SELECT fecha, sum(monto) as monto_total \n" +
"                       FROM papel_parley as p                     \n" +
"                	INNER JOIN detalle_parley AS d              \n" +
"                	ON p.id_papel_parley = d.id_papel_parley    \n" +
"                	WHERE fecha = '"+fechaSorteo+"'      \n" +
"                	GROUP BY fecha;";
        ResultSet resultadoComando3 = comando3.executeQuery(sql);
        while(resultadoComando3.next()) {
            reporteJuegosTotal reporte = new reporteJuegosTotal();
            reporte.setNombreSorteo("Parley");
            reporte.setMonto(resultadoComando3.getInt("monto_total"));
            reporte.setMontoReventado(0);
            listaReportes.add(reporte);
        }
        resultadoComando3.close();
        comando3.close();

        conexion.close();
        return listaReportes;

    }catch(Exception e) {
        System.out.println(e.getMessage());
    }
    return null;
}
        
    public ArrayList<reporteJuegosTotal> getReporteDia(String fechaSorteo){
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        Statement comando3 = null;
        ArrayList<reporteJuegosTotal> listaReportes = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT nombre, SUM(monto) AS monto_total ,SUM(monto_Reventado) AS monto_reventado_total , hora_cierre FROM ticket \n" +
                        "        INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                        "        INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                        "        INNER JOIN detalle_ticket ON ticket.id_ticket = detalle_ticket.id_ticket\n" +
                        "        WHERE sorteo.fecha = '"+fechaSorteo+"'\n" +
                        "GROUP BY nombre , nombre_sorteo.hora_cierre  \n" +
                        "ORDER BY nombre DESC;";
            
                
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                
                reporteJuegosTotal reporte = new reporteJuegosTotal();
                reporte.setNombreSorteo(resultadoComando.getString("nombre"));
                reporte.setMonto(resultadoComando.getInt("monto_total"));
                reporte.setMontoReventado(resultadoComando.getInt("monto_reventado_total"));
                String horaSorteo = resultadoComando.getString("hora_cierre");
                System.out.println(horaSorteo);
                
                String[] partes = horaSorteo.split(":");
                int horaCierre = Integer.parseInt(partes[0]);
                if(horaCierre < 13){
                    listaReportes.add(reporte);
                }
            }
            resultadoComando.close();
            comando.close();
            
            comando2 = conexion.createStatement();
            sql = "SELECT nombre, Sum(monto) as monto_total , hora_cierre FROM papel_pega3 AS p \n" +
            "                   INNER JOIN detalle_pega3 AS d ON p.id_papel_pega3 = d.id_papel_pega3     \n" +
            "                   INNER JOIN nombre_pega3 AS n ON p.id_nombre_pega3 = n.id_nombre_pega3                     \n" +
            "                   WHERE fecha = '"+fechaSorteo+"' \n" +
            "               Group by nombre ,n.hora_cierre \n" +
            "               Order by nombre DESC;";
            ResultSet resultadoComando2 = comando2.executeQuery(sql);
            while(resultadoComando2.next()) {
                reporteJuegosTotal reporte = new reporteJuegosTotal();
                reporte.setNombreSorteo(resultadoComando2.getString("nombre"));
                reporte.setMonto(resultadoComando2.getInt("monto_total"));
                reporte.setMontoReventado(0);
                String horaSorteo = resultadoComando2.getString("hora_cierre");
                String[] partes = horaSorteo.split(":");
                int horaCierre = Integer.parseInt(partes[0]);
                if(horaCierre < 13){
                    listaReportes.add(reporte);
                }
            }
            resultadoComando2.close();
            comando2.close();
            
            conexion.close();
            return listaReportes;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<reporteJuegosTotal> getReporteNoche(String fechaSorteo){
        Connection conexion = null;
        Statement comando = null;
        Statement comando2 = null;
        Statement comando3 = null;
        ArrayList<reporteJuegosTotal> listaReportes = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT nombre, SUM(monto) AS monto_total ,SUM(monto_Reventado) AS monto_reventado_total , hora_cierre FROM ticket \n" +
                        "        INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
                        "        INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
                        "        INNER JOIN detalle_ticket ON ticket.id_ticket = detalle_ticket.id_ticket\n" +
                        "        WHERE sorteo.fecha = '"+fechaSorteo+"'\n" +
                        "GROUP BY nombre , nombre_sorteo.hora_cierre  \n" +
                        "ORDER BY nombre DESC;";
            
                
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                
                reporteJuegosTotal reporte = new reporteJuegosTotal();
                reporte.setNombreSorteo(resultadoComando.getString("nombre"));
                reporte.setMonto(resultadoComando.getInt("monto_total"));
                reporte.setMontoReventado(resultadoComando.getInt("monto_reventado_total"));
                String horaSorteo = resultadoComando.getString("hora_cierre");
                String[] partes = horaSorteo.split(":");
                int horaCierre = Integer.parseInt(partes[0]);
                if(horaCierre >= 13){
                    listaReportes.add(reporte);
                }
            }
            resultadoComando.close();
            comando.close();
            
            comando2 = conexion.createStatement();
            sql = "SELECT nombre, Sum(monto) as monto_total , hora_cierre FROM papel_pega3 AS p \n" +
            "                   INNER JOIN detalle_pega3 AS d ON p.id_papel_pega3 = d.id_papel_pega3     \n" +
            "                   INNER JOIN nombre_pega3 AS n ON p.id_nombre_pega3 = n.id_nombre_pega3                     \n" +
            "                   WHERE fecha = '"+fechaSorteo+"' \n" +
            "               Group by nombre ,n.hora_cierre \n" +
            "               Order by nombre DESC;";
            ResultSet resultadoComando2 = comando2.executeQuery(sql);
            while(resultadoComando2.next()) {
                reporteJuegosTotal reporte = new reporteJuegosTotal();
                reporte.setNombreSorteo(resultadoComando2.getString("nombre"));
                reporte.setMonto(resultadoComando2.getInt("monto_total"));
                reporte.setMontoReventado(0);
                String horaSorteo = resultadoComando2.getString("hora_cierre");
                String[] partes = horaSorteo.split(":");
                int horaCierre = Integer.parseInt(partes[0]);
                if(horaCierre > 13){
                    listaReportes.add(reporte);
                }
            }
            resultadoComando2.close();
            comando2.close();
            
            comando3 = conexion.createStatement();
        sql = "SELECT fecha, sum(monto) as monto_total \n" +
        "                       FROM papel_parley as p                     \n" +
        "                	INNER JOIN detalle_parley AS d              \n" +
        "                	ON p.id_papel_parley = d.id_papel_parley    \n" +
        "                	WHERE fecha = '"+fechaSorteo+"'      \n" +
        "                	GROUP BY fecha;";
        ResultSet resultadoComando3 = comando3.executeQuery(sql);
        while(resultadoComando3.next()) {
            reporteJuegosTotal reporte = new reporteJuegosTotal();
            reporte.setNombreSorteo("Parley");
            reporte.setMonto(resultadoComando3.getInt("monto_total"));
            reporte.setMontoReventado(0);
            listaReportes.add(reporte);
        }
        resultadoComando3.close();
        comando3.close();

        conexion.close();
            return listaReportes;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Integer getMontoVendidoTiempos(String nombreSorteo, String fechaSorteo, int numero){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            
            String sql =  "SELECT id_numero, SUM(monto) AS monto FROM ticket \n" +
            "                	INNER JOIN sorteo ON ticket.id_sorteo = SORTEO.id_sorteo \n" +
            "                	INNER JOIN NOMBRE_SORTEO ON sorteo.id_nombre_sorteo = NOMBRE_SORTEO.id_nombre_sorteo\n" +
            "                	INNER JOIN detalle_ticket ON ticket.id_ticket = detalle_ticket.id_ticket \n" +
            "              WHERE sorteo.fecha = '"+fechaSorteo+"' AND nombre = '"+nombreSorteo+"' AND detalle_ticket.id_numero = '"+numero+"'\n" +
            "              GROUP BY id_numero;";
            
            ResultSet resultadoComando = comando.executeQuery(sql);
            int monto = 0;
            while(resultadoComando.next()) {
                monto = resultadoComando.getInt("monto");
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return monto;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Integer getMontoVendidoPega3(String nombreSorteo, String fechaSorteo, int numero ,String modalidad){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            
            String sql = "SELECT numero, sum(monto) as monto, modalidad  FROM papel_pega3 AS p                      \n" +
"                       INNER JOIN detalle_pega3 AS d                                                               \n" +
"                       ON p.id_papel_pega3 = d.id_papel_pega3                                                      \n" +
"                       INNER JOIN nombre_pega3 AS n                                                                \n" +
"                       ON p.id_nombre_pega3 = n.id_nombre_pega3                                                    \n" +
"                       WHERE nombre = '"+nombreSorteo+"' and fecha = '"+fechaSorteo+"' and modalidad='"+modalidad+"' and numero='"+numero+"'\n" +
"                Group by numero, modalidad;";
            
     
            ResultSet resultadoComando = comando.executeQuery(sql);
            int montoRestante = 0;
            while(resultadoComando.next()) {
                montoRestante = resultadoComando.getInt("monto");
            }
            resultadoComando.close();
            comando.close();
            
            
            conexion.close();
            return montoRestante;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public ArrayList<PapelParley> getMontoVendidoParley(String fechaSorteo){
        ArrayList<PapelParley> listaNumeros = new ArrayList<>();
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            String sql = "SELECT fecha, d.numero, d.numero2, sum(monto) as monto_total \n" +
                "       FROM papel_parley as p                     \n" +
                "	INNER JOIN detalle_parley AS d              \n" +
                "	ON p.id_papel_parley = d.id_papel_parley    \n" +
                "	WHERE fecha = '"+fechaSorteo+"'      \n" +
                "	GROUP BY fecha,d.numero, d.numero2;";
            
            
            PapelParley sorteo = new PapelParley();
            sorteo.setFechaTicket(fechaSorteo);
            ResultSet resultadoComando = comando.executeQuery(sql);
            while(resultadoComando.next()) {
                String numero = resultadoComando.getString("numero");
                String numero2 = resultadoComando.getString("numero2");
                int monto = resultadoComando.getInt("monto_total");
                sorteo.anadirNumeros(numero);
                sorteo.anadirNumeros2(numero2);
                sorteo.anadirMonto(monto);
            }
            listaNumeros.add(sorteo);
            resultadoComando.close();
            comando.close();
            conexion.close();
            return listaNumeros;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Integer getLimiteMonto(String tipoJuego){
        Connection conexion = null;
        Statement comando = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/tiempos",
                    "postgres", "MIlan123");
            
            comando = conexion.createStatement();
            
            String sql =  "SELECT limite FROM parametro WHERE juego = '"+tipoJuego+"';";
            
            ResultSet resultadoComando = comando.executeQuery(sql);
            int numero = 0;
            while(resultadoComando.next()) {
                numero = resultadoComando.getInt("limite");
            }
            resultadoComando.close();
            comando.close();
            conexion.close();
            return numero;
            
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}


