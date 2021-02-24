package com.gpixel.javabeans;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpixel.Login.JuegoActivity;
import com.gpixel.R;
import com.gpixel.Usuario.Aplicacion;

import java.util.ArrayList;

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.ComentariosVH>implements View.OnClickListener {

    private ArrayList<Comentario> lista;
    private Context contexto;
    private DatabaseReference dbR;
    private Aplicacion app;
    private String id;
    private JuegoActivity ja;
    private View.OnClickListener listener;


    public AdaptadorComentarios(ArrayList<Comentario> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    /*                       MÉTODOS NECESARIOS                                    */
    @NonNull
    @Override
    public ComentariosVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comentarios, viewGroup, false);
        ComentariosVH cvh = new ComentariosVH(v, contexto);
        v.setOnClickListener(this);


        return cvh;
    }
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ComentariosVH comentariosVH,int i) {
        comentariosVH.bindComentario(lista.get(i));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void clear() {
        lista.clear();
    }

    /*-------------------------           CLASE INTERNA           -------------------------------*/
    public class ComentariosVH extends RecyclerView.ViewHolder {

        private Context context;

        private TextView tvMatricula;
        private ImageView iv;
        private TextView tvAnio;
        private ImageButton btn;
        private TextView tvId;



        public ComentariosVH(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvMatricula=itemView.findViewById(R.id.tvNombreUsuario);
            tvAnio=itemView.findViewById(R.id.tvComentario);
            iv=itemView.findViewById(R.id.fotoil);
            dbR = FirebaseDatabase.getInstance().getReference().child("Comentario");
            app=(Aplicacion)context.getApplicationContext();
            tvId=itemView.findViewById(R.id.tvID);

            btn=itemView.findViewById(R.id.BorrarCom);


        }

        public void bindComentario(final Comentario c) {
            tvMatricula.setText(c.getUsuario().getUsuario());
            tvAnio.setText(c.getTexto());
            tvId.setText(c.getId());
            new JuegoActivity.DownloadImageTask((ImageView) iv)
                    .execute(c.getUsuario().getIdFoto());
            if (c.getUsuario().getEmail().equals(app.getUs().getEmail())) {
                btn.setVisibility(View.VISIBLE);


            }
            else {
                btn.setVisibility(View.GONE);
            }


            btn.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
                    alert.setTitle("Delete comment");
                    alert.setMessage("Are you sure you want to delete your comment?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "You comment was deleted successfully!", Toast.LENGTH_SHORT).show();
                            dbR.child((String) tvId.getText()).removeValue();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.create().show();

                }
            });

            tvMatricula.setText(c.getUsuario().getUsuario());
            tvAnio.setText(c.getTexto());

        }
        }

    }


