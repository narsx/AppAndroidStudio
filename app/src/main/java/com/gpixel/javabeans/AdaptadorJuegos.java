package com.gpixel.javabeans;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpixel.Login.JuegoActivity;
import com.gpixel.R;

import java.util.ArrayList;

public class AdaptadorJuegos extends RecyclerView.Adapter<AdaptadorJuegos.JuegosViewHolder> implements View.OnClickListener {






    /*--------------------------------   ATRIBUTOS   ------------------------------------------*/
    private ArrayList<Juego> datos;
    private View.OnClickListener listener;

    /*--------------------------------    CONSTRUCTOR  ------------------------------------------*/

    public AdaptadorJuegos(ArrayList<Juego> datos) {
        this.datos = datos;
    }



    /*--------------------------------   METODOS ADAPTER  -----------------------------------------*/
    @NonNull
    @Override
    public JuegosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.juego_item, viewGroup, false);
        v.setOnClickListener(this);
        JuegosViewHolder cvh = new JuegosViewHolder(v);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JuegosViewHolder juegoViewHolder, int i) {
        juegoViewHolder.bindJuego(datos.get(i));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    /*--------------------------------   METODOS LISTENER -----------------------------------------*/
    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    /*--------------------------------   CLASE INTERNA   ------------------------------------------*/

    /**
     *
     */
    public static class JuegosViewHolder extends RecyclerView.ViewHolder {
        /*-------------------------------   ATRIBUTOS   ------------------------------------------*/
        private TextView tvTitulo;
        private ImageView ivIcono;


        /*-------------------------------    CONSTRUCTOR  ----------------------------------------*/
        public JuegosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvNombre);
            ivIcono=itemView.findViewById(R.id.imgJuego);
        }
        /*----------------------------------    METODOS   ----------------------------------------*/
        public void bindJuego(Juego jg) {
            tvTitulo.setText(jg.getNombre());
            new JuegoActivity.DownloadImageTask(ivIcono)
                    .execute(jg.getimagen().getIcono());
        }


    }

}
