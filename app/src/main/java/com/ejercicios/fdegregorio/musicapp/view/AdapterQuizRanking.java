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
import com.ejercicios.fdegregorio.musicapp.model.POJO.Quiz;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuizRanking extends   RecyclerView.Adapter<AdapterQuizRanking.ItemViewHolder>{

    private List<Quiz> listaRanking;

    public AdapterQuizRanking (){
        this.listaRanking = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_ranking,parent,false);
        AdapterQuizRanking.ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Quiz quiz = listaRanking.get(position);
        holder.bindItem(quiz);

    }

    @Override
    public int getItemCount() {
        return listaRanking.size();
    }

    public void setRanking(List<Quiz> list){
        this.listaRanking = list;
        notifyDataSetChanged();

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombreUsuario;
        private TextView puntajeUsuario;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreUsuario=itemView.findViewById(R.id.nombreJugador);
            puntajeUsuario = itemView.findViewById(R.id.puntajeJugador);



        }
        public void  bindItem (Quiz quiz){


            textViewNombreUsuario.setText(quiz.getNombreUsuario());
            puntajeUsuario.setText(quiz.getPuntaje().toString());
        }
    }
}
