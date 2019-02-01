package helpers.cristian.com.ubiety.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_DB = "ubietybd";
    private static final int VERSION_DB = 1;

    private static DBHelper instancia;

    private DBHelper(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    public static synchronized DBHelper getInstancia(Context contexto) {
        if(instancia == null)
            instancia = new DBHelper(contexto.getApplicationContext());

        return instancia;
    }


    // [ INICIO ] CONSTANTES PARA EL MANEJO DE LAS TABLAS
    public static final String TABLA_NOTIFICACIONES = "notificaciones";
    public static final String TABLA_ZONAS = "zonas";
    public static final String TABLA_BLOQUES = "bloques";
    public static final String TABLA_POSICIONES = "posiciones";
    public static final String TABLA_IMAGENES = "imagenes";
    public static final String TABLA_IMAGEN_BLOQUE = "imagen_bloque";
    public static final String TABLA_FACULTADES = "facultades";
    public static final String TABLA_CARRERAS = "carreras";
    public static final String TABLA_NOTICIAS = "noticias";
    public static final String TABLA_NOTICIAS_CARRERA = "noticia_carrera";

    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRIPCION = "descripcion";
    public static final String TIPO = "tipo";
    public static final String FECHA = "fecha";
    public static final String HORA = "hora";
    public static final String NOMBRE = "nombre";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String URL = "url";
    public static final String FECHA_TOMADA = "fecha_tomada";
    public static final String CODIGO = "codigo";
    public static final String URL_IMAGEN = "url_imagen";
    public static final String ENLACE = "enlace";
    public static final String ID_BLOQUE = "id_bloque";
    public static final String ID_IMAGEN = "id_imagen";
    public static final String ID_ZONA = "id_zona";
    public static final String ID_POSICION = "id_posicion";
    public static final String ID_NOTICIA = "id_noticia";
    public static final String ID_CARRERA = "id_carrera";
    public static final String ID_FACULTAD = "id_facultad";
    // [ FIN ] CONSTANTES PARA EL MANEJO DE LAS TABLAS

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
         * Estos datos se utilizaran para los tabs principales, para que haya un información
         * guardad en el dispositivo y así los usuarios no tenga que esperar a que cargue cada vez
         * que elijen un tab, la información mostrada de ahí en adelante, como lo detalles de los bloques,
         * de las facultades, etc.. serán cargadas al servidor
         */

        String CREAR_TABLA_FACULTADES = "CREATE TABLE "+TABLA_FACULTADES+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT NOT NULL " +
                ");";

        String CREAR_TABLA_CARRERAS = "CREATE TABLE "+TABLA_CARRERAS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NOMBRE + " TEXT NOT NULL, " +
                ID_FACULTAD + " INTEGER NOT NULL, " +
                "FOREIGN KEY("+ID_FACULTAD+") REFERENCES "+TABLA_FACULTADES+"("+ID+") " +
                ");";

        String CREAR_TABLA_NOTICIAS = "CREATE TABLE "+TABLA_NOTICIAS+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                URL_IMAGEN + " TEXT, " +
                TITULO + " TEXT, " +
                DESCRIPCION + " TEXT, " +
                FECHA + " TEXT, " +
                ENLACE + " TEXT, " +
                TIPO + " TEXT " + // Tipos: Banner ó Noticia (Banner es solo una imagen)
                ");";

        String CREAR_TABLA_NOTICIAS_CARRERA = "CREATE TABLE "+TABLA_NOTICIAS_CARRERA+" ( " +
                ID_NOTICIA + " INTEGER NOT NULL, " +
                ID_CARRERA + " INTEGER NOT NULL, " +
                "FOREIGN KEY("+ID_NOTICIA+") REFERENCES "+TABLA_NOTICIAS+"("+ID+"), " +
                "FOREIGN KEY("+ID_CARRERA+") REFERENCES "+TABLA_CARRERAS+"("+ID+") " +
                ");";

        String CREAR_TABLA_NOTIFICACIONES = "CREATE TABLE "+TABLA_NOTIFICACIONES+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                TITULO + " TEXT NOT NULL, " +
                DESCRIPCION + " TEXT NOT NULL, " +
                TIPO + " INTEGER NOT NULL, " +
                FECHA + " TEXT NOT NULL, " +
                HORA + " TEXT NOT NULL " +
                ");";

        String CREAR_TABLA_BLOQUE = "CREATE TABLE "+TABLA_BLOQUES+" ( " +
                ID + " INTEGER PRIMARY KEY," +
                NOMBRE + " TEXT NOT NULL, " +
                CODIGO + " TEXT NOT NULL, " +
                ID_ZONA + " INTEGER NOT NULL, " +
                ID_POSICION + " INTEGER NOT NULL, " +
                "FOREIGN KEY("+ID_ZONA+") REFERENCES "+TABLA_ZONAS+"("+ID+"), " +
                "FOREIGN KEY("+ID_POSICION+") REFERENCES "+TABLA_POSICIONES+"("+ID+") " +
                ");";

        String CREAR_TABLA_ZONA = "CREATE TABLE "+TABLA_ZONAS+" ( " +
                ID + " INTEGER PRIMARY KEY," +
                NOMBRE + " TEXT NOT NULL " +
                ");";

        String CREAR_TABLA_POSICIONES = "CREATE TABLE "+TABLA_POSICIONES+" ( " +
                ID + " INTEGER PRIMARY KEY," +
                LATITUD + " DOUBLE NOT NULL, " +
                LONGITUD + " DOUBLE NOT NULL " +
                ");";

        String CREAR_TABLA_IMAGENES = "CREATE TABLE "+TABLA_IMAGENES+" ( " +
                ID + " INTEGER PRIMARY KEY," +
                URL + " TEXT NOT NULL, " +
                FECHA_TOMADA + " TEXT NOT NULL, " +
                "FOREIGN KEY("+ID_POSICION+") REFERENCES "+TABLA_POSICIONES+"("+ID+") " +
                ");";

        String CREAR_TABLA_IMAGEN_BLOQUE = "CREATE TABLE "+TABLA_IMAGEN_BLOQUE+" ( " +
                ID_IMAGEN + " INTEGER NOT NULL, " +
                ID_BLOQUE + " INTEGER NOT NULL, " +
                "FOREIGN KEY("+ID_IMAGEN+") REFERENCES "+TABLA_IMAGENES+"("+ID+"), " +
                "FOREIGN KEY("+ID_BLOQUE+") REFERENCES "+TABLA_BLOQUES+"("+ID+") " +
                ");";

        db.execSQL(CREAR_TABLA_FACULTADES);
        db.execSQL(CREAR_TABLA_NOTIFICACIONES);
        db.execSQL(CREAR_TABLA_BLOQUE);
        db.execSQL(CREAR_TABLA_ZONA);
        db.execSQL(CREAR_TABLA_POSICIONES);
        db.execSQL(CREAR_TABLA_IMAGENES);
        db.execSQL(CREAR_TABLA_IMAGEN_BLOQUE);
        db.execSQL(CREAR_TABLA_CARRERAS);
        db.execSQL(CREAR_TABLA_NOTICIAS);
        db.execSQL(CREAR_TABLA_NOTICIAS_CARRERA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
