package helpers.cristian.com.ubiety;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.fragmentos.BloquesFragment;
import helpers.cristian.com.ubiety.fragmentos.FacultadesFragment;
import helpers.cristian.com.ubiety.fragmentos.MapaFragment;
import helpers.cristian.com.ubiety.fragmentos.AlertasFragment;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Alerta;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.modelos.Usuario;
import helpers.cristian.com.ubiety.servicioweb.ResServer;
import helpers.cristian.com.ubiety.servicioweb.ServicioWeb;
import helpers.cristian.com.ubiety.servicioweb.ServicioWebUtils;
import helpers.cristian.com.ubiety.utilidades.Constantes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private final String TAG = "ActividadPrincipal";

    private final int POS_FACULTADES = 0;
    private final int POS_NOTI = 1;

    private ImageView imgItemFacultades, imgItemNoti;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private LinearLayout splash;
    private RelativeLayout contenedor;

    private ReceiverPrincipal receiver;

    private ServicioWeb servicioWeb;
    private DBManager dbManager;

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( Constantes.Acciones.AGREGAR_ALERTA);

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

        dbManager = new DBManager(this);

        if ( dbManager.getUsuarioLogeado() == null ) {
            /*
             * Si no está logueado se sobre-entiende que es un estudiante
             */

            FirebaseMessaging.getInstance().subscribeToTopic("ESTUDIANTE")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v(TAG, "Suscrito a el topico de estudiantes");
                }
            });
        }

        contenedor = findViewById(R.id.container);
        splash = findViewById(R.id.splash);
        imgItemFacultades = findViewById(R.id.bottom_item_facultades);
        imgItemNoti = findViewById(R.id.bottom_item_noti);
        pager = findViewById(R.id.pager_pages);

        pedirInfoInicial();

        imgItemFacultades.setOnClickListener(this);
        imgItemNoti.setOnClickListener(this);

        // Por defecto el mapa está seleccionado
        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.noticias_colores)
                .into(imgItemFacultades);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.noti_outline)
                .into(imgItemNoti);


        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(listenerCambioDePagina);
        pager.setCurrentItem(POS_FACULTADES);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentos = new ArrayList<Fragment>() {{
            add(POS_FACULTADES, new FacultadesFragment());
            add(POS_NOTI, new AlertasFragment());
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
                case POS_FACULTADES:


                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_colores)
                            .into(imgItemFacultades);

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noti_outline)
                            .into(imgItemNoti);

                    break;
                case POS_NOTI:

                    GlideApp.with(MainActivity.this)
                            .load("")
                            .placeholder(R.drawable.noticias_outline)
                            .into(imgItemFacultades);

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
            case R.id.bottom_item_facultades:
                pager.setCurrentItem(POS_FACULTADES);

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
                    case Constantes.Acciones.AGREGAR_ALERTA:

                        try {

                            Alerta alerta = (Alerta) intent.getSerializableExtra(Alerta.class.getSimpleName());

                            AlertasFragment alertaFragment = (AlertasFragment) pagerAdapter.getItem(POS_NOTI);

                            alertaFragment.addAlerta(alerta);

                        }catch (NullPointerException e){}
                        catch (ClassCastException ex) {}

                        break;
                }

            }
        }
    }

    private void pedirInfoInicial() {
        splash.setVisibility(View.VISIBLE);
        contenedor.setVisibility(View.GONE);

        servicioWeb = ServicioWebUtils.getServicioWeb(true);

        String rol = "ESTUDIANTE";// Por defecto

        Usuario usuario = dbManager.getUsuarioLogeado();
        if ( usuario != null ) rol = usuario.getRol();

        Call<ResServer> resServerCall = servicioWeb.getInfoInicial(rol);

        resServerCall.enqueue(new Callback<ResServer>() {
            @Override
            public void onResponse(Call<ResServer> call, Response<ResServer> response) {

                if ( response.isSuccessful() ) {
                    final ResServer resServer = response.body();

                    if ( resServer != null && resServer.isOkay() ) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dbManager.limpiarBD();

                                dbManager.insertarFacultades( resServer.getFacultades() );
                                dbManager.insertarNotificaciones( resServer.getNotificaciones() );
                                dbManager.insertarCarreras( resServer.getCarreras() );
                                dbManager.insertarNoticias( resServer.getNoticias() );

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        splash.setVisibility(View.GONE);
                                        contenedor.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }).start();

                    } else {
                        Toast.makeText(MainActivity.this, "No se pudo actualizar la información", Toast.LENGTH_SHORT).show();

                        splash.setVisibility(View.GONE);
                        contenedor.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo actualizar la información", Toast.LENGTH_SHORT).show();

                    splash.setVisibility(View.GONE);
                    contenedor.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResServer> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No se pudo actualizar la información", Toast.LENGTH_SHORT).show();
                splash.setVisibility(View.GONE);
                contenedor.setVisibility(View.VISIBLE);
            }
        });
    }
}



















