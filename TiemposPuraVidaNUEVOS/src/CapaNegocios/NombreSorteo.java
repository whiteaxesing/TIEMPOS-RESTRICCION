package CapaNegocios;

public class NombreSorteo {
    private int id_nombre_sorteo;
    private String nombre;
    private String hora_cierre;

    public NombreSorteo(int id_nombre_sorteo, String nombre, String hora_cierre) {
        this.id_nombre_sorteo = id_nombre_sorteo;
        this.nombre = nombre;
        this.hora_cierre = hora_cierre;
    }

    public int getId_nombre_sorteo() {
        return id_nombre_sorteo;
    }

    public void setId_nombre_sorteo(int id_nombre_sorteo) {
        this.id_nombre_sorteo = id_nombre_sorteo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora_cierre() {
        return hora_cierre;
    }

    public void setHora_cierre(String hora_cierre) {
        this.hora_cierre = hora_cierre;
    }
}
