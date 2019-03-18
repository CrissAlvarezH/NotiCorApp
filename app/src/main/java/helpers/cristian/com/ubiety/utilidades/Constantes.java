package helpers.cristian.com.ubiety.utilidades;

public class Constantes {

    public static final String TAG_DEBUG = "TagDebug";

    public interface URLs {
        String BASE_URL = "http://142.93.71.94/";

        String GET_INFO_INICIAL = "NotiServer/src/index.php/base/info-inicial/{rol}";
        String GET_NOTICIA_CARRERA = "NotiServer/src/index.php/noticias/noti-and-banners/{id_carrera}";
        String IMAGEN_NOTICIA = BASE_URL + "NotiServer/src/uploads/noticias/";
        String LOGIN = "NotiServer/src/index.php/usuarios/login";
    }

    public interface Acciones {
        String ACTUALIZAR_NOTIFICACIONES = "actualizarnotificaciones";
        String AGREGAR_ALERTA = "agregaralerta";
        String AGREGAR_NOTICIA = "agregarnoticia";
    }

}
