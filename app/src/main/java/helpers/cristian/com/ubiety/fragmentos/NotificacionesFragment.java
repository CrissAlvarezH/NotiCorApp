package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.PerfilActivity;
import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.adapter.NotificacionAdapter;
import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.modelos.Notificacion;


public class NotificacionesFragment extends Fragment implements NotificacionAdapter.ListenerNotificacion, View.OnClickListener {

    private RecyclerView recyclerNotis;
    private NotificacionAdapter notiAdapter;
    private DBManager dbManager;
    private ImageView btnIrPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        dbManager = new DBManager(getContext());

        recyclerNotis = vista.findViewById(R.id.recycler_notis);
        btnIrPerfil = vista.findViewById(R.id.imgbtn_perfil);

        btnIrPerfil.setOnClickListener(this);

        RecyclerView.LayoutManager lmNotis = new LinearLayoutManager(getContext());
        recyclerNotis.setLayoutManager(lmNotis);


        notiAdapter = new NotificacionAdapter(new ArrayList<Notificacion>(), this);
        recyclerNotis.setAdapter(notiAdapter);

        refreshNotificaciones();

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshNotificaciones();
    }

    public void refreshNotificaciones() {
        ArrayList<Notificacion> notis = dbManager.getNotificaciones();
        notiAdapter.setNotificaciones(notis);
    }

    public void addNotificacion(Notificacion noti) {
        notiAdapter.agregarNotificacion(noti);
    }

    @Override
    public void clickNotificacion(Notificacion notificacion, int posicion) {

    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.imgbtn_perfil:
                Intent irPerfil = new Intent(getContext(), PerfilActivity.class);
                startActivity(irPerfil);
                break;
        }
    }
}
