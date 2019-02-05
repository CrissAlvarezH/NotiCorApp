package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;


public class Noticia implements Serializable {
    private int id;
    private String urlImagen;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String enlace;
    private int tipo;

    public Noticia(int id, String urlImagen, String titulo, String descripcion,
                   String fecha, String enlace, int tipo) {
        this.id = id;
        this.urlImagen = urlImagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.enlace = enlace;
        this.tipo = tipo;
    }

    public interface Tipos {
        int BANNER = 1;
        int NOTICIA = 2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
