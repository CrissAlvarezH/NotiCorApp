package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.util.ArrayList;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_FACULTADES;

public class Facultad implements ModeloBaseDatos {
    private int id;
    private String nombre;
    private boolean mostrarCarreras;
    private ArrayList<Carrera> carreras;

    public Facultad(int id, String nombre, ArrayList<Carrera> carreras, boolean mostrarCarreras) {
        this.id = id;
        this.nombre = nombre;
        this.mostrarCarreras = mostrarCarreras;
        this.carreras = carreras;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(NOMBRE, nombre);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_FACULTADES;
    }

    public boolean isMostrarCarreras() {
        return mostrarCarreras;
    }

    public void setMostrarCarreras(boolean mostrarCarreras) {
        this.mostrarCarreras = mostrarCarreras;
    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<Carrera> carreras) {
        this.carreras = carreras;
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
