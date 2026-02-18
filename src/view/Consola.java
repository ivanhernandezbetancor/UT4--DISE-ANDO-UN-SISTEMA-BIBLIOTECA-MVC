package view;

import java.util.ArrayList;

import model.Libro;
import model.Prestamo;
import model.Usuario;

public class Consola {

     // Parte de ivan
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

     // Parte de ivan
    public void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }

     // Parte de ivan
    public void mostrarLibro(Libro libro) {
        System.out.println("ISBN: " + libro.getIsbn());
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor());
        System.out.println("Año: " + libro.getaniodePublicacion());
        System.out.println("Editorial: " + libro.getEditorial());
        System.out.println("Género: " + libro.getGenero());
        System.out.println("Copias disponibles: " + libro.getCopiasDisponibles() + "/" + libro.getTotalCopias());
        System.out.println("Estado: " + libro.getEstado());
    }

    // Parte de ivan
    public void mostrarListaLibros(ArrayList<Libro> libros) {
        if (libros.size() == 0) {
            System.out.println("No hay libros para mostrar.");
        } else {
            for (Libro libro : libros) {
                mostrarLibro(libro);
            }
        }
    }

     //Parte Izan
    public void mostrarUsuario(Usuario usuario) {
        System.out.println("ID: " + usuario.getIdUsuario());
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Préstamos activos: " + usuario.getPrestamosActivos().size());
    }

    //Parte Izan
    public void mostrarListaUsuarios(ArrayList<Usuario> usuarios) {
        if (usuarios.size() == 0) {
            System.out.println("No hay usuarios para mostrar.");
        } else {
            for (Usuario usuario : usuarios) {
                mostrarUsuario(usuario);
            }
        }
    }

    //Parte Izan
    public void mostrarPrestamo(Prestamo prestamo) {
        System.out.println("ID Préstamo: " + prestamo.getIdPrestamo());
        System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
        System.out.println("Libro: " + prestamo.getLibro().getTitulo());
        System.out.println("Fecha préstamo: " + prestamo.getFechaPrestamo());
        System.out.println("Fecha vencimiento: " + prestamo.getFechaVencimiento());
        if (prestamo.getFechaDevolucion() != null) {
            System.out.println("Fecha devolución: " + prestamo.getFechaDevolucion());
        } else {
            System.out.println("Fecha devolución: pendiente");
        }
    }

    //Parte Izan
    public void mostrarListaPrestamos(ArrayList<Prestamo> prestamos) {
        if (prestamos.size() == 0) {
            System.out.println("No hay préstamos para mostrar.");
        } else {
            for (Prestamo prestamo : prestamos) {
                mostrarPrestamo(prestamo);
            }
        }
    }

    //Parte Izan
    public void mostrarResumenBiblioteca(ArrayList<Libro> libros, ArrayList<Usuario> usuarios) {
        System.out.println("===== RESUMEN DE BIBLIOTECA =====");
        System.out.println("--- Libros ---");
        mostrarListaLibros(libros);
        System.out.println("--- Usuarios ---");
        mostrarListaUsuarios(usuarios);
        System.out.println("=================================");
    }

    //Parte Izan
    public void mostrarLibrosPorUsuario(Usuario usuario) {
        System.out.println("Libros prestados a " + usuario.getNombre() + ":");
        if (usuario.getPrestamosActivos().size() == 0) {
            System.out.println("  Sin préstamos activos.");
        } else {
            for (Prestamo prestamo : usuario.getPrestamosActivos()) {
                System.out.println("  - " + prestamo.getLibro().getTitulo()
                        + " (vence: " + prestamo.getFechaVencimiento() + ")");
            }
        }
    }
}
