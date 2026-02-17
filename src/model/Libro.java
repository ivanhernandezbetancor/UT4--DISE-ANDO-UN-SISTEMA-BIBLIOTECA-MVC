package modelo;

public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private int aniodePublicacion;
    private String editorial;
    private Genero genero;
    private int totalCopias;
    private int copiasDisponibles;
    private EstadoLibro estado;

    public Libro(String isbn, String titulo, String autor, int aniodePublicacion,String editorial, Genero genero, int totalCopias) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.aniodePublicacion = aniodePublicacion;
        this.editorial = editorial;
        this.genero = genero;
        this.totalCopias = totalCopias;
        this.copiasDisponibles = totalCopias;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getaniodePublicacion() {
        return aniodePublicacion;
    }

    public void saniodePublicacion(int aniodePublicacion) {
        this.aniodePublicacion = aniodePublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getTotalCopias() {
        return totalCopias;
    }

    public void setTotalCopias(int totalCopias) {
        this.totalCopias = totalCopias;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }
}
