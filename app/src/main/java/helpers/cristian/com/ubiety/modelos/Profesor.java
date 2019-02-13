package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.APELLIDOS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.NOMBRES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.PASS;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ROL;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_LOGIN;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.USUARIO;

public class Profesor extends Usuario implements ModeloBaseDatos {
    private int idCarrera;

    public Profesor(int id, String usuario, String pass, String nombres, String apellidos, String rol) {
        super(id, usuario, pass, nombres, apellidos, rol);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID, id);
        values.put(USUARIO, usuario);
        values.put(PASS, pass);
        values.put(NOMBRES, nombres);
        values.put(APELLIDOS, apellidos);
        values.put(ROL, rol);

        return values;
    }

    @Override
    public String getNombreTabla() {
        return TABLA_LOGIN;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

}
