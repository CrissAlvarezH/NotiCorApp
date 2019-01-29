package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.adapter.NotificacionAdapter;
import helpers.cristian.com.ubiety.modelos.Notificacion;


public class NotificacionesFragment extends Fragment implements NotificacionAdapter.ListenerNotificacion {

    private RecyclerView recyclerNotis;
    private NotificacionAdapter notiAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        recyclerNotis = vista.findViewById(R.id.recycler_notis);

        RecyclerView.LayoutManager lmNotis = new LinearLayoutManager(getContext());
        recyclerNotis.setLayoutManager(lmNotis);

        ArrayList<Notificacion> notificaciones = new ArrayList<>();
        notificaciones.add( new Notificacion(1, Notificacion.Tipos.ADVERTENCIA, "Ajá, advertencia", "Esta es una advertencia, pila pues", "2019-02-03", "01:06" ) );
        notificaciones.add( new Notificacion(1, Notificacion.Tipos.ADVERTENCIA, "Ajá, advertencia", "Esta es una advertencia, pila pues", "2019-02-03", "03:16" ) );
        notificaciones.add( new Notificacion(1, Notificacion.Tipos.NOTICIA, "Ajá, Noticias", "Esta es una noticia, pa' informarce", "2019-02-03", "01:06" ) );
        notificaciones.add( new Notificacion(1, Notificacion.Tipos.ADVERTENCIA, "Ajá, advertencia", "Esta es una advertencia, pila pues", "2019-02-03", "07:32" ) );
        notificaciones.add( new Notificacion(1, Notificacion.Tipos.PELIGRO, "Peligro pues", "¡Peligro en la U!", "2019-02-03", "06:00" ) );

        notiAdapter = new NotificacionAdapter(notificaciones, this);
        recyclerNotis.setAdapter(notiAdapter);

        return vista;
    }


    @Override
    public void clickNotificacion(Notificacion notificacion, int posicion) {

    }
}
