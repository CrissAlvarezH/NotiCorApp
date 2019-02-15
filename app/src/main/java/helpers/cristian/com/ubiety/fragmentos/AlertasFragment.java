package helpers.cristian.com.ubiety.fragmentos;

import android.content.Intent;
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
import helpers.cristian.com.ubiety.adapter.AlertasAdapter;
import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.modelos.Alerta;
import helpers.cristian.com.ubiety.modelos.Notificacion;


public class AlertasFragment extends Fragment implements AlertasAdapter.ListenerNotificacion, View.OnClickListener {

    private RecyclerView recyclerNotis;
    private AlertasAdapter notiAdapter;
    private DBManager dbManager;
    private ImageView btnIrPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_alertas, container, false);
        dbManager = new DBManager(getContext());

        recyclerNotis = vista.findViewById(R.id.recycler_notis);
        btnIrPerfil = vista.findViewById(R.id.imgbtn_perfil);

        btnIrPerfil.setOnClickListener(this);

        RecyclerView.LayoutManager lmNotis = new LinearLayoutManager(getContext());
        recyclerNotis.setLayoutManager(lmNotis);


        notiAdapter = new AlertasAdapter(getContext(), new ArrayList<Alerta>(), this);
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
        ArrayList<Alerta> notis = dbManager.getAlertas();
        notiAdapter.setAlertas(notis);
    }

    public void addAlerta(Alerta alerta) {

        if ( alerta.getMotivo() == Alerta.Motivos.BANNER ){
            alerta.getBanner().setCarrera( dbManager.getCarrera( alerta.getBanner().getIdCarrera() ) );
        }

        if ( alerta.getMotivo() == Alerta.Motivos.NOTICIA ) {
            alerta.getNoticia().setCarrera( dbManager.getCarrera( alerta.getNoticia().getIdCarrera() ) );
        }

        notiAdapter.agregarAlarma(alerta);
        recyclerNotis.smoothScrollToPosition(0);
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
