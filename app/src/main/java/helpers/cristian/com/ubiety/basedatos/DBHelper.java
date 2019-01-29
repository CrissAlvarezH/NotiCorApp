package helpers.cristian.com.ubiety.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_DB = "ubietybd";
    private static final int VERSION_DB = 1;

    public DBHelper(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    // [ INICIO ] CONSTANTES PARA EL MANEJO DE LAS TABLAS
    public static final String TABLA_NOTIFICACIONES = "notificaciones";
    public static final String TABLA_ZONAS = "zonas";
    public static final String TABLA_BLOQUES = "bloques";
    public static final String TABLA_POSICIONES = "posiciones";
    public static final String TABLA_IMAGENES = "imagenes";
    public static final String TABLA_IMAGEN_BLOQUE = "imagen_bloque";

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
    public static final String ID_BLOQUE = "id_bloque";
    public static final String ID_IMAGEN = "id_imagen";
    public static final String CODIGO = "codigo";
    public static final String ID_ZONA = "id_zona";
    public static final String ID_POSICION = "id_posicion";
    // [ FIN ] CONSTANTES PARA EL MANEJO DE LAS TABLAS

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREAR_TABLA_NOTIFICACIONES = "CREATE TABLE "+TABLA_NOTIFICACIONES+" ( " +
                ID + " INTEGER PRIMARY KEY, " +
                TITULO + " TEXT NOT NULL, " +
                DESCRIPCION + " TEXT NOT NULL, " +
                TIPO + " INTEGER NOT NULL, " +
                FECHA + " TEXT NOT NULL, " +
                HORA + " TEXT NOT NULL " +
                ");";

        String CREAR_TABLA_BLOQUE = "CREATE TABLE "+TABLA_BLOQUES+" ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOMBRE + " TEXT NOT NULL, " +
                CODIGO + " TEXT NOT NULL, " +
                ID_ZONA + " INTEGER NOT NULL, " +
                ID_POSICION + " INTEGER NOT NULL, " +
                "FOREIGN KEY("+ID_ZONA+") REFERENCES "+TABLA_ZONAS+"("+ID+"), " +
                "FOREIGN KEY("+ID_POSICION+") REFERENCES "+TABLA_POSICIONES+"("+ID+") " +
                ");";

        String CREAR_TABLA_ZONA = "CREATE TABLE "+TABLA_ZONAS+" ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOMBRE + " TEXT NOT NULL " +
                ");";

        String CREAR_TABLA_POSICIONES = "CREATE TABLE "+TABLA_POSICIONES+" ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LATITUD + " DOUBLE NOT NULL, " +
                LONGITUD + " DOUBLE NOT NULL " +
                ");";

        String CREAR_TABLA_IMAGENES = "CREATE TABLE "+TABLA_IMAGENES+" ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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

        db.execSQL(CREAR_TABLA_NOTIFICACIONES);
        db.execSQL(CREAR_TABLA_BLOQUE);
        db.execSQL(CREAR_TABLA_ZONA);
        db.execSQL(CREAR_TABLA_POSICIONES);
        db.execSQL(CREAR_TABLA_IMAGENES);
        db.execSQL(CREAR_TABLA_IMAGEN_BLOQUE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
