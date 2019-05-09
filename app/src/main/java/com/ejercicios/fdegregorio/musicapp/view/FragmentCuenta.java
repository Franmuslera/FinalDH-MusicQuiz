package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class FragmentCuenta extends Fragment {

    private ImageView imageViewPerfilCuenta;
    private TextView textViewNombreCuenta;
    private Button btnLogout;
    private ListenerFragmentCuenta listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);
        imageViewPerfilCuenta = view.findViewById(R.id.imagenPerfilCuenta);
        textViewNombreCuenta = view.findViewById(R.id.nombrePerfilCuenta);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textViewNombreCuenta.setText(user.getDisplayName());
        Picasso.get().load(user.getPhotoUrl()).into(imageViewPerfilCuenta);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.informarLogout();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListenerFragmentCuenta)context;
    }

    public interface ListenerFragmentCuenta {

        public void informarLogout();
    }

}
