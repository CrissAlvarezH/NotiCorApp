package helpers.cristian.com.ubiety.modelos;

import android.content.ContentValues;

public interface ModeloBaseDatos {
    ContentValues toContentValues();
    String getNombreTabla();
}
