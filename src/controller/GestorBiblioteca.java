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

    // Parte Iván
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

//Parte Izan
    // Métodos auxiliares del préstamo

    private void validarExistenciaUsuario(Usuario usuario, String identificadorUsuario)
            throws IllegalArgumentException {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuario no encontrado con identificador: " + identificadorUsuario);
        }
    }

    private void validarExistenciaLibro(Libro libro, String codigoIsbn)
            throws IllegalArgumentException {

        if (libro == null) {
            throw new IllegalArgumentException(
                    "Libro no encontrado con codigo ISBN: " + codigoIsbn);
        }
    }

    private void validarLimitePrestamosUsuario(Usuario usuario)
            throws Limiteprestamos_excedido_Excepciones {

        int cantidadPrestamosActivos = usuario.getPrestamosActivos().size();

        if (cantidadPrestamosActivos >= 3) {
            throw new Limiteprestamos_excedido_Excepciones();
        }
    }

    private void validarDisponibilidadLibro(Libro libro)
            throws Libro_NoDisponible_Exception {

        boolean libroNoDisponible = libro.getEstado() != EstadoLibro.DISPONIBLE ||
                libro.getCopiasDisponibles() == 0;

        if (libroNoDisponible) {
            throw new Libro_NoDisponible_Exception();
        }
    }

    private void validarBloqueoPrestamoAnterior(String identificadorUsuario, String codigoIsbn)
            throws BloqueoPrestamoException {

        for (Prestamo prestamoAnterior : prestamos) {

            boolean esDelMismoUsuario = prestamoAnterior.getUsuario().getIdUsuario().equals(identificadorUsuario);
            boolean esDelMismoLibro = prestamoAnterior.getLibro().getIsbn().equals(codigoIsbn);
            boolean yaFueDevuelto = prestamoAnterior.getFechaDevolucion() != null;

            if (esDelMismoUsuario && esDelMismoLibro && yaFueDevuelto) {

                long diasPrestado = prestamoAnterior.getFechaPrestamo()
                        .until(prestamoAnterior.getFechaDevolucion())
                        .getDays();

                if (diasPrestado >= 30) {

                    LocalDate fechaFinBloqueo = prestamoAnterior.getFechaDevolucion().plusDays(7);

                    if (LocalDate.now().isBefore(fechaFinBloqueo)) {
                        throw new BloqueoPrestamoException();
                    }
                }
            }
        }
    }

    private Prestamo crearPrestamoNuevo(Usuario usuario, Libro libro) {
        String identificadorPrestamo = "P" + contadorPrestamos++;
        Prestamo prestamoNuevo = new Prestamo(identificadorPrestamo, usuario, libro);
        return prestamoNuevo;
    }

    private void actualizarEstadoLibroAlPrestar(Libro libro) {
        int copiasActuales = libro.getCopiasDisponibles();
        libro.setCopiasDisponibles(copiasActuales - 1);

        if (libro.getCopiasDisponibles() == 0) {
            libro.setEstado(EstadoLibro.PRESTADO);
        }
    }

    //Parte Izan
    // Devolución de libros

    public void devolverLibro(String identificadorUsuario, String codigoIsbn) {
        try {
            Usuario usuarioEncontrado = buscarUsuarioPorId(identificadorUsuario);
            validarExistenciaUsuario(usuarioEncontrado, identificadorUsuario);

            Prestamo prestamoActivoDelUsuario = buscarPrestamoActivoEnUsuario(usuarioEncontrado, codigoIsbn);

            if (prestamoActivoDelUsuario == null) {
                throw new IllegalArgumentException(
                        "Este usuario no tiene prestado el libro con ISBN: " + codigoIsbn);
            }

            procesarDevolucionDelLibro(usuarioEncontrado, prestamoActivoDelUsuario);

            consola.mostrarMensaje("Devolucion registrada correctamente.");

        } catch (IllegalArgumentException excepcion) {
            consola.mostrarError(excepcion.getMessage());
        }
    }

    private Prestamo buscarPrestamoActivoEnUsuario(Usuario usuario, String codigoIsbn) {
        for (Prestamo prestamo : usuario.getPrestamosActivos()) {
            if (prestamo.getLibro().getIsbn().equals(codigoIsbn)) {
                return prestamo;
            }
        }
        return null;
    }

    private void procesarDevolucionDelLibro(Usuario usuario, Prestamo prestamo) {

        prestamo.setFechaDevolucion(LocalDate.now());
        usuario.getPrestamosActivos().remove(prestamo);
        usuario.getHistorialLibros().add(prestamo.getLibro());

        Libro libroDevuelto = prestamo.getLibro();
        libroDevuelto.setCopiasDisponibles(libroDevuelto.getCopiasDisponibles() + 1);

        if (libroDevuelto.getEstado() != EstadoLibro.RESERVADO) {
            libroDevuelto.setEstado(EstadoLibro.DISPONIBLE);
        }
    }

    // Parte Iván
    // Reserva de libros
    public void reservarLibro(String identificadorUsuario, String codigoIsbn) {
        try {
            Usuario usuarioEncontrado = buscarUsuarioPorId(identificadorUsuario);
            Libro libroEncontrado = buscarLibroPorIsbn(codigoIsbn);

            validarExistenciaUsuario(usuarioEncontrado, identificadorUsuario);
            validarExistenciaLibro(libroEncontrado, codigoIsbn);

            if (libroEncontrado.getEstado() == EstadoLibro.RESERVADO ||
                    libroEncontrado.getEstado() == EstadoLibro.DISPONIBLE) {

                throw new Libro_NoDisponible_Exception();
            }

            libroEncontrado.setEstado(EstadoLibro.RESERVADO);
            consola.mostrarMensaje("Libro reservado correctamente.");

        } catch (Libro_NoDisponible_Exception excepcion) {
            consola.mostrarError("El libro no esta disponible para reserva.");
        } catch (IllegalArgumentException excepcion) {
            consola.mostrarError(excepcion.getMessage());
        }
    }

    // Parte Iván
    public Libro buscarLibroPorIsbn(String codigoIsbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(codigoIsbn)) {
                return libro;
            }
        }
        return null;
    }

        // Parte Iván
    public ArrayList<Libro> buscarLibrosPorTitulo(String titulo) {
        ArrayList<Libro> resultadoBusqueda = new ArrayList<>();

        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultadoBusqueda.add(libro);
            }
        }

        return resultadoBusqueda;
    }

        // Parte Iván
    public ArrayList<Libro> buscarLibrosPorGenero(Genero genero) {
        ArrayList<Libro> resultadoBusqueda = new ArrayList<>();

        for (Libro libro : libros) {
            if (libro.getGenero() == genero) {
                resultadoBusqueda.add(libro);
            }
        }

        return resultadoBusqueda;
    }

//Parte Izan

    public Usuario buscarUsuarioPorId(String identificadorUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario().equals(identificadorUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    public void buscarUsuarioConLibro(String codigoIsbn) {
        for (Usuario usuario : usuarios) {
            for (Prestamo prestamo : usuario.getPrestamosActivos()) {
                if (prestamo.getLibro().getIsbn().equals(codigoIsbn)) {
                    consola.mostrarMensaje("El libro esta prestado a: " + usuario.getNombre()
                            + " (ID: " + usuario.getIdUsuario() + ")");
                    return;
                }
            }
        }
        consola.mostrarMensaje("Ningun usuario tiene prestado ese libro actualmente.");
    }

    public void mostrarResumenLibros() {
        consola.mostrarMensaje("===== RESUMEN DE LIBROS =====");
        consola.mostrarListaLibros(libros);
    }

    public void mostrarResumenUsuarios() {
        consola.mostrarMensaje("===== RESUMEN DE USUARIOS =====");
        for (Usuario usuario : usuarios) {
            consola.mostrarLibrosPorUsuario(usuario);
        }
    }

    public void mostrarResumenBiblioteca() {
        consola.mostrarResumenBiblioteca(libros, usuarios);
    }
}
