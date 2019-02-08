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
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.utilidades.Constantes;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    private ArrayList<Noticia> noticias;
    private Context context;

    public NoticiasAdapter(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView txtTitulo, txtDescripcion, txtEnlace;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_img_noticia);
            txtTitulo = itemView.findViewById(R.id.item_descripcion_noticia);
            txtDescripcion = itemView.findViewById(R.id.item_descripcion_noticia);
            txtEnlace = itemView.findViewById(R.id.item_enlace_noticia);
        }
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from( viewGroup.getContext() )
                .inflate(R.layout.item_noticia, viewGroup, false);

        return new NoticiasViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int pos) {
        Noticia noticia = noticias.get(pos);

        GlideApp.with(context)
                .load( Constantes.URLs.IMAGEN_NOTICIA + noticia.getId() + ".jpg" )
                .into( holder.img );

        holder.txtTitulo.setText( noticia.getTitulo() );
        holder.txtDescripcion.setText( noticia.getDescripcion() );

        if ( !noticia.getEnlace().trim().isEmpty() ) {
            holder.txtEnlace.setText( noticia.getEnlace() );
            holder.txtEnlace.setVisibility( View.VISIBLE );
        } else {
            holder.txtEnlace.setVisibility( View.GONE );
        }
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public void setNoticias(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
        notifyDataSetChanged();
    }
}
