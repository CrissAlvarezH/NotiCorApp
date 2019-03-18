package helpers.cristian.com.ubiety;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.adapter.NoticiasAdapter;
import helpers.cristian.com.ubiety.fragmentos.SliderFragment;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.servicioweb.ResServer;
import helpers.cristian.com.ubiety.servicioweb.ServicioWeb;
import helpers.cristian.com.ubiety.servicioweb.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class NoticiasCarreraActivity extends AppCompatActivity {

    private Carrera carrera;
    private ViewPager pagerBanners;
    private TextView txtCantBanners;
    private RecyclerView recyclerNoticias;
    private NoticiasAdapter noticiasAdapter;
    private PagerBanners bannerAdapter;
    private LinearLayout contenedor, cargando;
    private TextView txtNoHayNoticias;
    private SwipeRefreshLayout swipeRefresh;

    private ServicioWeb servicioWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_carrera);

        Bundle bundle = getIntent().getExtras();
        if ( bundle != null && bundle.getSerializable( Carrera.CLASS_NAME ) != null) {

            carrera = (Carrera) bundle.getSerializable( Carrera.CLASS_NAME );

            setToolbar( carrera.getNombre() );

            swipeRefresh = findViewById(R.id.swipe_refresh);
            contenedor = findViewById(R.id.contenedor_noticias);
            cargando = findViewById(R.id.layout_cargando_noticias);
            pagerBanners = findViewById(R.id.pager_banners_carrera);
            txtCantBanners = findViewById(R.id.txt_cant_banners_carrera);
            recyclerNoticias = findViewById(R.id.recycler_noticias_carreras);
            txtNoHayNoticias = findViewById(R.id.txt_no_hay_noticias);

            RecyclerView.LayoutManager lmNoticias = new LinearLayoutManager(this);
            recyclerNoticias.setLayoutManager(lmNoticias);
            recyclerNoticias.setNestedScrollingEnabled(false);

            noticiasAdapter = new NoticiasAdapter(new ArrayList<Noticia>(), this);
            recyclerNoticias.setAdapter(noticiasAdapter);

            bannerAdapter = new PagerBanners(getSupportFragmentManager(), new ArrayList<Noticia>());
            pagerBanners.setAdapter(bannerAdapter);

            pedirNoticias( carrera.getId(), true );

            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            pedirNoticias(carrera.getId(), false);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }).start();

                }
            });

            if ( noticiasAdapter.getItemCount() > 0 ) txtNoHayNoticias.setVisibility(GONE);
            else txtNoHayNoticias.setVisibility(View.VISIBLE);

            if ( bannerAdapter.getCount() > 0 ) pagerBanners.setVisibility(View.VISIBLE);
            else pagerBanners.setVisibility(GONE);

        } else {
            finish();
            Toast.makeText(this, "Carrera no especificada", Toast.LENGTH_SHORT).show();
        }
    }

    private void setToolbar(String nombreCarrera) {
        Toolbar toolbar = findViewById(R.id.toolbar_noticias_carrera);
        setSupportActionBar(toolbar);

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(nombreCarrera);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PagerBanners extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentos;

        public PagerBanners(FragmentManager fm, ArrayList<Noticia> banners) {
            super(fm);

            fragmentos = new ArrayList<>();

            for (Noticia banner : banners) {
                fragmentos.add( SliderFragment.getInstanciaNoticia(banner) );
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

    private void pedirNoticias(int idCarrera, final boolean mostrarCargando) {
        if (mostrarCargando) {
            cargando.setVisibility(View.VISIBLE);
            contenedor.setVisibility(GONE);
        }

        servicioWeb = ServicioWebUtils.getServicioWeb(true);

        Call<ResServer> resServerCall = servicioWeb.getNoticiaDeCarrera(idCarrera);

        resServerCall.enqueue(new Callback<ResServer>() {
            @Override
            public void onResponse(Call<ResServer> call, Response<ResServer> response) {

                if ( response.isSuccessful() ) {

                    ResServer resServer = response.body();

                    if ( resServer != null && resServer.isOkay() ) {
                        ArrayList<Noticia> noticias = resServer.getNoticias();
                        ArrayList<Noticia> banners = resServer.getBanners();

                        if ( noticias != null ) noticiasAdapter.setNoticias(noticias);
                        if ( banners != null ) {
                            bannerAdapter.setBanners(banners);
                            txtCantBanners.setText( banners.size() + " Banners" );
                        }

                        if ( noticiasAdapter.getItemCount() > 0 ) txtNoHayNoticias.setVisibility(GONE);
                        else txtNoHayNoticias.setVisibility(View.VISIBLE);

                        if ( bannerAdapter.getCount() > 0 ) pagerBanners.setVisibility(View.VISIBLE);
                        else pagerBanners.setVisibility(GONE);
                    }
                }

                if (mostrarCargando) {
                    cargando.setVisibility(GONE);
                    contenedor.setVisibility(View.VISIBLE);
                } else {
                    swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ResServer> call, Throwable t) {
                Toast.makeText(NoticiasCarreraActivity.this, "No se pudo cargar la información", Toast.LENGTH_SHORT).show();

                if (mostrarCargando) {
                    cargando.setVisibility(GONE);
                    contenedor.setVisibility(View.VISIBLE);
                } else {
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
    }
}
