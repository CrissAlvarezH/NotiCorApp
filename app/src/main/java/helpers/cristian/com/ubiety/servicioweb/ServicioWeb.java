package helpers.cristian.com.ubiety.servicioweb;

import helpers.cristian.com.ubiety.utilidades.Constantes;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServicioWeb {

    @FormUrlEncoded
    @POST(Constantes.URLs.ACTUALIZAR_TOKEN)
    Call<ResServer> actualizarToken(@Path("usuario") String usuario,
                                    @Path("token") String token);

}
