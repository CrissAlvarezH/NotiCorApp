package helpers.cristian.com.ubiety;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.fragmentos.BloquesFragment;
import helpers.cristian.com.ubiety.fragmentos.MapaFragment;
import helpers.cristian.com.ubiety.fragmentos.NotificacionesFragment;
import helpers.cristian.com.ubiety.glide.GlideApp;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private final int POS_BLOQUES = 0;
    private final int POS_MAPA = 1;
    private final int POS_NOTI = 2;

    private ImageView imgItemMapa, imgItemBloques, imgItemNoti;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgItemMapa = findViewById(R.id.bottom_item_map);
        imgItemBloques = findViewById(R.id.bottom_item_bloques);
        imgItemNoti = findViewById(R.id.bottom_item_noti);
        pager = findViewById(R.id.pager_pages);

        imgItemBloques.setOnClickListener(this);
        imgItemMapa.setOnClickListener(this);
        imgItemNoti.setOnClickListener(this);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.edificios_outline)
                .into(imgItemBloques);

        // Por defecto el mapa está seleccionado
        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.google_maps_colores)
                .into(imgItemMapa);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.noti_outline)
                .into(imgItemNoti);


        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(listenerCambioDePagina);
        pager.setCurrentItem(POS_MAPA);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentos = new ArrayList<Fragment>() {{
            add(POS_BLOQUES, new BloquesFragment());
            add(POS_MAPA, new MapaFragment());
            add(POS_NOTI, new NotificacionesFragment());
        }};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
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

            fragmentos.set(position, fragment);// Actualizamos la instancia del afragment

            return fragment;
        }
    }

    private ViewPager.OnPageChangeListener listenerCambioDePagina = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {}

        @Override
        public void onPageSelected(int pos) {
            switch (pos) {
                case POS_BLOQUES:

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.edificios_colores)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_outline)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_MAPA:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.edificios_outline)
                            .into(imgItemBloques);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.google_maps_colores)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_NOTI:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.edificios_outline)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_outline)
                            .into(imgItemMapa);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.noti_colores)
                            .into(imgItemNoti);

                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int pos) {}
    };

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.bottom_item_bloques:
                pager.setCurrentItem(POS_BLOQUES);

                break;
            case R.id.bottom_item_map:
                pager.setCurrentItem(POS_MAPA);

                break;
            case R.id.bottom_item_noti:
                pager.setCurrentItem(POS_NOTI);

                break;
        }
    }
}



















