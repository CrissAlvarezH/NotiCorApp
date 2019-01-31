package helpers.cristian.com.ubiety.servicioweb;

import android.support.annotation.NonNull;
import android.util.Log;

import helpers.cristian.com.ubiety.utilidades.Constantes;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioWebUtils {

    public static ServicioWeb getServicioWeb(boolean conIterceptor) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        if (conIterceptor) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    Log.v("LogginServicioWeb", message);
                }
            });

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URLs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ServicioWeb.class);
    }

}
