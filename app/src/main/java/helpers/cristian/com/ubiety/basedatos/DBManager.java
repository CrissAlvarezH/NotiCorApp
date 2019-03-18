package helpers.cristian.com.ubiety.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Alerta;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;
import helpers.cristian.com.ubiety.modelos.ModeloBaseDatos;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.modelos.Profesor;
import helpers.cristian.com.ubiety.modelos.Usuario;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.APELLIDOS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.DESCRIPCION;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ENLACE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.FECHA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.HORA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_CARRERA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_FACULTAD;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRE;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.PASS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ROL;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_CARRERAS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_FACULTADES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_LOGIN;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTICIAS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTIFICACIONES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_USUARIO_CARRERA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TIPO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TITULO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.URL_IMAGEN;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.USUARIO;

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

    public Carrera getCarrera(int id) {
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM "+TABLA_CARRERAS+" WHERE "+ID+" = ? ",
                new String[] { id+"" }
        );

        Carrera carrera = new Carrera(0, "");

        if ( c.moveToFirst() ) {
            carrera = new Carrera(
                    c.getInt( c.getColumnIndex(ID) ),
                    c.getString( c.getColumnIndex(NOMBRE) )
            );
        }

        return carrera;
    }

    public ArrayList<Alerta> getAlertas() {
        db = helper.getReadableDatabase();

        ArrayList<Alerta> alertas = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT * FROM ( " +
                            "SELECT noti.id, noti.titulo, noti.descripcion, noti.tipo, (noti.fecha || ' ' || noti.hora) fecha, null url_imagen, null carrera, 1 tipo_alarma FROM notificaciones noti " +
                            "UNION " +
                            "SELECT n.id, n.titulo, n.descripcion, n.tipo, n.fecha, n.url_imagen, c.nombre carrera, 2 tipo_alarma FROM noticias n, carreras c WHERE n.id_carrera = c.id AND n.tipo = 1 " +
                            "UNION " +
                            "SELECT n.id, n.titulo, n.descripcion, n.tipo, n.fecha, n.url_imagen, c.nombre carrera, 3 tipo_alarma FROM noticias n, carreras c WHERE n.id_carrera = c.id AND n.tipo = 2 " +
                        ") R ORDER BY fecha DESC",
                null
        );

        if ( c.moveToFirst() ) {
            do {
                Alerta alerta = new Alerta(
                        c.getInt( c.getColumnIndex("id") ),
                        c.getInt( c.getColumnIndex("tipo_alarma") )
                );

                switch ( alerta.getMotivo() ) {
                    case Alerta.Motivos.NOTIFICACION:
                        String[] fechaHora = c.getString( c.getColumnIndex("fecha") ).split(" ");

                        Notificacion notificacion = new Notificacion(
                                c.getInt( c.getColumnIndex("id") ),
                                c.getInt( c.getColumnIndex("tipo") ),
                                c.getString( c.getColumnIndex("titulo") ),
                                c.getString( c.getColumnIndex("descripcion") ),
                                fechaHora[0],
                                fechaHora[1]
                        );

                        alerta.setNotificacion(notificacion);

                        break;
                    case Alerta.Motivos.NOTICIA:
                        Noticia noticia = new Noticia();

                        noticia.setId( c.getInt( c.getColumnIndex("id") ) );
                        noticia.setTipo( c.getInt( c.getColumnIndex("tipo") ) );
                        noticia.setTitulo( c.getString( c.getColumnIndex("titulo") ) );
                        noticia.setDescripcion( c.getString( c.getColumnIndex("descripcion") ) );
                        noticia.setFecha( c.getString( c.getColumnIndex("fecha") ) );

                        Carrera carrera = new Carrera(0, c.getString( c.getColumnIndex("carrera") ));
                        noticia.setCarrera( carrera );

                        alerta.setNoticia(noticia);

                        break;
                    case Alerta.Motivos.BANNER:
                        Noticia banner = new Noticia();

                        banner.setId( c.getInt( c.getColumnIndex("id") ) );
                        Carrera carreraBanner = new Carrera(0, c.getString( c.getColumnIndex("carrera") ));
                        banner.setCarrera( carreraBanner );

                        alerta.setBanner(banner);

                        break;
                }

                alertas.add(alerta);

            } while ( c.moveToNext() );
        }

        c.close();

        return alertas;
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

    public ArrayList<Noticia> getNoticias() {
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM "+TABLA_NOTICIAS+" ORDER BY "+ID+" DESC",
                null
        );

        ArrayList<Noticia> noticias = new ArrayList<>();

        if ( c.moveToFirst() ) {
            do {
                Noticia noticia = new Noticia(
                        c.getInt( c.getColumnIndex(ID) ),
                        c.getString( c.getColumnIndex(URL_IMAGEN) ),
                        c.getString( c.getColumnIndex(TITULO) ),
                        c.getString( c.getColumnIndex(DESCRIPCION) ),
                        c.getString( c.getColumnIndex(FECHA) ),
                        c.getString( c.getColumnIndex(ENLACE) ),
                        c.getInt( c.getColumnIndex(TIPO) ),
                        c.getInt( c.getColumnIndex(ID_CARRERA) )
                );

                noticias.add(noticia);

            } while ( c.moveToNext() );
        }

        c.close();

        return noticias;
    }

    public void borrarCredenciasles() {
        db = helper.getWritableDatabase();

        db.delete(TABLA_LOGIN, null, null);
        db.delete(TABLA_USUARIO_CARRERA, null, null);
    }

    public Usuario getUsuarioLogeado() {
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM "+TABLA_LOGIN,
                null
        );

        Usuario usuario = null;

        if ( c.moveToFirst() ) {
            usuario = new Usuario(
                    c.getInt( c.getColumnIndex(ID) ),
                    c.getString( c.getColumnIndex(USUARIO) ),
                    c.getString( c.getColumnIndex(PASS) ),
                    c.getString( c.getColumnIndex(NOMBRES) ),
                    c.getString( c.getColumnIndex(APELLIDOS) ),
                    c.getString( c.getColumnIndex(ROL) )
            );
        }

        c.close();

        return usuario;
    }

    public Carrera getCarreraDeUsuario() {
        db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * " +
                        "FROM "+TABLA_USUARIO_CARRERA+", "+TABLA_CARRERAS+
                        " WHERE "+TABLA_USUARIO_CARRERA+"."+ID_CARRERA+" = "+TABLA_CARRERAS+"."+ID,
                null
        );

        Carrera carrera = null;

        if ( c.moveToFirst() ) {
            carrera = new Carrera(
                    c.getInt( c.getColumnIndex(ID) ),
                    c.getString( c.getColumnIndex(NOMBRE) )
            );
        }

        c.close();

        return carrera;
    }

    public long insertarModelo(ModeloBaseDatos modelo) {
        db = helper.getWritableDatabase();

        return db.insert(modelo.getNombreTabla(), null, modelo.toContentValues());
    }

    public long realizarInsert(String tabla, ContentValues values) {
        db = helper.getWritableDatabase();

        return db.insert(tabla, null, values);
    }

    public int limpiarTabla(String tabla) {
        db = helper.getWritableDatabase();

        return db.delete(tabla, null, null);
    }

    public void limpiarBD() {
        db = helper.getWritableDatabase();

        db.delete(TABLA_NOTIFICACIONES, null, null);
        db.delete(TABLA_CARRERAS, null, null);
        db.delete(TABLA_FACULTADES, null, null);
        db.delete(TABLA_NOTICIAS, null, null);
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

    public void insertarNoticias(ArrayList<Noticia> noticias) {
        for (Noticia noticia : noticias) {
            insertarModelo(noticia);
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
