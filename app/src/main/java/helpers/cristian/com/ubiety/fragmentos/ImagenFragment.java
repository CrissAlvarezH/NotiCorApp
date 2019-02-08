package helpers.cristian.com.ubiety.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.utilidades.Constantes;


public class ImagenFragment extends Fragment {

    private final static String ARG_URL_IMG = "url_imagen";

    private ImageView imagen;

    public ImagenFragment() {
    }

    public static ImagenFragment getInstanciaBloque(String urlImg) {
        ImagenFragment imagenFragment = new ImagenFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL_IMG, urlImg);

        imagenFragment.setArguments(bundle);

        return imagenFragment;
    }

    public static ImagenFragment getInstanciaBanner(Noticia noticia) {
        ImagenFragment imagenFragment = new ImagenFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL_IMG, Constantes.URLs.IMAGEN_NOTICIA + noticia.getId() + ".jpg");

        imagenFragment.setArguments(bundle);

        return imagenFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_imagen, container, false);

        imagen = vista.findViewById(R.id.imagen);

        String urlImagen = "";
        Bundle args = getArguments();

        if (args != null) urlImagen = args.getString(ARG_URL_IMG);


        GlideApp.with(this)
                .load(urlImagen)
                .into(imagen);


        return vista;
    }


}
