package com.ejercicios.fdegregorio.musicapp.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.ArtistController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Genero;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterGeneros extends RecyclerView.Adapter<AdapterGeneros.ItemViewHolder> {

    private List<Genero> generos;
    private ListenerAdapterGeneros listenerAdapterGeneros;
    private AdapterArtistas.ListenerAdapterArtistas listenerAdapterArtistas;

    public AdapterGeneros(ListenerAdapterGeneros listenerAdapterItems, AdapterArtistas.ListenerAdapterArtistas listenerAdapterArtistas) {
        this.generos = new ArrayList<>();
        this.listenerAdapterGeneros = listenerAdapterItems;
        this.listenerAdapterArtistas = listenerAdapterArtistas;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_genero,viewGroup,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);


        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Genero genero = generos.get(i);
        itemViewHolder.bindItem(genero);
    }

    @Override
    public int getItemCount() {
        return generos.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombreGenero;
        private RecyclerView recyclerViewArtistas;
        private AdapterArtistas adapterArtistas;
        private Boolean primeraVez = true;
        private Genero genero;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreGenero = itemView.findViewById(R.id.nombreGenero);
            recyclerViewArtistas = itemView.findViewById(R.id.recyclerArtistas);

        }


        public void bindItem(Genero genero) {

            //Hago el pedido de artistas cuando se carga el Genero
            if (primeraVez || !this.genero.equals(genero)) {
                this.genero = genero;
                textViewNombreGenero.setText(genero.getNombre());

                LinearLayoutManager layoutManagerArtista = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerViewArtistas.setLayoutManager(layoutManagerArtista);

                adapterArtistas = new AdapterArtistas(listenerAdapterArtistas);
                recyclerViewArtistas.setAdapter(adapterArtistas);

                ArtistController artistController = new ArtistController();
                artistController.getArtistsByGenre(genero.getId(), new ResultListener<List<Artist>>() {
                    @Override
                    public void finish(List<Artist> result) {
                        adapterArtistas.setArtistas(result);
                    }
                }, itemView.getContext());

                primeraVez = false;
            }
        }
    }

    public interface ListenerAdapterGeneros{
        public void generoSeleccionado(Genero generoSeleccionado);
    }




}

