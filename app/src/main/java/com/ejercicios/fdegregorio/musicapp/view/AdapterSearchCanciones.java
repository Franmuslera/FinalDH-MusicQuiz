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

public class AdapterSearchCanciones extends RecyclerView.Adapter<AdapterSearchCanciones.ItemViewHolder> {

    private List<Track> cancionesSearch;
    private ListenerAdapterSearchCanciones listenerSearchCanciones;

    public AdapterSearchCanciones(ListenerAdapterSearchCanciones listener) {

        this.cancionesSearch = new ArrayList<>();
        this.listenerSearchCanciones = listener;
    }

    @NonNull
    @Override
    public AdapterSearchCanciones.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_search_cancion,viewGroup,false);
        AdapterSearchCanciones.ItemViewHolder itemViewHolder = new AdapterSearchCanciones.ItemViewHolder(view);


        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchCanciones.ItemViewHolder itemViewHolder, int i) {

        Track cancion = cancionesSearch.get(i);
        itemViewHolder.bindItem(cancion);
    }

    @Override
    public int getItemCount() {
        return cancionesSearch.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSearchCancion;
        private TextView txtSearchCancionNombre;
        private TextView txtSearchCancionArtista;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSearchCancionNombre = itemView.findViewById(R.id.txtSearchCancionNombre);
            txtSearchCancionArtista = itemView.findViewById(R.id.txtSearchCancionArtista);
            imgSearchCancion = itemView.findViewById(R.id.imgSearchCancion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Track track = cancionesSearch.get(getAdapterPosition());
                    listenerSearchCanciones.reproducirCancion(track);
                }
            });
        }

        public void bindItem(Track cancion) {

            txtSearchCancionNombre.setText(cancion.getNombre());
            txtSearchCancionArtista.setText(cancion.getArtist().getNombre());

            Picasso.get()
                    .load(cancion.getAlbum().getImagen())
                    .placeholder(R.drawable.music_placeholder)
                    .into(imgSearchCancion);
        }
    }

    public void setResult(List<Track> canciones){

        this.cancionesSearch = canciones;
        notifyDataSetChanged();
    }

    public interface ListenerAdapterSearchCanciones {
        void reproducirCancion(Track cancionSeleccionada);
    }
}
