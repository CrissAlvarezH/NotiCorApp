package helpers.cristian.com.ubiety.servicioweb;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;
import helpers.cristian.com.ubiety.modelos.Notificacion;

public class ResServer {
    private boolean okay;
    private ArrayList<Facultad> facultades;
    private ArrayList<Notificacion> notificaciones;
    private ArrayList<Carrera> carreras;

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
