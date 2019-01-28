package helpers.cristian.com.ubiety.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Bloque implements Serializable {
    public static String CLASS_NAME = "Bloque";

    private int id;
    private String nombre, codigo, urlImg;
    private int cantSalones;
    private ArrayList<String> urlImagenes;
    private Posicion posicion;

    public Bloque(int id, String nombre, String codigo, int cantSalones, ArrayList<String> urlImagenes) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantSalones = cantSalones;
        this.urlImagenes = urlImagenes;
    }

    public int getCantSalones() {
        return cantSalones;
    }

    public void setCantSalones(int cantSalones) {
        this.cantSalones = cantSalones;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<String> getUrlImagenes() {
        return urlImagenes;
    }

    public void setUrlImagenes(ArrayList<String> urlImagenes) {
        this.urlImagenes = urlImagenes;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
