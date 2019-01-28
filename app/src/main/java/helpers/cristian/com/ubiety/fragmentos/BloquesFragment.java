package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.BloqueActivity;
import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.adapter.BloquesAdapter;
import helpers.cristian.com.ubiety.modelos.Bloque;

public class BloquesFragment extends Fragment implements BloquesAdapter.ListenerClick {

    private RecyclerView recyclerBloques;
    private LinearLayoutManager lmBloques;
    private BloquesAdapter bloquesAdapter;

    public BloquesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_bloques, container, false);

        recyclerBloques = vista.findViewById(R.id.recycler_bloques);

        lmBloques = new LinearLayoutManager( getContext() );
        recyclerBloques.setLayoutManager(lmBloques);

        ArrayList<Bloque> bloques = new ArrayList<>();

        ArrayList<String> urlImgs = new ArrayList<>();
        urlImgs.add("https://stratto.weebly.com/uploads/1/3/6/6/13668912/55122_orig.jpg");
        urlImgs.add("http://www.prensalibrecasanare.com/uploads/posts/2013-07/1375172099_aulas-inconclusas-jorge-eliecer-gaitan11.jpg");
        urlImgs.add("https://www.elcomercio.com/files/article_main/uploads/2018/05/21/5b0305928c14a.jpeg");

        bloques.add( new Bloque(1, "Bioclimatico", "52", 12, urlImgs) );
        bloques.add( new Bloque(1, "Informatica", "23", 2, urlImgs) );
        bloques.add( new Bloque(1, "Matematicas", "12", 31, urlImgs) );

        bloquesAdapter = new BloquesAdapter(bloques, getContext(), this);
        recyclerBloques.setAdapter(bloquesAdapter);

        return vista;
    }

    @Override
    public void clickItemBoque(Bloque bloque, int posicion) {
        Intent intent = new Intent(getContext(), BloqueActivity.class);
        intent.putExtra(Bloque.CLASS_NAME, bloque);
        startActivity(intent);
    }
}
