package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GestorBiblioteca;
import model.Genero;
import model.Libro;
import model.Usuario;

public class Main {

    private static GestorBiblioteca gestor = new GestorBiblioteca();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        cargarDatosDePrueba();

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero("Elige una opcion: ");
            procesarOpcion(opcion);
        }

        scanner.close();
        System.out.println("Hasta luego.");
    }

    private static void mostrarMenu() {
        System.out.println("\n");
        System.out.println("\n===============================");
        System.out.println("SISTEMA DE GESTION BIBLIOTECA");
        System.out.println("===============================");
        System.out.println(" 1. Prestar libro");
        System.out.println(" 2. Devolver libro");
        System.out.println(" 3. Reservar libro");
        System.out.println(" 4. Buscar libro por Codigo ISBN");
        System.out.println(" 5. Buscar libros por titulo");
        System.out.println(" 6. Buscar libros por genero");
        System.out.println(" 7. Ver quien tiene un libro");
        System.out.println(" 8. Agregar libro");
        System.out.println(" 9. Agregar usuario");
        System.out.println("10. Eliminar libro");
        System.out.println("11. Eliminar usuario");
        System.out.println("12. Resumen de libros");
        System.out.println("13. Resumen de usuarios");
        System.out.println("14. Resumen completo");
        System.out.println(" 0. Salir");
        System.out.println("\n");
    }

    private static void procesarOpcion(int opcion) {
        System.out.println();
        switch (opcion) {
            case 1:
                opcionPrestarLibro();
                break;
            case 2:
                opcionDevolverLibro();
                break;
            case 3:
                opcionReservarLibro();
                break;
            case 4:
                opcionBuscarPorCodigoIsbn();
                break;
            case 5:
                opcionBuscarPorTitulo();
                break;
            case 6:
                opcionBuscarPorGenero();
                break;
            case 7:
                opcionBuscarUsuarioConLibro();
                break;
            case 8:
                opcionAgregarLibro();
                break;
            case 9:
                opcionAgregarUsuario();
                break;
            case 10:
                opcionEliminarLibro();
                break;
            case 11:
                opcionEliminarUsuario();
                break;
            case 12:
                gestor.mostrarResumenLibros();
                break;
            case 13:
                gestor.mostrarResumenUsuarios();
                break;
            case 14:
                gestor.mostrarResumenBiblioteca();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion no valida. Elige entre 0 y 14.");
                break;
        }
    }

    private static void opcionPrestarLibro() {
        System.out.println("PRESTAR LIBRO");
        String identificadorUsuario = leerTexto("Identificador del usuario: ");
        String codigoIsbn = leerTexto("Codigo ISBN del libro: ");
        gestor.prestarLibro(identificadorUsuario, codigoIsbn);
    }

    private static void opcionDevolverLibro() {
        System.out.println("DEVOLVER LIBRO");
        String identificadorUsuario = leerTexto("Identificador del usuario: ");
        String codigoIsbn = leerTexto("Codigo ISBN del libro: ");
        gestor.devolverLibro(identificadorUsuario, codigoIsbn);
    }

    private static void opcionReservarLibro() {
        System.out.println("RESERVAR LIBRO");
        String identificadorUsuario = leerTexto("Identificador del usuario: ");
        String codigoIsbn = leerTexto("Codigo ISBN del libro: ");
        gestor.reservarLibro(identificadorUsuario, codigoIsbn);
    }

    private static void opcionBuscarPorCodigoIsbn() {
        System.out.println("BUSCAR POR CODIGO ISBN");
        String codigoIsbn = leerTexto("Codigo ISBN del libro: ");
        Libro libro = gestor.buscarLibroPorIsbn(codigoIsbn);
        if (libro == null) {
            System.out.println("No se encontro ningun libro con ese codigo ISBN.");
        } else {
            System.out.println("Codigo ISBN:" + libro.getIsbn());
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Estado: " + libro.getEstado());
            System.out.println("Copias: " + libro.getCopiasDisponibles() + "/" + libro.getTotalCopias());
        }
    }

    private static void opcionBuscarPorTitulo() {
        System.out.println("BUSCAR POR TITULO");
        String titulo = leerTexto("Titulo: ");
        ArrayList<Libro> resultado = gestor.buscarLibrosPorTitulo(titulo);
        if (resultado.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo.");
        } else {
            System.out.println("Resultados: " + resultado.size());
            for (int indice = 0; indice < resultado.size(); indice++) {
                Libro libroEncontrado = resultado.get(indice);
                System.out.println("  - [" + libroEncontrado.getIsbn() + "] " + libroEncontrado.getTitulo()
                        + " - " + libroEncontrado.getAutor() + " (" + libroEncontrado.getEstado() + ")");
            }
        }
    }

    private static void opcionBuscarPorGenero() {
        System.out.println("BUSCAR POR GENERO");
        System.out.println("Generos disponibles:");
        Genero[] generosDisponibles = Genero.values();
        for (int indice = 0; indice < generosDisponibles.length; indice++) {
            System.out.println("  " + indice + ". " + generosDisponibles[indice].name());
        }
        int indiceGenero = leerEntero("Numero de genero: ");
        if (indiceGenero < 0 || indiceGenero >= generosDisponibles.length) {
            System.out.println("Genero no valido.");
            return;
        }
        Genero generoSeleccionado = generosDisponibles[indiceGenero];
        ArrayList<Libro> resultado = gestor.buscarLibrosPorGenero(generoSeleccionado);
        if (resultado.isEmpty()) {
            System.out.println("No hay libros de ese genero.");
        } else {
            System.out.println("Libros de " + generoSeleccionado + ":");
            for (int indice = 0; indice < resultado.size(); indice++) {
                Libro libroEncontrado = resultado.get(indice);
                System.out.println("  - [" + libroEncontrado.getIsbn() + "] " + libroEncontrado.getTitulo() + " ("
                        + libroEncontrado.getEstado() + ")");
            }
        }
    }

    private static void opcionBuscarUsuarioConLibro() {
        System.out.println("VER QUIEN TIENE UN LIBRO");
        String codigoIsbn = leerTexto("Codigo ISBN del libro: ");
        gestor.buscarUsuarioConLibro(codigoIsbn);
    }

    private static void opcionAgregarLibro() {
        System.out.println("AGREGAR LIBRO");
        String codigoIsbn = leerTexto("Codigo ISBN:  ");
        String titulo = leerTexto("Titulo: ");
        String autor = leerTexto("Autor: ");
        int aniodePublicacion = leerEntero("Anio de publicacion: ");
        String editorial = leerTexto("Editorial: ");

        System.out.println("Generos disponibles:");
        Genero[] generosDisponibles = Genero.values();
        for (int indice = 0; indice < generosDisponibles.length; indice++) {
            System.out.println("  " + indice + ". " + generosDisponibles[indice].name());
        }
        int indiceGenero = leerEntero("Numero de genero: ");
        if (indiceGenero < 0 || indiceGenero >= generosDisponibles.length) {
            System.out.println("Genero no valido. Libro no anadido. ");
            return;
        }
        Genero generoSeleccionado = generosDisponibles[indiceGenero];
        int totalCopias = leerEntero("Total de copias: ");

        Libro nuevoLibro = new Libro(codigoIsbn, titulo, autor, aniodePublicacion, editorial, generoSeleccionado,totalCopias);
        gestor.agregarLibro(nuevoLibro);
    }

    private static void opcionAgregarUsuario() {
        System.out.println("AGREGAR USUARIO");
        String identificadorUsuario = leerTexto("Identificador del usuario: ");
        String nombre = leerTexto("Nombre: ");
        gestor.agregarUsuario(new Usuario(identificadorUsuario, nombre));
    }

    private static void opcionEliminarLibro() {
        System.out.println("ELIMINAR LIBRO");
        String codigoIsbn = leerTexto("Codigo ISBN del libro a eliminar: ");
        gestor.eliminarLibro(codigoIsbn);
    }

    private static void opcionEliminarUsuario() {
        System.out.println("ELIMINAR USUARIO");
        String identificadorUsuario = leerTexto("Identificador del usuario a eliminar: ");
        gestor.eliminarUsuario(identificadorUsuario);
    }

    private static void cargarDatosDePrueba() {
        System.out.println("\n");
        gestor.agregarLibro(new Libro("ISBN001", "El Quijote", "Cervantes", 1605, "Anaya", Genero.NOVELA, 3));
        gestor.agregarLibro(new Libro("ISBN002", "Dune", "Frank Herbert", 1965, "Salvat", Genero.CIENCIA_FICCION, 2));
        gestor.agregarLibro(new Libro("ISBN003", "Harry Potter", "J.K. Rowling", 1997, "Salamandra", Genero.FANTASIA, 4));
        gestor.agregarLibro(new Libro("ISBN004", "El nombre del viento", "Patrick Rothfuss", 2007, "Plaza", Genero.FANTASIA, 1));

        gestor.agregarUsuario(new Usuario("U001", "Ivan Garcia"));
        gestor.agregarUsuario(new Usuario("U002", "Izan Lopez"));
        gestor.agregarUsuario(new Usuario("U003", "Carlos Ruiz"));

        System.out.println("Datos de prueba");
        System.out.println("Libros:   ISBN001, ISBN002, ISBN003, ISBN004");
        System.out.println("Usuarios: U001 (Ivan), U002 (Izan), U003 (Carlos)");
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un numero entero valido.");
            }
        }
    }
}