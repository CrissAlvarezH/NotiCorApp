package helpers.cristian.com.ubiety.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Notificacion;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.DESCRIPCION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.FECHA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.HORA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
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
}
