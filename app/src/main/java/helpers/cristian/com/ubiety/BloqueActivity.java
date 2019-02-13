package helpers.cristian.com.ubiety;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.adapter.SalonesAdapter;
import helpers.cristian.com.ubiety.fragmentos.SliderFragment;
import helpers.cristian.com.ubiety.modelos.Bloque;
import helpers.cristian.com.ubiety.modelos.Salon;

public class BloqueActivity extends AppCompatActivity implements OnMapReadyCallback, SalonesAdapter.ListenerClick {

    private Toolbar toolbar;
    private ViewPager pagerImgs;
    private TextView txtCantFotos;
    private TextView txtNombre;
    private TextView txtCodigo;
    private MapView mapView;
    private RecyclerView recyclerSalones;

    private Bloque bloque;

    private GoogleMap mapa;

    private PagerImagenes pagerAdapter;

    private GridLayoutManager lmSalones;
    private SalonesAdapter salonesAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloque);

        if ( getIntent().getExtras() != null && getIntent().getExtras().getSerializable( Bloque.CLASS_NAME ) != null ) {
            bloque = (Bloque) getIntent().getExtras().getSerializable( Bloque.CLASS_NAME );
        } else {
            Toast.makeText(this, "Bloque no especificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setToolbar();

        pagerImgs = findViewById(R.id.pager_imgs_bloque);
        txtCantFotos = findViewById(R.id.txt_cant_fotos);
        txtNombre = findViewById(R.id.txt_nombre_bloque);
        txtCodigo = findViewById(R.id.txt_codigo_bloque);
        mapView = findViewById(R.id.map_view_bloque);
        recyclerSalones = findViewById(R.id.recycler_salones_bloque);

        lmSalones = new GridLayoutManager(this, 2);
        recyclerSalones.setLayoutManager(lmSalones);

        ArrayList<Salon> salones = new ArrayList<>();
        salones.add( new Salon(1, 2, "201", "Telematica", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRl_lpqhCyRmY4RiG8XfOEciolyT1bJSKrPrXYQkR7buGuTKIPG") );
        salones.add( new Salon(1, 2, "202", "Telematica", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRl_lpqhCyRmY4RiG8XfOEciolyT1bJSKrPrXYQkR7buGuTKIPG") );
        salones.add( new Salon(1, 2, "203", "Telematica", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRl_lpqhCyRmY4RiG8XfOEciolyT1bJSKrPrXYQkR7buGuTKIPG") );
        salones.add( new Salon(1, 3, "302", "Telematica", "https://www.utadeo.edu.co/sites/tadeo/files/collections/node/gallery/field_image/web_aula_103_modulo_1_20160122_001.jpg") );
        salones.add( new Salon(1, 3, "303", "Telematica", "https://images-na.ssl-images-amazon.com/images/I/81yj4g%2BYTpL._SY606_.jpg") );
        salones.add( new Salon(1, 4, "401", "Telematica", "https://images-na.ssl-images-amazon.com/images/I/81yj4g%2BYTpL._SY606_.jpg") );
        salones.add( new Salon(1, 4, "402", "Telematica", "https://images-na.ssl-images-amazon.com/images/I/81yj4g%2BYTpL._SY606_.jpg") );

        salonesAdapter = new SalonesAdapter(salones, this, this);
        recyclerSalones.setAdapter(salonesAdapter);
        recyclerSalones.setNestedScrollingEnabled(false);
        recyclerSalones.setHasFixedSize(true);

        txtNombre.setText( bloque.getNombre() );
        txtCodigo.setText( bloque.getCodigo() );
        txtCantFotos.setText( bloque.getUrlImagenes().size() + " Fotos" );

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        pagerAdapter = new PagerImagenes(getSupportFragmentManager(), bloque.getUrlImagenes());
        pagerImgs.setAdapter(pagerAdapter);

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_bloque);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "Bloque " + bloque.getCodigo() );
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {
            case android.R.id.home:
                finish();
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setMapToolbarEnabled(false);
        mapa.getUiSettings().setScrollGesturesEnabled(false);
    }

    @Override
    public void clickItemSalon(Salon salon, int posicion) {

    }

    private class PagerImagenes extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentos = new ArrayList<>();

        public PagerImagenes(FragmentManager fm, ArrayList<String> urlImgs) {
            super(fm);

            for (String url : urlImgs) {
                fragmentos.add( SliderFragment.getInstanciaBloque(url) );
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
