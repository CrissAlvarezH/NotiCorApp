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

    private ArrayList<Bloque> bloques;
    private Context contexto;

    public BloquesAdapter(ArrayList<Bloque> bloques, Context contexto) {
        this.bloques = bloques;
        this.contexto = contexto;
    }

    public class BloquesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView txtNombre, txtCodigo, txtCantSalones;

        public BloquesViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_img_bloque);
            txtNombre = itemView.findViewById(R.id.item_nombre_bloque);
            txtCodigo = itemView.findViewById(R.id.item_codigo_bloque);
            txtCantSalones = itemView.findViewById(R.id.item_cant_salones_bloque);
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

        GlideApp.with(contexto)
                .load( bloque.getUrlImg() )
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
