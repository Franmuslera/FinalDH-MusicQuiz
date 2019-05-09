package com.ejercicios.fdegregorio.musicapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearchArtistas extends RecyclerView.Adapter<AdapterSearchArtistas.ItemViewHolder> {

    private List<Artist> artistasSearch;
    private ListenerAdapterSearchArtistas listenerAdapterSearchArtistas;

    public AdapterSearchArtistas(ListenerAdapterSearchArtistas listenerAdapterSearchArtistas) {
        this.artistasSearch = new ArrayList<>();
        this.listenerAdapterSearchArtistas= listenerAdapterSearchArtistas;
    }

    @NonNull
    @Override
    public AdapterSearchArtistas.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_search_artista,viewGroup,false);
        AdapterSearchArtistas.ItemViewHolder itemViewHolder = new AdapterSearchArtistas.ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchArtistas.ItemViewHolder itemViewHolder, int i) {

        Artist artista = artistasSearch.get(i);
        itemViewHolder.bindItem(artista);
    }

    @Override
    public int getItemCount() {
        return artistasSearch.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSearchArtista;
        private TextView txtSearchArtistaNombre;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSearchArtistaNombre = itemView.findViewById(R.id.txtArtistaSearchNombre);
            imgSearchArtista = itemView.findViewById(R.id.imgArtistaSearch);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artist artist = artistasSearch.get(getAdapterPosition());
                    listenerAdapterSearchArtistas.artistaSeleccionado(artist);
                }
            });
        }

        public void bindItem(Artist artista) {

            txtSearchArtistaNombre.setText(artista.getNombre());

            Picasso.get()
                    .load(artista.getImagen())
                    .placeholder(R.drawable.music_placeholder)
                    .into(imgSearchArtista);
        }
    }

    public void setResult(List<Artist> artistas){

        this.artistasSearch = artistas;
        notifyDataSetChanged();
    }
    public interface ListenerAdapterSearchArtistas {
        public void artistaSeleccionado(Artist artistaSeleccionado);
    }
}
