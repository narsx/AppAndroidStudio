package com.gpixel.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.gpixel.Contactenos.ActivityNosotros;
import com.gpixel.DescubrirActivity.Descubrir;
import com.gpixel.Perfil.PerfilActivity;
import com.gpixel.R;
import com.gpixel.Retrofit.APIRestService;
import com.gpixel.Retrofit.RetrofitClient;
import com.gpixel.Usuario.Aplicacion;
import com.gpixel.Usuario.UsuarioPojo;
import com.gpixel.javabeans.AdaptadorComentarios;
import com.gpixel.javabeans.Comentario;
import com.gpixel.javabeans.Genero;
import com.gpixel.javabeans.Juego;
import com.gpixel.javabeans.Prueba2;
import com.gpixel.javabeans.plataformas;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JuegoActivity extends AppCompatActivity {
    TextView tvNombre;
    TextView tvDescripcion;
    UsuarioPojo us2;
    TextView tvFecha;
    TextView tvPlatafor;
    String total = "";
    String correoActual;
    Aplicacion app;
    Juego jg;
    private Bitmap loadedImage;
    TextView tvGenero;
    ArrayList<Genero> datos;
    String total2 = "";
    private FirebaseAuth auth;
    Query usuario;
    UsuarioPojo us;
    Juego juego1;
    RecyclerView rv;
    EditText etCom;
    private ChildEventListener cel;
    private ChildEventListener cel2;
    private AdaptadorComentarios adaptador;
    String url;
    BottomNavigationView navView;
    Toolbar menucillo;

    Comentario com;
    DatabaseReference dbR;
    DatabaseReference dbR2;
    private LinearLayoutManager manager;
    ArrayList<Comentario> Alcom;
    ArrayList<Comentario> Alcom2;
    int posicion = 0;
    Button btn;
    ArrayList<UsuarioPojo> al;


    Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        ;

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(JuegoActivity.this, InicioActivity.class));
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(JuegoActivity.this, Descubrir.class));
                    finish();
                    return true;
                case R.id.navigation_profile:
                    startActivity(new Intent(JuegoActivity.this, PerfilActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        tvNombre = findViewById(R.id.tvNombre);
        tvDescripcion = findViewById(R.id.tvDesc);
        tvFecha = findViewById(R.id.tvFecha);
        tvPlatafor = findViewById(R.id.tvPlataformas);
        tvGenero = findViewById(R.id.tvGenero);
        ArrayList<plataformas> datos2;

        auth = FirebaseAuth.getInstance();
        rv = findViewById(R.id.rvComentarios);
        etCom = findViewById(R.id.etComentarios);
        app = (Aplicacion) getApplicationContext();
        tvGenero = findViewById(R.id.tvGenero);
        btn = findViewById(R.id.button);
        jg = app.getJuego();
        us = app.getUs();

        Typeface face = getResources().getFont(R.font.montserrat);
        etCom.setTypeface(face);
        btn.setTypeface(face);

        datos2 = jg.getPlataformasAL();
        new DownloadImageTask((ImageView) findViewById(R.id.img))
                .execute(jg.getimagen().getImagen());       // downloadFile(url);

        menucillo = findViewById(R.id.toolbar);
        setSupportActionBar(menucillo);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvNombre.setText(jg.getNombre());
        tvDescripcion.setText(jg.getDescripcion());
        String fecha = jg.getFecha();
        if (fecha != null) {
            tvFecha.setText(fecha.substring(0, fecha.length() - 8));
        } else {
            tvFecha.setText("");
        }
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView buttomNavigationView = findViewById(R.id.nav_view);
        menu = buttomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
/*
        Typeface face = getResources().getFont(R.font.montserrat);
        tvNombre.setTypeface(face);
        tvDescripcion.setTypeface(face);
        tvFecha.setTypeface(face);
        tvPlatafor.setTypeface(face);
        tvGenero.setTypeface(face);
*/
        if (datos2 == null) {
            tvPlatafor.setText("");
        } else {
            for (int i = 0; i < datos2.size(); i++) {

                String prueba = datos2.get(i).getNombre();
                total = prueba + ", " + total;

            }
            tvPlatafor.setText(total.substring(0, total.length() - 2));
        }

        RellenarGenero();
        correoActual = auth.getCurrentUser().getEmail();


        Alcom = new ArrayList<Comentario>();
        Alcom2 = new ArrayList<Comentario>();
        adaptador = new AdaptadorComentarios(Alcom, JuegoActivity.this);
        manager = new LinearLayoutManager(this);

        rv.setAdapter(adaptador);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());

        dbR = FirebaseDatabase.getInstance().getReference().child("Comentario");


        addChildEventListener();
        app.setAlCom(Alcom2);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.atras) {
            startActivity(new Intent(JuegoActivity.this, InicioActivity.class));
            return true;
        }
        if (id == R.id.android) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(JuegoActivity.this);
            alert.setTitle("Sign out");
            alert.setMessage("Are you sure you want to sign out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(JuegoActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    auth.signOut();

                    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) {
                            }
                        }
                    };
                    startActivity(new Intent(JuegoActivity.this, LoginActivity.class));
                    finish();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();

            /* finish(); */
        }
        if (id == R.id.about) {
            startActivity(new Intent(JuegoActivity.this, ActivityNosotros.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void RellenarGenero() {
        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);


        Call<Prueba2> call = ars.GeneroJuego(jg.getId(), ars.Key, ars.format, ars.field_list_generp);

        call.enqueue(new Callback<Prueba2>() {
            @Override
            public void onResponse(Call<Prueba2> call, Response<Prueba2> response) {
                if (response.isSuccessful()) {
                    Prueba2 juego = response.body();
                    juego1 = juego.getResults();
                    datos = juego1.getGenero();
                    //adaptador.notifyDataSetChanged();
                    if (datos == null) {

                        tvGenero.setText("");

                    } else {
                        for (int i = 0; i < datos.size(); i++) {

                            String prueba2 = datos.get(i).getNombre();
                            total2 = prueba2 + ", " + total2;

                        }
                        tvGenero.setText(total2.substring(0, total2.length() - 2));

                    }


                } else {
                    System.out.print("Eres un inutil");
                }

            }

            @Override
            public void onFailure(Call<Prueba2> call, Throwable t) {
                System.out.print("Eres un inutil");

            }
        });

    }


    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }

    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    com = dataSnapshot.getValue(Comentario.class);
                    Alcom2.add(com);
                    al = app.getAlUs();
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getIdusuario().equals(com.getUsuario().getIdusuario())) {
                            com = new Comentario(al.get(i), com.getIdJuego(), com.getTexto(), com.getId());
                        }
                    }
                    if (com.getIdJuego().equals(jg.getId())) {

                        Alcom.add(com);
                    }


                    adaptador.notifyItemChanged(Alcom.size() - 1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    com = dataSnapshot.getValue(Comentario.class);

                    for (int i = 0; i < Alcom.size(); i++) {
                        if (com.getId().equals(Alcom.get(i).getId())) {
                            posicion = i;
                        } else {
                            //Alcom2.add(Alcom.get(i));
                        }

                    }
                    Alcom.remove(posicion);
                    rv.removeViewAt(posicion);
                    adaptador.notifyItemRemoved(posicion);
                    adaptador.notifyItemRangeChanged(posicion, Alcom.size());



                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            dbR.addChildEventListener(cel);
            //dbR2.addChildEventListener(cel);
        }
    }

    public void annadirComentario(View v) {
        String comentario = etCom.getText().toString();
        String correo = auth.getCurrentUser().getEmail();
        String id = dbR.push().getKey();


        com = new Comentario(us, jg.getId(), comentario, id);
        dbR.child(com.getId()).setValue(com);
        etCom.setText("");

    }

}