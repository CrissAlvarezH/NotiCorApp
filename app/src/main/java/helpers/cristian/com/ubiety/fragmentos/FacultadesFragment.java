package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.NoticiasCarreraActivity;
import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.adapter.CarrerasAdapter;
import helpers.cristian.com.ubiety.adapter.FacultadesAdapter;
import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Facultad;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.utilidades.Constantes;


public class FacultadesFragment extends Fragment implements CarrerasAdapter.ListenerClick, FacultadesAdapter.ListenerClick {

    private ViewPager pagerNoticias;
    private RecyclerView recyclerFacultades;
    private FacultadesAdapter facultadesAdapter;
    private DBManager dbManager;
    private BannerAdapter bannerAdapter;

    public FacultadesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_facultades, container, false);
        dbManager = new DBManager(getContext());

        pagerNoticias = vista.findViewById(R.id.pager_noticias);
        recyclerFacultades = vista.findViewById(R.id.recycler_facultades);

        RecyclerView.LayoutManager lmFacultades = new LinearLayoutManager(getContext());
        recyclerFacultades.setLayoutManager(lmFacultades);
        recyclerFacultades.setNestedScrollingEnabled(false);

        ArrayList<Facultad> facultades = dbManager.getFacultades();

        facultadesAdapter = new FacultadesAdapter(getContext(), facultades, this, this);
        recyclerFacultades.setAdapter(facultadesAdapter);

        ArrayList<Noticia> noticias = dbManager.getNoticias();

        bannerAdapter = new BannerAdapter(getChildFragmentManager(), noticias);
        pagerNoticias.setAdapter(bannerAdapter);

        Log.v(Constantes.TAG_DEBUG, "onCreateView facultadesFragment");

        return vista;
    }


    @Override
    public void clickCarrera(Carrera carrera, int posicion) {
        Intent intent = new Intent(getContext(), NoticiasCarreraActivity.class);
        intent.putExtra(Carrera.CLASS_NAME, carrera);
        startActivity(intent);
    }

    @Override
    public void clickFacultad(Facultad facultad, int posicion) {
        facultad.setMostrarCarreras( !facultad.isMostrarCarreras() );
        facultadesAdapter.setFacultad(facultad, posicion);
    }

    private class BannerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentos;

        public BannerAdapter(FragmentManager fm, ArrayList<Noticia> banners) {
            super(fm);

            fragmentos = new ArrayList<>();

            for (Noticia banner : banners) {
                fragmentos.add( SliderFragment.getInstanciaNoticia(banner) );
                Log.v(Constantes.TAG_DEBUG, "Agregando un slider");
            }
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentos.get(i);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);

            fragmentos.set(position, fragment);// Actualizamos la instancia en memoria del fragment
            Log.v(Constantes.TAG_DEBUG, "instantiateImten, pos: "+position);


            return fragment;
        }

        public void setBanners(ArrayList<Noticia> banners) {
            fragmentos = new ArrayList<>();

            for (Noticia banner : banners) {
                fragmentos.add( SliderFragment.getInstanciaNoticia(banner) );
            }

            notifyDataSetChanged();
        }
    }
}
