package com.gpixel.DescubrirActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpixel.Contactenos.ActivityNosotros;
import com.gpixel.Login.InicioActivity;
import com.gpixel.Login.JuegoActivity;
import com.gpixel.Login.LoginActivity;
import com.gpixel.Perfil.PerfilActivity;
import com.gpixel.R;

public class Descubrir extends AppCompatActivity {

    Toolbar toolbar;
    private FirebaseAuth auth;
    Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        ;
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(Descubrir.this, InicioActivity.class));
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(Descubrir.this, Descubrir.class));
                    finish();
                    return true;
                case R.id.navigation_profile:
                    startActivity(new Intent(Descubrir.this, PerfilActivity.class));
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubrir);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        auth = FirebaseAuth.getInstance();
        BottomNavigationView buttomNavigationView=findViewById(R.id.nav_view);
        menu=buttomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
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
        if(id==R.id.atras){
            startActivity(new Intent(Descubrir.this, InicioActivity.class));
            return true;
        }if (id == R.id.android) {

            final AlertDialog.Builder alert = new AlertDialog.Builder(Descubrir.this);
            alert.setTitle("Sign out");
            alert.setMessage("Are you sure you want to sign out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Descubrir.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    auth.signOut();

                    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) {
                            }
                        }
                    };
                    startActivity(new Intent(Descubrir.this, LoginActivity.class));
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
            startActivity(new Intent(Descubrir.this, ActivityNosotros.class));
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    String filtroPs4 = "146";
    String filtroPs3 = "35";
    String filtroXbox1 = "145";
    String filtroXbox360 = "20";
    String filtroPc = "95";
    String filtro3ds = "96";
    String filtroWiiU = "139";
    String filtroSwitch = "157";
    public void ps4(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroPs4);
        startActivity(i);

    }
    public void x1(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroXbox1);
        startActivity(i);
    }
    public void Nswitch(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroSwitch);
        startActivity(i);
    }
    public void N3ds(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtro3ds);
        startActivity(i);
    }
    public void ps3(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroPs3);
        startActivity(i);

    }
    public void x360(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroXbox360);
        startActivity(i);
    }
    public void pc(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroPc);
        startActivity(i);


    }
    public void WiiU(View v){
        Intent i = new Intent(Descubrir.this, InicioActivity.class);
        i.putExtra("plataforma",filtroWiiU);
        startActivity(i);
    }



}
