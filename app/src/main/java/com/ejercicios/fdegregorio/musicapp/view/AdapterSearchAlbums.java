package com.ejercicios.fdegregorio.musicapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerAlbumClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearchAlbums extends RecyclerView.Adapter<AdapterSearchAlbums.ItemViewHolder> {

    private List<Album> albumSearch;
    private ListenerAdapterSearchAlbum listenerAdapterSearchAlbum;

    public AdapterSearchAlbums(ListenerAdapterSearchAlbum listenerAdapterSearchAlbum) {
        this.albumSearch = new ArrayList<>();
        this.listenerAdapterSearchAlbum = listenerAdapterSearchAlbum;
    }

    @NonNull
    @Override
    public AdapterSearchAlbums.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_search_album, viewGroup, false);
        AdapterSearchAlbums.ItemViewHolder itemViewHolder = new AdapterSearchAlbums.ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchAlbums.ItemViewHolder itemViewHolder, int i) {

        Album album = albumSearch.get(i);
        itemViewHolder.bindItem(album);
    }

    @Override
    public int getItemCount() {
        return albumSearch.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAlbumSearch;
        private TextView txtAlbumSearchNombre;
        private TextView txtAlbumSearchArtista;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAlbumSearchNombre = itemView.findViewById(R.id.txtAlbumSearchNombre);
            txtAlbumSearchArtista = itemView.findViewById(R.id.txtAlbumSearchArtista);
            imgAlbumSearch = itemView.findViewById(R.id.imgAlbumSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = albumSearch.get(getAdapterPosition());
                    listenerAdapterSearchAlbum.albumSeleccionado(album);
                }
            });
        }

        public void bindItem(Album album) {

            txtAlbumSearchNombre.setText(album.getNombre());
            txtAlbumSearchArtista.setText(album.getArtist().getNombre());

            Picasso.get()
                    .load(album.getImagen())
                    .placeholder(R.drawable.music_placeholder)
                    .into(imgAlbumSearch);
        }
    }

    public void setResult(List<Album> albums) {

        this.albumSearch = albums;
        notifyDataSetChanged();
    }
    public interface ListenerAdapterSearchAlbum{
        void albumSeleccionado(Album album);
    }

}
