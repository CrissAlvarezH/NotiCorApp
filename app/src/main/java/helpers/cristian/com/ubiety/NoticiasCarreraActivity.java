package helpers.cristian.com.ubiety;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.adapter.NoticiasAdapter;
import helpers.cristian.com.ubiety.fragmentos.ImagenFragment;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Noticia;

public class NoticiasCarreraActivity extends AppCompatActivity {

    private Carrera carrera;
    private ViewPager pagerBanners;
    private TextView txtCantBanners;
    private RecyclerView recyclerNoticias;
    private NoticiasAdapter noticiasAdapter;
    private PagerBanners bannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_carrera);

        Bundle bundle = getIntent().getExtras();
        if ( bundle != null && bundle.getSerializable( Carrera.CLASS_NAME ) != null) {

            carrera = (Carrera) bundle.getSerializable( Carrera.CLASS_NAME );

            setToolbar( carrera.getNombre() );

            pagerBanners = findViewById(R.id.pager_banners_carrera);
            txtCantBanners = findViewById(R.id.txt_cant_banners_carrera);
            recyclerNoticias = findViewById(R.id.recycler_noticias_carreras);

            RecyclerView.LayoutManager lmNoticias = new LinearLayoutManager(this);
            recyclerNoticias.setLayoutManager(lmNoticias);
            recyclerNoticias.setNestedScrollingEnabled(false);

            ArrayList<Noticia> noticias = new ArrayList<>();
            noticias.add(new Noticia(
                    1,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWvODDXGaeQoFgAySH1IjHBZgZSuQfPnqP88JiPHb-ek96t7wi",
                    "Primera hora, atención",
                    "El dia de ayer a las 4 de la mañana se presentó una ardilla en el bosque buscando algo de comer.",
                    "2019-09-02",
                    "",
                    Noticia.Tipos.NOTICIA
            ));
            noticias.add(new Noticia(
                    1,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWvODDXGaeQoFgAySH1IjHBZgZSuQfPnqP88JiPHb-ek96t7wi",
                    "Primera hora, atención",
                    "El dia de ayer a las 4 de la mañana se presentó una ardilla en el bosque buscando algo de comer.",
                    "2019-09-02",
                    "",
                    Noticia.Tipos.NOTICIA
            ));
            noticias.add(new Noticia(
                    1,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWvODDXGaeQoFgAySH1IjHBZgZSuQfPnqP88JiPHb-ek96t7wi",
                    "Primera hora, atención",
                    "El dia de ayer a las 4 de la mañana se presentó una ardilla en el bosque buscando algo de comer.",
                    "2019-09-02",
                    "",
                    Noticia.Tipos.NOTICIA
            ));

            noticiasAdapter = new NoticiasAdapter(noticias, this);
            recyclerNoticias.setAdapter(noticiasAdapter);

            ArrayList<String> urlImgs = new ArrayList<>();
            urlImgs.add("https://stratto.weebly.com/uploads/1/3/6/6/13668912/55122_orig.jpg");
            urlImgs.add("http://www.prensalibrecasanare.com/uploads/posts/2013-07/1375172099_aulas-inconclusas-jorge-eliecer-gaitan11.jpg");
            urlImgs.add("https://www.elcomercio.com/files/article_main/uploads/2018/05/21/5b0305928c14a.jpeg");


            bannerAdapter = new PagerBanners(getSupportFragmentManager(), urlImgs);
            pagerBanners.setAdapter(bannerAdapter);

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
        private ArrayList<Fragment> fragmentos = new ArrayList<>();

        public PagerBanners(FragmentManager fm, ArrayList<String> urlImgs) {
            super(fm);

            for (String url : urlImgs) {
                fragmentos.add( ImagenFragment.getInstancia(url) );
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
    }
}
