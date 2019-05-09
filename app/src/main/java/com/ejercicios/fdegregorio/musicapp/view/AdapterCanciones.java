package com.ejercicios.fdegregorio.musicapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCanciones extends RecyclerView.Adapter<AdapterCanciones.ItemViewHolder> {
    private List<Track> cancionesArtista;
    private ListenerAdapterCanciones listenerAdapterCanciones;
    public AdapterCanciones(ListenerAdapterCanciones listenerAdapterCanciones){

        this.cancionesArtista = new ArrayList<>();
        this.listenerAdapterCanciones = listenerAdapterCanciones;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_detalle_canciones,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Track cancionArtista = cancionesArtista.get(position);
        holder.bindItem(cancionArtista);

    }

    @Override
    public int getItemCount() {
        return cancionesArtista.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        private TextView txtCancionNombre;
        private TextView txtAlbumNombre;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCancionNombre = itemView.findViewById(R.id.txtCancionDetalleNombre);
            txtAlbumNombre = itemView.findViewById(R.id.txtCancionDetalleAlbum);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Track track = cancionesArtista.get(getAdapterPosition());
                    listenerAdapterCanciones.reproducirCancion(track);
                }
            });
        }

        public void bindItem(Track cancion) {

            txtCancionNombre.setText(cancion.getNombre());

            if (cancion.getAlbum() != null) {
                txtAlbumNombre.setText(cancion.getAlbum().getNombre());
            }else {
                txtAlbumNombre.setText(cancion.getNombreAlbum());
            }
        }
    }
    public void setResult(List<Track> canciones){

        this.cancionesArtista = canciones;
        notifyDataSetChanged();
    }
    public interface ListenerAdapterCanciones{
      void  reproducirCancion(Track cancionSeleccionada);
    }
}
