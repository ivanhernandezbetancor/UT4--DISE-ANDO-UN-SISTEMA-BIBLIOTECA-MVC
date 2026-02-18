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
