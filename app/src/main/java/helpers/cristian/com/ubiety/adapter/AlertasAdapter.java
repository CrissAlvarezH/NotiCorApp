package helpers.cristian.com.ubiety.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import helpers.cristian.com.ubiety.R;
import helpers.cristian.com.ubiety.modelos.Alerta;
import helpers.cristian.com.ubiety.modelos.Noticia;
import helpers.cristian.com.ubiety.modelos.Notificacion;
import helpers.cristian.com.ubiety.utilidades.Constantes;

public class AlertasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface ListenerNotificacion {
        void clickNotificacion(Notificacion notificacion, int posicion);
    }

    private ArrayList<Alerta> alertas;
    private ListenerNotificacion miListener;
    private Context contexto;

    public AlertasAdapter(Context contexto, ArrayList<Alerta> alertas, ListenerNotificacion miListener) {
        this.alertas = alertas;
        this.miListener = miListener;
        this.contexto = contexto;
    }

    public class NotificacionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout layoutIcono;
        private ImageView imgIcono;
        private TextView txtTitulo, txtFecha, txtDescripcion;

        public NotificacionViewHolder(View itemView) {
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
                miListener.clickNotificacion( alertas.get( getAdapterPosition() ).getNotificacion(), getAdapterPosition() );
            }
        }
    }

    public class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView txtTitulo, txtDescripcion, txtCarrera;

        public NoticiaViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.alarma_noticia_img);
            txtTitulo = itemView.findViewById(R.id.alarma_noticia_titulo);
            txtDescripcion = itemView.findViewById(R.id.alarma_noticia_descripcion);
            txtCarrera = itemView.findViewById(R.id.alarma_noticia_carrera);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView txtCarrera;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.alarma_banner_img);
            txtCarrera = itemView.findViewById(R.id.alarma_banner_carrera);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.item_notificacion, parent, false);

        switch ( viewType ) {
            case Alerta.Motivos.NOTIFICACION:
                item = LayoutInflater.from( parent.getContext() )
                        .inflate(R.layout.item_notificacion, parent, false);

                return new NotificacionViewHolder(item);
            case Alerta.Motivos.NOTICIA:
                item = LayoutInflater.from( parent.getContext() )
                        .inflate(R.layout.item_alarma_noticia, parent, false);

                return new NoticiaViewHolder(item);
            case Alerta.Motivos.BANNER:
                item = LayoutInflater.from( parent.getContext() )
                        .inflate(R.layout.item_alarma_banner, parent, false);

                return new BannerViewHolder(item);
        }

        return new NotificacionViewHolder(item);
    }

    @Override
    public int getItemViewType(int position) {
        return alertas.get(position).getMotivo();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Alerta alerta = alertas.get(position);

        switch ( getItemViewType(position) ) {
            case Alerta.Motivos.NOTIFICACION:
                Notificacion notificacion = alerta.getNotificacion();

                NotificacionViewHolder holderNotificacion = (NotificacionViewHolder) holder;

                holderNotificacion.txtTitulo.setText( notificacion.getTitulo() );
                holderNotificacion.txtFecha.setText( notificacion.getFecha() );
                holderNotificacion.txtDescripcion.setText( notificacion.getDescripcion() );

                int tipo = notificacion.getTipo();

                int iconoRecurso = R.drawable.ic_campana_blanco;
                int colorIcono = R.color.amarillo;

                switch (tipo) {
                    case Notificacion.Tipos.MENSAJE:
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

                holderNotificacion.layoutIcono.setBackgroundResource(colorIcono);
                holderNotificacion.imgIcono.setImageResource(iconoRecurso);

                break;
            case Alerta.Motivos.NOTICIA:
                Noticia noticia = alerta.getNoticia();

                NoticiaViewHolder holderNoticias = (NoticiaViewHolder) holder;

                Glide.with(contexto)
                        .load( Constantes.URLs.IMAGEN_NOTICIA + noticia.getId() + ".jpg")
                        .into( holderNoticias.img );

                holderNoticias.txtTitulo.setText( noticia.getTitulo() );
                holderNoticias.txtDescripcion.setText( noticia.getDescripcion() );
                holderNoticias.txtCarrera.setText( noticia.getCarrera().getNombre() );

                break;
            case Alerta.Motivos.BANNER:
                Noticia banner = alerta.getBanner();

                BannerViewHolder holderBanner = (BannerViewHolder) holder;

                Glide.with(contexto)
                        .load(Constantes.URLs.IMAGEN_NOTICIA + banner.getId() + ".jpg")
                        .into( holderBanner.img );

                holderBanner.txtCarrera.setText( banner.getCarrera().getNombre() );

                break;
        }


    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    public void agregarAlarma(Alerta alerta){
        this.alertas.add(0, alerta);
        // Se insertó en la ultima posicion
        notifyItemInserted( 0 );
    }

    public void setAlertas(ArrayList<Alerta> alertas){
        this.alertas = alertas;
        notifyDataSetChanged();
    }
}