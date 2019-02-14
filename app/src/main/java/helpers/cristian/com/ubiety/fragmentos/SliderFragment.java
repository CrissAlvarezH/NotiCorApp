package helpers.cristian.com.ubiety.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.utilidades.Constantes;


public class SliderFragment extends Fragment {

    private final static String ARG_URL_IMG = "url_imagen";

    private ImageView imagen;
    private Noticia noticia;
    private LinearLayout layoutTexto;
    private TextView txtTituloNoticia;
    private TextView txtDescripcionNoticia;

    public SliderFragment() {
    }

    public static SliderFragment getInstanciaBloque(String urlImg) {
        SliderFragment sliderFragment = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL_IMG, urlImg);

        sliderFragment.setArguments(bundle);

        return sliderFragment;
    }

    public static SliderFragment getInstanciaNoticia(Noticia noticia) {
        SliderFragment sliderFragment = new SliderFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL_IMG, Constantes.URLs.IMAGEN_NOTICIA + noticia.getId() + ".jpg");
        bundle.putSerializable(Noticia.class.getSimpleName(), noticia);

        sliderFragment.setArguments(bundle);

        return sliderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_slider, container, false);

        Log.v(Constantes.TAG_DEBUG, "onCreateView sliderFragment");

        imagen = vista.findViewById(R.id.imagen);
        layoutTexto = vista.findViewById(R.id.layout_texto_slider);
        txtTituloNoticia = vista.findViewById(R.id.txt_titulo_noticia_slider);
        txtDescripcionNoticia = vista.findViewById(R.id.txt_descripcion_slider);

        String urlImagen = "";
        Bundle args = getArguments();

        if (args != null) {
            urlImagen = args.getString(ARG_URL_IMG);
            Log.v(Constantes.TAG_DEBUG, "Slider: urlImagen "+urlImagen);

            noticia = (Noticia) args.getSerializable(Noticia.class.getSimpleName());

            if (noticia != null && noticia.getTipo() == Noticia.Tipos.NOTICIA) {
                Log.v(Constantes.TAG_DEBUG, "Slider: noticia no nula, tipo noticia");

                layoutTexto.setVisibility(View.VISIBLE);

                txtTituloNoticia.setText( noticia.getTitulo() );
                txtDescripcionNoticia.setText( noticia.getDescripcion() );

            } else {
                layoutTexto.setVisibility(View.GONE);
            }
        }


        GlideApp.with(this)
                .load(urlImagen)
                .into(imagen);

        return vista;
    }


}
