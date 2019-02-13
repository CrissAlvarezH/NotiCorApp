package helpers.cristian.com.ubiety.servicioweb;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.modelos.Profesor;

public class ResServer {
    private boolean okay;
    private String mensaje;
    private ArrayList<Facultad> facultades;
    private ArrayList<Notificacion> notificaciones;
    private ArrayList<Carrera> carreras;
    private ArrayList<Noticia> noticias;
    private ArrayList<Noticia> banners;
    private Profesor profesor;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ArrayList<Noticia> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Noticia> banners) {
        this.banners = banners;
    }

    public ArrayList<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<Carrera> carreras) {
        this.carreras = carreras;
    }

    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(ArrayList<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public ArrayList<Facultad> getFacultades() {
        return facultades;
    }

    public void setFacultades(ArrayList<Facultad> facultades) {
        this.facultades = facultades;
    }

    public boolean isOkay() {
        return okay;
    }

    public void setOkay(boolean okay) {
        this.okay = okay;
    }
}
