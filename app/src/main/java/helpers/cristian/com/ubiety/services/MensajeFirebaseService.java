package helpers.cristian.com.ubiety.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.modelos.Alerta;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.utilidades.Constantes;


public class MensajeFirebaseService extends FirebaseMessagingService {

    private final String TAG = "MensajeFCM";
    private Gson gson;
    private DBManager dbManager;
    private NotificationManager notificationManager;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        gson = new Gson();

        Log.v(TAG, "Mensaje resivido");

        Map<String, String> data = remoteMessage.getData();

        atenderMsj(data);
    }

    private void atenderMsj(Map<String, String> data) {
        String accion = data.get("accion");

        if ( accion == null ) return;

        dbManager = new DBManager(this);

        switch (accion) {
            case "notificacion":

                Notificacion notificacion = gson.fromJson( data.get("cuerpo"), Notificacion.class);

                Log.v(TAG, "Notificacion "+ gson.toJson(notificacion));

                dbManager.insertarNotificacion(notificacion);

                Alerta alertaNotificacion = new Alerta(notificacion.getId(), Alerta.Motivos.NOTIFICACION);
                alertaNotificacion.setNotificacion(notificacion);

                LocalBroadcastManager.getInstance(this).sendBroadcast(
                        new Intent(Constantes.Acciones.AGREGAR_ALERTA)
                        .putExtra(Alerta.class.getSimpleName(), alertaNotificacion)
                );

                lanzarNotifiacion(notificacion.getTitulo(), notificacion.getDescripcion(), notificacion.getTipo());

                break;
            case "noticia":

                Noticia noticia = gson.fromJson( data.get("cuerpo"), Noticia.class);

                dbManager.insertarModelo(noticia);

                lanzarNotifiacion(noticia.getTitulo(), noticia.getDescripcion(), 1);

                Alerta alertaNoticia;

                if ( noticia.getTipo() == Noticia.Tipos.NOTICIA ) {
                    alertaNoticia = new Alerta(noticia.getId(),  Alerta.Motivos.NOTICIA);
                    alertaNoticia.setNoticia(noticia);
                } else {
                    alertaNoticia = new Alerta(noticia.getId(),  Alerta.Motivos.BANNER);
                    alertaNoticia.setBanner(noticia);
                }

                LocalBroadcastManager.getInstance(this).sendBroadcast(
                        new Intent(Constantes.Acciones.AGREGAR_NOTICIA)
                );

                LocalBroadcastManager.getInstance(this).sendBroadcast(
                        new Intent(Constantes.Acciones.AGREGAR_ALERTA)
                                .putExtra(Alerta.class.getSimpleName(), alertaNoticia)
                );

                break;
        }
    }

    public void lanzarNotifiacion(String titulo, String descripcion, int tipo){
        Uri sonidoUri = Uri.parse("android.resource://helpers.cristian.com.notiapp/raw/noti_" + tipo);

        NotificationCompat.Builder builderNotificacion = new NotificationCompat.Builder(getApplicationContext(), "micanal")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle( titulo )
                .setContentText( descripcion )
                .setVibrate(new long[]{100, 200, 300})
                .setAutoCancel(true)
                .setSound(sonidoUri);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notiapp",
                        "NotificacionesNotiApp",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(4234, builderNotificacion.build());
        }
    }

}
