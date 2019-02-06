package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_FACULTAD;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_CARRERAS;

public class Carrera implements ModeloBaseDatos, Serializable {

    public static final String CLASS_NAME = "carrera";

    private int id;
    private String nombre;
    private int idFacultad;

    public Carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Carrera(int id, String nombre, int idFacultad) {
        this.id = id;
        this.nombre = nombre;
        this.idFacultad = idFacultad;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(NOMBRE, nombre);
        values.put(ID_FACULTAD, idFacultad);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_CARRERAS;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
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

}
