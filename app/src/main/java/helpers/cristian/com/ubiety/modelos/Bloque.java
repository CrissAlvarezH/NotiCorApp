package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.CODIGO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_POSICION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_ZONA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_BLOQUES;

public class Bloque implements Serializable, ModeloBaseDatos {
    public static String CLASS_NAME = "Bloque";

    private int id;
    private String nombre, codigo;
    private int cantSalones;
    private ArrayList<String> urlImagenes;
    private Zona zona;
    private Posicion posicion;

    public Bloque(int id, String nombre, String codigo, int cantSalones, ArrayList<String> urlImagenes) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantSalones = cantSalones;
        this.urlImagenes = urlImagenes;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(NOMBRE, nombre);
        values.put(CODIGO, codigo);
        values.put(ID_ZONA, zona.getId());
        values.put(ID_POSICION, posicion.getId());

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_BLOQUES;
    }

    public int getCantSalones() {
        return cantSalones;
    }

    public void setCantSalones(int cantSalones) {
        this.cantSalones = cantSalones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<String> getUrlImagenes() {
        return urlImagenes;
    }

    public void setUrlImagenes(ArrayList<String> urlImagenes) {
        this.urlImagenes = urlImagenes;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }
}
