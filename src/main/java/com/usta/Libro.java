package com.usta;
public class Libro {
    private String nombre;
    private String autor;
    private String genero;
    private String isbn;
    
    public Libro() {
    }
    public Libro(String nombre, String autor, String genero, String isbn) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.isbn = isbn;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    

    
    
    
}
