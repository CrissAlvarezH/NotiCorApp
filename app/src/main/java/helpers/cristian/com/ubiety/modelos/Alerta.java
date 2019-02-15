package helpers.cristian.com.ubiety.modelos;

import java.io.Serializable;

public class Alerta implements Serializable {
    private int id;
    private int motivo;
    private Notificacion notificacion;
    private Noticia noticia;
    private Noticia banner;

    public Alerta(int id, int motivo) {
        this.id = id;
        this.motivo = motivo;
    }

    public interface Motivos {
        int NOTIFICACION = 1;
        int NOTICIA = 2;
        int BANNER = 3;
    }

    public Noticia getBanner() {
        return banner;
    }

    public void setBanner(Noticia banner) {
        this.banner = banner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMotivo() {
        return motivo;
    }

    public void setMotivo(int motivo) {
        this.motivo = motivo;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }
}
