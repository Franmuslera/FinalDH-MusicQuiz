package com.ejercicios.fdegregorio.musicapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;

import java.util.ArrayList;
import java.util.List;

public class AdapterCancionesAlbum extends  RecyclerView.Adapter<AdapterCancionesAlbum.ItemViewHolder>{

    private List<Track> cancionesAlbum;
    private ListenerAdapterCancionesAlbum listenerAdapterCancionesAlbum;

    public AdapterCancionesAlbum(ListenerAdapterCancionesAlbum listenerAdapterCancionesAlbum) {
        this.cancionesAlbum = new ArrayList<>();
        this.listenerAdapterCancionesAlbum = listenerAdapterCancionesAlbum;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_canciones_album,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);



        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Track cancionAlbum = cancionesAlbum.get(position);
        holder.bindItem(cancionAlbum);

    }

    @Override
    public int getItemCount() {
        return cancionesAlbum.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNombreCancion;
        private TextView textViewNombreArtista;


        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewNombreCancion = itemView.findViewById(R.id.nombreCancionDetalle);
            textViewNombreArtista = itemView.findViewById(R.id.nombreArtistaAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = cancionesAlbum.get(getAdapterPosition());
                    listenerAdapterCancionesAlbum.reproducirTrack(track);
                }
            });
        }

        public void bindItem(Track track){
            textViewNombreCancion.setText(track.getNombre());

            if (track.getArtist() != null) {
                textViewNombreArtista.setText(track.getArtist().getNombre());
            }else {
                textViewNombreArtista.setText(track.getNombreArtist());
            }
        }
    }

    public void setResult(List<Track> canciones){
        this.cancionesAlbum = canciones;
        notifyDataSetChanged();

    }
    public interface ListenerAdapterCancionesAlbum{
         void reproducirTrack(Track cancionSeleccionada);
    }
}
