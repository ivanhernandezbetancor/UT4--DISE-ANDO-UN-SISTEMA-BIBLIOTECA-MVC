package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.EstadoLibro;
import model.Genero;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import model.excepciones.BloqueoPrestamoException;
import model.excepciones.Libro_NoDisponible_Exception;
import model.excepciones.Limiteprestamos_excedido_Excepciones;
import view.Consola;

public class GestorBiblioteca {

    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;
    private Consola consola;
    private int contadorPrestamos;

    public GestorBiblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.consola = new Consola();
        this.contadorPrestamos = 1;
    }

    // Parte Iván
    // Añado un libro al sistema
    public void agregarLibro(Libro libro) {
        libros.add(libro);
        consola.mostrarMensaje("Libro añadido: " + libro.getTitulo());
    }

    // Parte Iván
    // Elimino un libro por ISBN
    public void eliminarLibro(String codigoIsbn) {
        Libro libroEncontrado = buscarLibroPorIsbn(codigoIsbn);

        if (libroEncontrado == null) {
            consola.mostrarError("No se encontro ningun libro con ISBN: " + codigoIsbn);
            return;
        }

        libros.remove(libroEncontrado);
        consola.mostrarMensaje("Libro eliminado: " + libroEncontrado.getTitulo());
    }

    // Parte Iván
    // Registro un nuevo usuario
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        consola.mostrarMensaje("Usuario registrado: " + usuario.getNombre());
    }

    // Parte Iván
    // Elimino un usuario por ID
    public void eliminarUsuario(String identificadorUsuario) {
        Usuario usuarioEncontrado = buscarUsuarioPorId(identificadorUsuario);

        if (usuarioEncontrado == null) {
            consola.mostrarError("No se encontro ningun usuario con ID: " + identificadorUsuario);
            return;
        }

        usuarios.remove(usuarioEncontrado);
        consola.mostrarMensaje("Usuario eliminado: " + usuarioEncontrado.getNombre());
    }

    //  IVÁN 
    // Gestiono el préstamo completo del libro
    public void prestarLibro(String identificadorUsuario, String codigoIsbn) {
        try {
            Usuario usuario = buscarUsuarioPorId(identificadorUsuario);
            Libro libro = buscarLibroPorIsbn(codigoIsbn);

            validarExistenciaUsuario(usuario, identificadorUsuario);
            validarExistenciaLibro(libro, codigoIsbn);
            validarLimitePrestamosUsuario(usuario);
            validarDisponibilidadLibro(libro);
            validarBloqueoPrestamoAnterior(identificadorUsuario, codigoIsbn);

            Prestamo prestamoNuevo = crearPrestamoNuevo(usuario, libro);
            actualizarEstadoLibroAlPrestar(libro);

            usuario.getPrestamosActivos().add(prestamoNuevo);
            prestamos.add(prestamoNuevo);

            consola.mostrarMensaje("Prestamo realizado correctamente.");
            consola.mostrarPrestamo(prestamoNuevo);

        } catch (Limiteprestamos_excedido_Excepciones excepcion) {
            consola.mostrarError("El usuario ya tiene 3 libros prestados. No puede pedir mas.");
        } catch (Libro_NoDisponible_Exception excepcion) {
            consola.mostrarError("El libro no esta disponible para prestamo.");
        } catch (BloqueoPrestamoException excepcion) {
            consola.mostrarError("No puedes volver a pedir este libro todavia. Espera 7 dias desde la devolucion.");
        } catch (IllegalArgumentException excepcion) {
            consola.mostrarError(excepcion.getMessage());
        }
    }

