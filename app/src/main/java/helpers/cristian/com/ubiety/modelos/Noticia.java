package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.DESCRIPCION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ENLACE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.FECHA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_CARRERA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTICIAS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TIPO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TITULO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.URL_IMAGEN;


public class Noticia implements Serializable, ModeloBaseDatos {
    private int id;
    private String urlImagen;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String enlace;
    private int tipo;
    private int idCarrera;

    public Noticia(int id, String urlImagen, String titulo, String descripcion,
                   String fecha, String enlace, int tipo, int idCarrera) {
        this.id = id;
        this.urlImagen = urlImagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.enlace = enlace;
        this.tipo = tipo;
        this.idCarrera = idCarrera;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(URL_IMAGEN, id);
        values.put(TITULO, titulo);
        values.put(DESCRIPCION, descripcion);
        values.put(TIPO, tipo);
        values.put(FECHA, fecha);
        values.put(ENLACE, enlace);
        values.put(ID_CARRERA, idCarrera);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_NOTICIAS;
    }

    public interface Tipos {
        int NOTICIA = 1;
        int BANNER = 2;
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
