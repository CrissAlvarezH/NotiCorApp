package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import java.io.Serializable;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.LATITUD;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.LONGITUD;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_POSICIONES;

public class Posicion implements Serializable, ModeloBaseDatos {
    private int id;
    private double latitud;
    private double longitud;

    public Posicion(int id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(LATITUD, latitud);
        values.put(LONGITUD, longitud);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_POSICIONES;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


}
