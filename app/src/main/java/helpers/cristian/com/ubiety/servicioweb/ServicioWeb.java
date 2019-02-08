package helpers.cristian.com.ubiety.servicioweb;

import helpers.cristian.com.ubiety.utilidades.Constantes;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServicioWeb {

    @GET(Constantes.URLs.GET_INFO_INICIAL)
    Call<ResServer> getInfoInicial();

    @GET(Constantes.URLs.GET_NOTICIA_CARRERA)
    Call<ResServer> getNoticiaDeCarrera(@Path("id_carrera") int id);
}
