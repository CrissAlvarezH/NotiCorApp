package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.DESCRIPCION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ENLACE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.FECHA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTICIAS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TIPO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TITULO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.URL_IMAGEN;

public class Noticia implements ModeloBaseDatos {
    private int id;
    private String urlImagen;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String enlace;
    private String tipo;

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(URL_IMAGEN, urlImagen);
        values.put(TITULO, titulo);
        values.put(DESCRIPCION, descripcion);
        values.put(FECHA, fecha);
        values.put(ENLACE, enlace);
        values.put(TIPO, tipo);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_NOTICIAS;
    }

    public interface Tipos {
        String BANNER = "Banner";
        String NOTICIA = "Noticia";
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
