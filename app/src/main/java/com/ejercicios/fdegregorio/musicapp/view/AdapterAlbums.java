package com.ejercicios.fdegregorio.musicapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterAlbums extends RecyclerView.Adapter<AdapterAlbums.ItemViewHolder> {

    private List<Album> albums;
    private ListenerAdapterItems listenerAdapterItems;

    public AdapterAlbums(ListenerAdapterItems listenerAdapterItems){

        this.albums = new ArrayList<>();
        this.listenerAdapterItems = listenerAdapterItems;
    }

    public void setAlbums(List<Album> albums){
        this.albums = albums;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_detalle_albums, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Album album = albums.get(i);
        itemViewHolder.bindItem(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageViewImagenAlbum;

        private TextView textViewNombreAlbum;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewImagenAlbum = itemView.findViewById(R.id.imgAlbumDetalleCelda);

            textViewNombreAlbum = itemView.findViewById(R.id.txtAlbumDetalleNombre);



            //Agrego el handler del click en la celda (toda la celda)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Album album = albums.get(getAdapterPosition());
                    listenerAdapterItems.albumSeleccionado(album);
                }
            });
        }

        public void bindItem(Album album){

            Picasso.get()
                    .load(album.getImagen())
                    .placeholder(R.drawable.music_placeholder)
                    .into(imageViewImagenAlbum);
            textViewNombreAlbum.setText(album.getNombre());



        }
    }
    public void setResult(List<Album> albums) {

        this.albums = albums;
        notifyDataSetChanged();
    }

    public interface ListenerAdapterItems {

        public void albumSeleccionado(Album albumSeleccionado);
    }
}
