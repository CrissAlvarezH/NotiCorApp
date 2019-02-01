package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.adapter.CarrerasAdapter;
import helpers.cristian.com.ubiety.adapter.FacultadesAdapter;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;


public class FacultadesFragment extends Fragment implements CarrerasAdapter.ListenerClick, FacultadesAdapter.ListenerClick {

    private ViewPager pagerNoticias;
    private RecyclerView recyclerFacultades;
    private FacultadesAdapter facultadesAdapter;

    public FacultadesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_facultades, container, false);

        pagerNoticias = vista.findViewById(R.id.pager_noticias);
        recyclerFacultades = vista.findViewById(R.id.recycler_facultades);

        RecyclerView.LayoutManager lmFacultades = new LinearLayoutManager(getContext());
        recyclerFacultades.setLayoutManager(lmFacultades);
        recyclerFacultades.setNestedScrollingEnabled(false);

        ArrayList<Facultad> facultades = new ArrayList<>();
        ArrayList<Carrera> carreras = new ArrayList<>();
        carreras.add(new Carrera(1, "Carrera 1"));
        carreras.add(new Carrera(1, "Carrera 2"));
        carreras.add(new Carrera(1, "Carrera 2"));

        facultades.add(new Facultad(
                1,
                "Ingenierias",
                carreras,
                false
        ));
        facultades.add(new Facultad(
                1,
                "Ingenierias",
                carreras,
                false
        ));
        facultades.add(new Facultad(
                1,
                "Ingenierias",
                carreras,
                false
        ));

        facultadesAdapter = new FacultadesAdapter(getContext(), facultades, this, this);
        recyclerFacultades.setAdapter(facultadesAdapter);


        return vista;
    }


    @Override
    public void clickCarrera(Carrera carrera, int posicion) {
        Toast.makeText(getContext(), "Click a "+carrera.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickFacultad(Facultad facultad, int posicion) {
        facultad.setMostrarCarreras( !facultad.isMostrarCarreras() );
        facultadesAdapter.setFacultad(facultad, posicion);
    }
}
