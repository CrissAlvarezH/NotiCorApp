package helpers.cristian.com.ubiety.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;
import helpers.cristian.com.ubiety.modelos.ModeloBaseDatos;
import helpers.cristian.com.ubiety.modelos.Notificacion;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.DESCRIPCION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.FECHA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.HORA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_FACULTAD;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_CARRERAS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_FACULTADES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTIFICACIONES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TIPO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TITULO;

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = DBHelper.getInstancia(context);
    }

    public long insertarNotificacion(Notificacion noti) {
        db = helper.getWritableDatabase();

        return db.insert(noti.getNombreTabla(), null, noti.toContentValues());
    }

    public ArrayList<Notificacion> getNotificaciones() {
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM "+TABLA_NOTIFICACIONES+" ORDER BY "+ID+" DESC",
                null
        );

        ArrayList<Notificacion> notificaciones = new ArrayList<>();

        if ( c.moveToFirst() ) {
            do {
                Notificacion notificacion = new Notificacion(
                        c.getInt( c.getColumnIndex(ID) ),
                        c.getInt( c.getColumnIndex(TIPO) ),
                        c.getString( c.getColumnIndex(TITULO) ),
                        c.getString( c.getColumnIndex(DESCRIPCION) ),
                        c.getString( c.getColumnIndex(FECHA) ),
                        c.getString( c.getColumnIndex(HORA) )
                );

                notificaciones.add(notificacion);

            } while ( c.moveToNext() );
        }

        c.close();

        return notificaciones;
    }

    public void insertarModelo(ModeloBaseDatos modelo) {
        db = helper.getWritableDatabase();

        db.insert(modelo.getNombreTabla(), null, modelo.toContentValues());
    }

    public void insertarModelos(ArrayList<ModeloBaseDatos> modelos) {
        db = helper.getWritableDatabase();

        for (ModeloBaseDatos modelo : modelos) {
            db.insert(modelo.getNombreTabla(), null, modelo.toContentValues());
        }
    }

    public void limpiarBD() {
        db = helper.getWritableDatabase();

        db.delete(TABLA_NOTIFICACIONES, null, null);
        db.delete(TABLA_CARRERAS, null, null);
        db.delete(TABLA_FACULTADES, null, null);
    }

    public void insertarNotificaciones(ArrayList<Notificacion> notificaciones) {
        for (Notificacion notificacion: notificaciones) {
            insertarModelo(notificacion);
        }
    }

    public void insertarFacultades(ArrayList<Facultad> facultades) {
        for (Facultad facultad : facultades) {
            insertarModelo(facultad);
        }
    }

    public void insertarCarreras(ArrayList<Carrera> carreras) {
        for (Carrera carrera : carreras) {
            insertarModelo(carrera);
        }
    }

    public ArrayList<Facultad> getFacultades() {
        db = helper.getReadableDatabase();

        ArrayList<Facultad> facultades = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT * FROM "+TABLA_FACULTADES,
                null
        );

        if ( c.moveToFirst() ) {
            do {
                Facultad facultad = new Facultad(
                        c.getInt( c.getColumnIndex(ID) ),
                        c.getString( c.getColumnIndex(NOMBRE) ),
                        new ArrayList<Carrera>(),
                        false
                );

                ArrayList<Carrera> carreras = new ArrayList<>();

                Cursor cCar = db.rawQuery(
                        "SELECT * FROM "+TABLA_CARRERAS+" WHERE "+ID_FACULTAD+" = ?",
                        new String[] { facultad.getId()+"" }
                );

                if ( cCar.moveToFirst() ) {
                    do {
                        Carrera carrera = new Carrera(
                                cCar.getInt( cCar.getColumnIndex(ID) ),
                                cCar.getString( cCar.getColumnIndex(NOMBRE) ),
                                cCar.getInt( cCar.getColumnIndex(ID_FACULTAD) )
                        );

                        carreras.add(carrera);

                    } while ( cCar.moveToNext() );
                }

                cCar.close();

                facultad.setCarreras(carreras);

                facultades.add(facultad);

            } while ( c.moveToNext() );
        }

        c.close();

        return facultades;
    }
}
