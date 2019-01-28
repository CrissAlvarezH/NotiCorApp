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
import helpers.cristian.com.ubiety.modelos.Bloque;

public class BloquesAdapter extends RecyclerView.Adapter<BloquesAdapter.BloquesViewHolder> {

    public interface ListenerClick {
        void clickItemBoque(Bloque bloque, int posicion);
    }

    private ArrayList<Bloque> bloques;
    private Context contexto;
    private ListenerClick listener;

    public BloquesAdapter(ArrayList<Bloque> bloques, Context contexto, ListenerClick listener) {
        this.bloques = bloques;
        this.contexto = contexto;
        this.listener = listener;
    }

    public class BloquesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView img;
        private final TextView txtNombre, txtCodigo, txtCantSalones;

        public BloquesViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_img_bloque);
            txtNombre = itemView.findViewById(R.id.item_nombre_bloque);
            txtCodigo = itemView.findViewById(R.id.item_codigo_bloque);
            txtCantSalones = itemView.findViewById(R.id.item_cant_salones_bloque);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (listener != null ) {

                switch ( view.getId() ) {
                    case R.id.item_bloque:
                        listener.clickItemBoque( bloques.get( getAdapterPosition() ), getAdapterPosition() );
                        break;
                }
            }
        }
    }

    @NonNull
    @Override
    public BloquesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from( viewGroup.getContext() )
                .inflate(R.layout.item_bloque, viewGroup, false);

        return new BloquesViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull BloquesViewHolder holder, int pos) {
        Bloque bloque = bloques.get(pos);

        String urlImg = bloque.getUrlImagenes().size() > 0 ? bloque.getUrlImagenes().get(0) : "";

        GlideApp.with(contexto)
                .load( urlImg )
                .into( holder.img );

        holder.txtNombre.setText( bloque.getNombre() );
        holder.txtCantSalones.setText( "Bloque N° " + bloque.getCodigo() );
        holder.txtCodigo.setText( bloque.getCantSalones() + " Salones" );
    }

    @Override
    public int getItemCount() {
        return bloques.size();
    }
}
