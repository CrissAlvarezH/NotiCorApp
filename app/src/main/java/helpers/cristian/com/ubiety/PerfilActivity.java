package helpers.cristian.com.ubiety;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.modelos.Carrera;
import helpers.cristian.com.ubiety.modelos.Usuario;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTIFICACIONES;

public class PerfilActivity extends AppCompatActivity {
    private final int COD_LOGUEARSE = 523;
    private final String TAG = "ActividadPerfil";

    private DBManager dbManager;

    private LinearLayout layoutIrPerfil;
    private LinearLayout contenedor;

    private TextView txtNombre, txtCarrera;
    private ProgressBar progressCerrarSesion;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setToolbar();

        progressCerrarSesion = findViewById(R.id.progress_cerrar_sesion);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        txtNombre = findViewById(R.id.txt_nombre_usuario);
        txtCarrera = findViewById(R.id.txt_carrera_usuario);

        dbManager = new DBManager(this);

        setearDatos();
    }

    private void setearDatos() {
        Usuario usuario = dbManager.getUsuarioLogeado();

        layoutIrPerfil = findViewById(R.id.layout_ir_login);
        contenedor = findViewById(R.id.contenedor_perfil);

        if ( usuario != null ) {
            layoutIrPerfil.setVisibility(View.GONE);
            contenedor.setVisibility(View.VISIBLE);

            Carrera carrera = dbManager.getCarreraDeUsuario();

            txtNombre.setText( usuario.getNombres() + " " + usuario.getApellidos() );
            txtCarrera.setText( carrera.getNombre() );

        } else {
            layoutIrPerfil.setVisibility(View.VISIBLE);
            contenedor.setVisibility(View.GONE);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_perfil);

        setSupportActionBar(toolbar);

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void irLogin(View btn) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, COD_LOGUEARSE);
    }

    public void clickCerrarSesion(View btn) {

        Dialog dialogSeguro = new AlertDialog.Builder(this)
                .setTitle("¿Seguro que quiere cerrar sesión?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cerrarSesion();
                    }
                })
                .setNegativeButton("NO", null)
                .create();

        if ( !PerfilActivity.this.isFinishing() )
            dialogSeguro.show();
    }

    private void cerrarSesion() {
        progressCerrarSesion.setVisibility(View.VISIBLE);
        btnCerrarSesion.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dbManager.borrarCredenciasles();
                dbManager.limpiarTabla(TABLA_NOTIFICACIONES);

                // Resetamos el id de firebase y nos volvemos a suscrbir como estudiantes
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FirebaseMessaging.getInstance().subscribeToTopic("ESTUDIANTE")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                setearDatos();

                                progressCerrarSesion.setVisibility(View.GONE);
                                btnCerrarSesion.setVisibility(View.VISIBLE);
                            }
                        });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK ) {
            switch ( requestCode ) {
                case COD_LOGUEARSE:
                    setearDatos();
                    break;
            }
        }
    }
}
