package helpers.cristian.com.ubiety;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import helpers.cristian.com.ubiety.basedatos.DBManager;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Profesor;
import helpers.cristian.com.ubiety.servicioweb.ResServer;
import helpers.cristian.com.ubiety.servicioweb.ServicioWeb;
import helpers.cristian.com.ubiety.servicioweb.ServicioWebUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_CARRERA;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.ID_USUARIO;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_NOTIFICACIONES;
import static helpers.cristian.com.ubiety.basedatos.DBHelper.TABLA_USUARIO_CARRERA;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private EditText edtUsuario, edtPass;
    private ProgressBar progress;
    private Button btnIngresar;

    private ServicioWeb servicioWeb;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager = new DBManager(this);

        imgLogo = findViewById(R.id.img_logo_login);
        edtUsuario = findViewById(R.id.edt_usuario);
        edtPass = findViewById(R.id.edt_pass);
        progress = findViewById(R.id.progress_login);
        btnIngresar = findViewById(R.id.btn_ingresar);

        GlideApp.with(this)
                .load("")
                .placeholder(R.drawable.logo_unicor)
                .into(imgLogo);

        servicioWeb = ServicioWebUtils.getServicioWeb(true);
    }

    public void clickIngresar(View btn) {
        if ( validarCampos() ) {
            progress.setVisibility(View.VISIBLE);
            btnIngresar.setVisibility(View.GONE);

            String usuario = edtUsuario.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            Call<ResServer> resServerCall = servicioWeb.login(usuario, pass);

            resServerCall.enqueue(new Callback<ResServer>() {
                @Override
                public void onResponse(Call<ResServer> call, Response<ResServer> response) {

                    if ( response.isSuccessful() ) {
                        final ResServer resServer = response.body();

                        if ( resServer != null && resServer.isOkay() ) {

                            if ( resServer.getMensaje().equals("PROFESOR") ) {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Profesor profesor = resServer.getProfesor();

                                        int id = (int) dbManager.insertarModelo(profesor);

                                        if ( profesor.getIdCarrera() > 0 ) {
                                            ContentValues values = new ContentValues();
                                            values.put(ID_USUARIO, id);
                                            values.put(ID_CARRERA, profesor.getIdCarrera());

                                            dbManager.realizarInsert(TABLA_USUARIO_CARRERA, values);
                                        }

                                        // Reseteamos el token y nos suscribimos a los tokens de dodente y de la carrera respectiva
                                        try {
                                            FirebaseInstanceId.getInstance().deleteInstanceId();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        // Para las notificaciones
                                        FirebaseMessaging.getInstance().subscribeToTopic( profesor.getRol() );

                                        // Para las noticias
                                        if ( profesor.getIdCarrera() > 0 )
                                            FirebaseMessaging.getInstance().subscribeToTopic( "CARRERA_"+profesor.getIdCarrera() );

                                        int res = dbManager.limpiarTabla(TABLA_NOTIFICACIONES);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                setResult(RESULT_OK);
                                                finish();

                                                progress.setVisibility(View.GONE);
                                                btnIngresar.setVisibility(View.VISIBLE);
                                            }
                                        });

                                    }
                                }).start();

                            } else {
                                progress.setVisibility(View.GONE);
                                btnIngresar.setVisibility(View.VISIBLE);

                                Toast.makeText(LoginActivity.this, "Su rol no le permite ingresar a la app", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progress.setVisibility(View.GONE);
                            btnIngresar.setVisibility(View.VISIBLE);

                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progress.setVisibility(View.GONE);
                        btnIngresar.setVisibility(View.VISIBLE);

                        Toast.makeText(LoginActivity.this, "No se pudo establecer conexión", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResServer> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "No se pudo establecer conexión", Toast.LENGTH_SHORT).show();

                    progress.setVisibility(View.GONE);
                    btnIngresar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private boolean validarCampos() {
        if ( edtUsuario.getText().toString().trim().isEmpty() ) {
            edtUsuario.setError("Digite su usuario");
            edtUsuario.requestFocus();

            return false;
        }

        if ( edtPass.getText().toString().trim().isEmpty() ) {
            edtPass.setError("Digite su contraseña");
            edtPass.requestFocus();

            return false;
        }

        return true;
    }
}
