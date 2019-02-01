package helpers.cristian.com.ubiety.servicioweb;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.modelos.Facultad;

public class ResServer {
    private String okay;
    private ArrayList<Facultad> facultades;

    public ArrayList<Facultad> getFacultades() {
        return facultades;
    }

    public void setFacultades(ArrayList<Facultad> facultades) {
        this.facultades = facultades;
    }

    public String getOkay() {
        return okay;
    }

    public void setOkay(String okay) {
        this.okay = okay;
    }
}
