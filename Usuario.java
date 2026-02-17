package model;
import java.util.ArrayList;

public class Usuario {

    private String idUsuario;
    private String nombre;
    private ArrayList<Prestamo> prestamosActivos;
    private ArrayList<Libro> historialLibros;

    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.prestamosActivos = new ArrayList<>();
        this.historialLibros = new ArrayList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    public void setPrestamosActivos(ArrayList<Prestamo> prestamosActivos) {
        this.prestamosActivos = prestamosActivos;
    }

    public ArrayList<Libro> getHistorialLibros() {
        return historialLibros;
    }

    public void setHistorialLibros(ArrayList<Libro> historialLibros) {
        this.historialLibros = historialLibros;
    }
}
