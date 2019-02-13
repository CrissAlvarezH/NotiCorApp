package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_ZONAS;

public class Zona implements Serializable, ModeloBaseDatos {
    private int id;
    private String nombre;

    public Zona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(NOMBRES, nombre);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_ZONAS;
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
