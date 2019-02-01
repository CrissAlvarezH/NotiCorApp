package helpers.cristian.com.ubiety.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.modelos.Facultad;

public class FacultadesAdapter extends RecyclerView.Adapter<FacultadesAdapter.FacultadesViewHolder> {

    public interface ListenerClick {
        void clickFacultad(Facultad facultad, int posicion);
    }

    private Context contexto;
    private ArrayList<Facultad> facultades;
    private CarrerasAdapter.ListenerClick listenerCarreras;
    private ListenerClick listenerFacultades;

    public FacultadesAdapter(Context contexto, ArrayList<Facultad> facultades, ListenerClick listenerFacultades,CarrerasAdapter.ListenerClick listenerCarreras) {
        this.contexto = contexto;
        this.facultades = facultades;
        this.listenerCarreras = listenerCarreras;
        this.listenerFacultades = listenerFacultades;
    }

    public class FacultadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtFacultad;
        private final ImageView imgUpDown;
        private final RelativeLayout layoutFacultad;
        private final RecyclerView recyclerCarreras;

        public FacultadesViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutFacultad = itemView.findViewById(R.id.layout_item_facultad);
            txtFacultad = itemView.findViewById(R.id.item_facultad);
            imgUpDown = itemView.findViewById(R.id.item_img_updown);
            recyclerCarreras = itemView.findViewById(R.id.recycler_carreras);

            RecyclerView.LayoutManager lmCarreras = new LinearLayoutManager(contexto);
            recyclerCarreras.setLayoutManager(lmCarreras);
            recyclerCarreras.setHasFixedSize(true);
            recyclerCarreras.setNestedScrollingEnabled(false);

            layoutFacultad.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listenerFacultades != null)
                listenerFacultades.clickFacultad( facultades.get(getAdapterPosition()), getAdapterPosition() );
        }
    }

    @NonNull
    @Override
    public FacultadesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from( viewGroup.getContext() )
                        .inflate(R.layout.item_facultades, viewGroup, false);

        return new FacultadesViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultadesViewHolder holder, int pos) {
        Facultad facultad = facultades.get(pos);

        holder.txtFacultad.setText( facultad.getNombre() );

        holder.imgUpDown.setImageResource( facultad.isMostrarCarreras() ? R.drawable.ic_flecha_down : R.drawable.ic_flecha_left );

        CarrerasAdapter carrerasAdapter = new CarrerasAdapter(facultad.getCarreras(), listenerCarreras);
        holder.recyclerCarreras.setAdapter(carrerasAdapter);

        holder.recyclerCarreras.setVisibility( facultad.isMostrarCarreras() ? View.VISIBLE : View.GONE );
    }

    @Override
    public int getItemCount() {
        return facultades.size();
    }

    public void setFacultad(Facultad facultad, int posicion) {
        facultades.set(posicion, facultad);
        notifyItemChanged(posicion);
    }
}




























