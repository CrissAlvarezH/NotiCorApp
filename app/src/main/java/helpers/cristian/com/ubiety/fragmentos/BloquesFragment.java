package helpers.cristian.com.ubiety.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import helpers.cristian.com.ubiety.R;

public class BloquesFragment extends Fragment {

    public BloquesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_bloques, container, false);



        return vista;
    }

}
