package helpers.cristian.com.ubiety.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.modelos.Notificacion;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotifiacionViewHolder>{
    public interface ListenerNotificacion {
        void clickNotificacion(Notificacion notificacion, int posicion);
    }

    private ArrayList<Notificacion> notificaciones;
    private ListenerNotificacion miListener;

    public NotificacionAdapter(ArrayList<Notificacion> notificaciones, ListenerNotificacion miListener) {
        this.notificaciones = notificaciones;
        this.miListener = miListener;
    }

    public class NotifiacionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout layoutIcono;
        private ImageView imgIcono;
        private TextView txtTitulo, txtFecha, txtDescripcion;

        public NotifiacionViewHolder(View itemView) {
            super(itemView);

            layoutIcono = itemView.findViewById(R.id.noti_layout_icono);
            imgIcono = itemView.findViewById(R.id.noti_icono);
            txtTitulo = itemView.findViewById(R.id.noti_titulo);
            txtFecha = itemView.findViewById(R.id.noti_fecha);
            txtDescripcion = itemView.findViewById(R.id.noti_descripcion);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( miListener != null ){
                miListener.clickNotificacion( notificaciones.get( getAdapterPosition() ), getAdapterPosition() );
            }
        }
    }

    @NonNull
    @Override
    public NotifiacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.item_notificacion, parent, false);

        return new NotifiacionViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifiacionViewHolder holder, int position) {
        Notificacion notificacion = notificaciones.get(position);

        holder.txtTitulo.setText( notificacion.getTitulo() );
        holder.txtFecha.setText( notificacion.getFecha() );
        holder.txtDescripcion.setText( notificacion.getDescripcion() );

        int tipo = notificacion.getTipo();

        int iconoRecurso = R.drawable.ic_campana_blanco;
        int colorIcono = R.color.amarillo;

        switch (tipo) {
            case Notificacion.Tipos.NOTICIA:
                iconoRecurso = R.drawable.ic_noticia_blanco;
                colorIcono = R.color.azul;
                break;
            case Notificacion.Tipos.ADVERTENCIA:
                iconoRecurso = R.drawable.ic_campana_blanco;
                colorIcono = R.color.amarillo;
                break;
            case Notificacion.Tipos.PELIGRO:
                iconoRecurso = R.drawable.ic_peligro_blanco;
                colorIcono = R.color.rojo;
                break;
        }

        holder.layoutIcono.setBackgroundResource(colorIcono);
        holder.imgIcono.setImageResource(iconoRecurso);
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    public void agregarNotificacion(Notificacion notificacion){
        this.notificaciones.add(0, notificacion);
        // Se insertó en la ultima posicion
        notifyItemInserted( 0 );
    }

    public void setNotificaciones(ArrayList<Notificacion> notifiaciones){
        this.notificaciones = notifiaciones;
        notifyDataSetChanged();
    }
}