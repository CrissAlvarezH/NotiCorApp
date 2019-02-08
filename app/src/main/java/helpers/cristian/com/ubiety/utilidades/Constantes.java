package helpers.cristian.com.ubiety.utilidades;

public class Constantes {

    public interface URLs {
        String BASE_URL = "http://142.93.71.94/";

        String GET_INFO_INICIAL = "NotiServer/src/index.php/base/info-inicial";
        String GET_NOTICIA_CARRERA = "NotiServer/src/index.php/noticias/noti-and-banners/{id_carrera}";
        String IMAGEN_NOTICIA = "http://142.93.71.94/NotiServer/src/uploads/noticias/";
    }

    public interface Acciones {
        String ACTUALIZAR_NOTIFICACIONES = "actualizarnotificaciones";
        String AGREGAR_NOTIFICACION = "agregarnotificacion";
    }

}
