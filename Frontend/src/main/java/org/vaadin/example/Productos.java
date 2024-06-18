package org.vaadin.example;

public class Productos {
    String nombre;
    String categoria;
    String precio;
    String ean13;

    public Productos() {
    }

    public Productos(String nombre, String categoria, String precio, String ean13) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.ean13 = ean13;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio='" + precio + '\'' +
                ", ean13='" + ean13 + '\'';
    }
}
