# UT4--DISE-ANDO-UN-SISTEMA-BIBLIOTECA-MVC

## Objetivo
- Este sistema permite gestionar de forma integral una biblioteca digital mediante una interfaz de consola interactiva. La aplicación implementa el patrón MVC.

## Caracteristicas: 

- Libros: añadir, eliminar y buscar por ISBN, título o género.
- Usuarios: registrar y eliminar usuarios con seguimiento de préstamos activos e historial.
- Préstamos: prestar, devolver y reservar libros con validaciones automáticas:
- Máximo 3 préstamos activos por usuario.
- Bloqueo de 7 días si se devuelve un libro con más de 30 días de préstamo.
- Vencimiento automático a los 30 días.
- Excepciones personalizadas para controlar errores de negocio.
- Enums para estados del libro (DISPONIBLE, PRESTADO, RESERVADO) y géneros literarios.
 
## Cómo ejecutar el programa

- Compila el proyecto desde la raíz main.java

## REPARTO DE TAREAS RESUMIDO:

 ## IVÁN : 

MODELO : genero, estadolibro y libro

EXCECIONES : LibroNoDisponibleException , LimitePrestamosExcedidoException y BloqueoPrestamoException

VISTA : Main y la consola hace la parte de : monstrarMensaje, mostrarError, mostrarLibro y mostrarListaLibros

CONTROLADOR : Gestor Biblioteca hace la parte agregarLibro, eliminarLibro , agregarUsuario , eliminarUsuario, prestarLibro , reservarLibro, buscarLibroPorIsbn , buscarLibrosPorTitulo y buscarLibrosPorGenero.


 ## IZAN :

MODELO : Prestamo y Usuario

VISTA : Consola: mostrarUsuario, mostrarListaUsusarios, mostrarPrestamo, mostrarListaPrestamos, mostrarResumenBiblioteca y mostrarLibrosUsuario

CONTROLADOR : GestorBiblioteca;
Metodos auxiliares del prestamo: validarExistenciaUsuario, validarExistenciaLibro, validarLimitePrestamosUsuario, validarDisponibilidadLibro, validarBloqueoPrestamoAnterior, crearPrestamoNuevo y actualizarEstadoLibroAlPrestar
Devolucion de libros: devolverLibro, buscarPrestamoActivoEnUsuario y procesarDevolucionDelLibro
buscarUsuarioPorId, buscarUsuarioConLibro, mostrarResumenLibros, mostrarResumenUsuarios y mostrarResumenBiblioteca

