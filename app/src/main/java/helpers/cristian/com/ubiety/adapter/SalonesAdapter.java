package helpers.cristian.com.ubiety.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.glide.GlideApp;
import helpers.cristian.com.ubiety.modelos.Salon;

public class SalonesAdapter extends RecyclerView.Adapter<SalonesAdapter.SalonViewHolder> {
    public interface ListenerClick {
        void clickItemSalon(Salon salon, int posicion);
    }

    private ArrayList<Salon> salones;
    private ListenerClick listener;
    private Context contexto;

    public SalonesAdapter(ArrayList<Salon> salones, ListenerClick listener, Context contexto) {
        this.salones = salones;
        this.listener = listener;
        this.contexto = contexto;
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtCodigo, txtPiso;

        public SalonViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_img_salon);
            txtCodigo = itemView.findViewById(R.id.item_codigo_salon);
            txtPiso = itemView.findViewById(R.id.item_piso_salon);
        }
    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int posicion) {
        View vista = LayoutInflater.from( viewGroup.getContext() )
                .inflate(R.layout.item_salon, viewGroup, false);

        return new SalonViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonViewHolder holder, int pos) {
        Salon salon = salones.get(pos);

        GlideApp.with(contexto)
                .load( salon.getUrlImg() )
                .into( holder.img );

        holder.txtCodigo.setText( salon.getCodigo() );
        holder.txtPiso.setText( salon.getPiso() + "" );
    }

    @Override
    public int getItemCount() {
        return salones.size();
    }

}
