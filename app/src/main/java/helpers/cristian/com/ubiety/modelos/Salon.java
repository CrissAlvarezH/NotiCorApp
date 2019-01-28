package helpers.cristian.com.ubiety.modelos;

import java.io.Serializable;

public class Salon implements Serializable {
    public static final String CLASS_NAME = "salon";

    private int id;
    private int piso;
    private String codigo;
    private String nombre;
    private String urlImg;

    public Salon(int id, int piso, String codigo, String nombre, String urlImg) {
        this.id = id;
        this.piso = piso;
        this.codigo = codigo;
        this.nombre = nombre;
        this.urlImg = urlImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
