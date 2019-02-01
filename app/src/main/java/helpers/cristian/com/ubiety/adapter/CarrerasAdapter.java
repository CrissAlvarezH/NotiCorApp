package helpers.cristian.com.ubiety.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.modelos.Carrera;

public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarrerasViewHolder> {

    public interface ListenerClick {
        void clickCarrera(Carrera carrera, int posicion);
    }

    private ArrayList<Carrera> carreras;
    private ListenerClick listener;

    public CarrerasAdapter(ArrayList<Carrera> carreras, ListenerClick listener) {
        this.carreras = carreras;
        this.listener = listener;
    }

    public class CarrerasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtNombre;

        public CarrerasViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.item_carrera);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.clickCarrera( carreras.get( getAdapterPosition() ), getAdapterPosition() );
            }
        }
    }

    @NonNull
    @Override
    public CarrerasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from( viewGroup.getContext() )
                .inflate(R.layout.item_carrera, viewGroup, false);

        return new CarrerasViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrerasViewHolder holder, int pos) {
        Carrera carrera = carreras.get(pos);

        holder.txtNombre.setText( carrera.getNombre() );
    }

    @Override
    public int getItemCount() {
        return carreras.size();
    }


}
