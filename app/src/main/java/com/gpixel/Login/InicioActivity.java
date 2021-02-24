package com.gpixel.Login;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpixel.Contactenos.ActivityNosotros;
import com.gpixel.DescubrirActivity.Descubrir;
import com.gpixel.Perfil.PerfilActivity;
import com.gpixel.R;
import com.gpixel.Retrofit.APIRestService;
import com.gpixel.Retrofit.RetrofitClient;
import com.gpixel.Usuario.Aplicacion;
import com.gpixel.Usuario.UsuarioPojo;
import com.gpixel.javabeans.AdaptadorJuegos;
import com.gpixel.javabeans.Genero;
import com.gpixel.javabeans.Imagen;
import com.gpixel.javabeans.Juego;
import com.gpixel.javabeans.Prueba;
import com.gpixel.javabeans.Prueba2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InicioActivity extends AppCompatActivity {


    BottomNavigationView navView;


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(InicioActivity.this, InicioActivity.class));
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(InicioActivity.this, Descubrir.class));
                    finish();
                    return true;
                case R.id.navigation_profile:
                    startActivity(new Intent(InicioActivity.this, PerfilActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
    private RecyclerView rvMain;
    private AdaptadorJuegos adaptador;
    private LinearLayoutManager llm;
    String id;
    Menu menu;

    private ArrayList<Juego> datos;
    private ArrayList<String> datos2;
    private ArrayList<Juego> datos3;
    Juego juego;
    ChildEventListener cel;

    Juego juego1;
    Imagen imag;
    Toolbar toolbar;

    DatabaseReference dbR;

    Aplicacion app;
    private FirebaseAuth auth;

    MenuItem menuItem;
    String pepe;
    SearchView searchView;
    ArrayList<Genero> genero;
    ArrayList<UsuarioPojo>alUs;


    UsuarioPojo us;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.rvComentarios);
        datos = new ArrayList<Juego>();
        adaptador = new AdaptadorJuegos(datos);
        llm = new LinearLayoutManager(this);
        datos2 = new ArrayList<String>();

        alUs=new ArrayList<>();
        dbR = FirebaseDatabase.getInstance().getReference().child("Perfil");


        navView = findViewById(R.id.nav_view);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setAdapter(adaptador);
        rvMain.setLayoutManager(llm);
        datos3 = new ArrayList<Juego>();
        app = (Aplicacion) getApplicationContext();
        auth = FirebaseAuth.getInstance();
        String plataforma=getIntent().getStringExtra("plataforma");

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView buttomNavigationView = findViewById(R.id.nav_view);

        menu = buttomNavigationView.getMenu();
        if(plataforma!=null){
            filtrarPlataforma(plataforma);
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);

        }
        else {
            consumirWS();
            MenuItem menuItem = menu.getItem(0);
            menuItem.setChecked(true);

        }
/*
        BottomNavigationView buttomNavigationView = findViewById(R.id.nav_view);

        menu = buttomNavigationView.getMenu();*/
        //MenuItem menuItem = menu.getItem(0);
        //menuItem.setChecked(true);
        Toolbar();
        addChildEventListener2();
        app.setAlUs(alUs);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.atras) {
            auth.signOut();


            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {

                    }
                }
            };
            startActivity(new Intent(InicioActivity.this, LoginActivity.class));
            finish();
        }
        if (id == R.id.android) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(InicioActivity.this);
            alert.setTitle("Sign out");
            alert.setMessage("Are you sure you want to sign out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(InicioActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    auth.signOut();

                    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) {
                            }
                        }
                    };
                    startActivity(new Intent(InicioActivity.this, LoginActivity.class));
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
        if (id == R.id.search) {
            return true;
        }
        if (id == R.id.about) {
            startActivity(new Intent(InicioActivity.this, ActivityNosotros.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void Toolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void consumirWS() {
        if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService ars = r.create(APIRestService.class);
            Call<Prueba> call = ars.obtenerPrueba(ars.Key, ars.format, ars.field_list, ars.filter, ars.order);

            call.enqueue(new Callback<Prueba>() {


                @Override
                public void onResponse(Call<Prueba> call, Response<Prueba> response) {
                    if (response.isSuccessful()) {
                        Prueba juego = response.body();
                        datos = juego.getResults();


                        adaptador = new AdaptadorJuegos(datos);
                        rvMain.setAdapter(adaptador);

                        //adaptador.notifyDataSetChanged();

                    } else {
                        System.out.print("Eres un inutil");
                    }
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Juego juego = datos.get(rvMain.getChildAdapterPosition(v));
                            //Juego jg = consumirWS2(juego);

                            app.setJuego(juego);


                            Intent i = new Intent(InicioActivity.this, JuegoActivity.class);


                            startActivity(i);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Prueba> call, Throwable t) {
                    System.out.print("Eres un inutil");

                }
            });
        }
    }


    public ArrayList<Juego> filtrarNombre(String nombre) {


        if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService ars = r.create(APIRestService.class);

            Call<Prueba> call = ars.filtrarNombre(ars.Key, ars.format, ars.field_list, ars.filtro + nombre, ars.order);

            call.enqueue(new Callback<Prueba>() {

                @Override
                public void onResponse(Call<Prueba> call, Response<Prueba> response) {
                    if (!response.isSuccessful()) {
                        Log.i("Resultado: ", "Error" + response.code());
                    } else {
                        Prueba prueba = response.body();
                        datos = prueba.getResults();
                        adaptador = new AdaptadorJuegos(datos);
                        rvMain.setAdapter(adaptador);


                    }
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Juego juego = datos.get(rvMain.getChildAdapterPosition(v));

                            app.setJuego(juego);


                            Intent i = new Intent(InicioActivity.this, JuegoActivity.class);


                            startActivity(i);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Prueba> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });
        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
        }
        return datos;
    }

    public void filtrarPlataforma(String plataforma) {

        if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService ars = r.create(APIRestService.class);
            String filtroPs4 = "146";
            String filtroPs3 = "";
            String filtroXbox1 = "145";
            String filtroXbox360 = "";
            String filtroPc = "95";
            String filtro3ds = "96";
            String filtroWiiU = "";
            String filtroSwitch = "157";

            Call<Prueba> call = ars.obtenerPrueba(ars.Key, ars.format, ars.field_list, ars.filtroplat+plataforma, ars.order);

            call.enqueue(new Callback<Prueba>() {


                @Override
                public void onResponse(Call<Prueba> call, Response<Prueba> response) {
                    if (response.isSuccessful()) {
                        Prueba juego = response.body();
                        datos = juego.getResults();
                        adaptador = new AdaptadorJuegos(datos);
                        rvMain.setAdapter(adaptador);

                        adaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(InicioActivity.this, ActivityRegistrar.class);
                            }
                        });


                        adaptador.notifyDataSetChanged();

                        //adaptador.notifyDataSetChanged();

                    } else {
                        System.out.print("Eres un inutil");
                    }
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                      /*      Juego juego=datos.get(rvMain.getChildAdapterPosition(v));
                            ArrayList<Genero>genero=consumirWS2(juego);
                            juego.setGenero(genero);
                            */
                            Juego juego = datos.get(rvMain.getChildAdapterPosition(v));

                            app.setJuego(juego);


                            Intent i = new Intent(InicioActivity.this, JuegoActivity.class);


                            startActivity(i);
                        }
                    });
                    addChildEventListener2();
                }

                @Override
                public void onFailure(Call<Prueba> call, Throwable t) {
                    System.out.print("Eres un inutil");

                }
            });
        }
    }


    public boolean isNetworkAvailable() {
        boolean isAvailable = false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager) getSystemService(LoginActivity.CONNECTIVITY_SERVICE);
        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datos3 = datos;
                } else {
                    ArrayList<Juego> filteredList = new ArrayList<>();
                    filteredList = filtrarNombre(charString);
                    //contactListFiltered=datosf
                    //contactList=datos

                    datos3 = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = datos3;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                datos3 = (ArrayList<Juego>) filterResults.values;
                adaptador.notifyDataSetChanged();
            }
        };
    }

    public Juego consumirWS2(Juego jg) {

        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);
        String id = jg.getId();
        genero = new ArrayList<Genero>();
        Call<Prueba2> call = ars.GeneroJuego(id, ars.Key, ars.format, APIRestService.field_list_generp);

        call.enqueue(new Callback<Prueba2>() {
            @Override
            public void onResponse(Call<Prueba2> call, Response<Prueba2> response) {
                if (response.isSuccessful()) {
                    Prueba2 juego = response.body();
                    Juego juego1 = juego.getResults();

                    //adaptador.notifyDataSetChanged();

                } else {
                    System.out.print("Eres un inutil");
                }

            }

            @Override
            public void onFailure(Call<Prueba2> call, Throwable t) {
                System.out.print("Eres un inutil");

            }
        });


        return juego1;
    }

    private void addChildEventListener2() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    us = dataSnapshot.getValue(UsuarioPojo.class);
                    String email = us.getEmail();
                    System.out.print(email);
                    String emailActual=auth.getCurrentUser().getEmail();
                    System.out.println(emailActual);
                    alUs.add(us);
                    if (email.equals(auth.getCurrentUser().getEmail())) {
                        app.setUs(us);

                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    /*Coche c = dataSnapshot.getValue(Coche.class);
                    int pos = 0;
                    for(int i = 0; i < datos.size(); i++) {
                        if(datos.get(i).getMatricula().equals(c.getMatricula())) {        // se busca el mismo coche
                            datos.set(i, c);
                            pos = i;
                        }
                    }
                    adapter.notifyItemChanged(pos);*/
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    /*
                    Eliminar con dbR.child(?/matricula = AAA12345).removeValue
                    */
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            dbR.addChildEventListener(cel);
        }
    }
}