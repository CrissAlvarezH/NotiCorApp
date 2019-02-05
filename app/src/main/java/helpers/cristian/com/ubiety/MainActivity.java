package helpers.cristian.com.ubiety;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.fragmentos.BloquesFragment;
import helpers.cristian.com.ubiety.fragmentos.FacultadesFragment;
import helpers.cristian.com.ubiety.fragmentos.MapaFragment;
import helpers.cristian.com.ubiety.fragmentos.NotificacionesFragment;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.utilidades.Constantes;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private final String TAG = "ActividadPrincipal";

    private final int POS_MAPA = 0;
    private final int POS_FACULTADES = 1;
    private final int POS_BLOQUES = 2;
    private final int POS_NOTI = 3;

    private ImageView imgItemMapa, imgItemFacultades, imgItemBloques, imgItemNoti;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private ReceiverPrincipal receiver;

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( Constantes.Acciones.AGREGAR_NOTIFICACION );

        receiver = new ReceiverPrincipal();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if ( receiver != null ) LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("Estudiantes")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.v(TAG, "Suscrito a el topico de estudiantes");
            }
        });

        imgItemMapa = findViewById(R.id.bottom_item_map);
        imgItemFacultades = findViewById(R.id.bottom_item_facultades);
        imgItemBloques = findViewById(R.id.bottom_item_bloques);
        imgItemNoti = findViewById(R.id.bottom_item_noti);
        pager = findViewById(R.id.pager_pages);

        imgItemMapa.setOnClickListener(this);
        imgItemFacultades.setOnClickListener(this);
        imgItemBloques.setOnClickListener(this);
        imgItemNoti.setOnClickListener(this);

        // Por defecto el mapa está seleccionado
        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.google_maps_colores)
                .into(imgItemMapa);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.noticias_outline)
                .into(imgItemFacultades);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.edificios_outline)
                .into(imgItemBloques);

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
            add(POS_MAPA, new MapaFragment());
            add(POS_FACULTADES, new FacultadesFragment());
            add(POS_BLOQUES, new BloquesFragment());
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
                case POS_MAPA:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_colores)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_outline)
                            .into(imgItemFacultades);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.edificios_outline)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_FACULTADES:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_outline)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_colores)
                            .into(imgItemFacultades);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.edificios_outline)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_BLOQUES:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_outline)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_outline)
                            .into(imgItemFacultades);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.edificios_colores)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_NOTI:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.google_maps_outline)
                            .into(imgItemMapa);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_outline)
                            .into(imgItemFacultades);

                    GlideApp.with( MainActivity.this )
                            .load("")
                            .placeholder(R.drawable.edificios_outline)
                            .into(imgItemBloques);

                    GlideApp.with(MainActivity.this)
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
            case R.id.bottom_item_map:
                pager.setCurrentItem(POS_MAPA);

                break;
            case R.id.bottom_item_facultades:
                pager.setCurrentItem(POS_FACULTADES);

                break;
            case R.id.bottom_item_bloques:
                pager.setCurrentItem(POS_BLOQUES);

                break;
            case R.id.bottom_item_noti:
                pager.setCurrentItem(POS_NOTI);

                break;
        }
    }

    private class ReceiverPrincipal extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ( intent != null && intent.getAction() != null ) {

                switch ( intent.getAction() ) {
                    case Constantes.Acciones.AGREGAR_NOTIFICACION:

                        try {

                            Notificacion noti = (Notificacion) intent.getSerializableExtra(Notificacion.class.getSimpleName());

                            NotificacionesFragment notiFragment = (NotificacionesFragment) pagerAdapter.getItem(POS_NOTI);

                            notiFragment.addNotificacion(noti);

                        }catch (NullPointerException e){}
                        catch (ClassCastException ex) {}

                        break;
                }

            }
        }
    }
}



















