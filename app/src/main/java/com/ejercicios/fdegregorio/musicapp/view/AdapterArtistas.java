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

public class AdapterArtistas extends RecyclerView.Adapter<AdapterArtistas.ItemViewHolder> {

    private List<Artist> artistas;
    private ListenerAdapterArtistas listenerAdapterArtistas;

    public AdapterArtistas (ListenerAdapterArtistas listenerAdapterItems){
        this.artistas= new ArrayList<>();
        this.listenerAdapterArtistas = listenerAdapterItems;
    }
    public void setArtistas (List<Artist> artistas){
        this.artistas = artistas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_artista,viewGroup,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Artist artista = artistas.get(i);
        itemViewHolder.bindItem(artista);

    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombreArtista;
        private ImageView imageViewArtista;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreArtista=itemView.findViewById(R.id.nombre_artista);
            imageViewArtista = itemView.findViewById(R.id.fotoArtista);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artist artista= artistas.get(getAdapterPosition());
                    listenerAdapterArtistas.artistaSeleccionado(artista);
                }
            });

        }
        public void  bindItem (Artist artista){

            Picasso.get()
                    .load(artista.getImagen())
                    .placeholder(R.drawable.music_placeholder)
                    .into(imageViewArtista);

            textViewNombreArtista.setText(artista.getNombre());
        }
    }


    public interface ListenerAdapterArtistas {
        public void artistaSeleccionado(Artist artistaSeleccionado);
    }

    public Boolean estaVacia(){
        return artistas.isEmpty();
    }
}
