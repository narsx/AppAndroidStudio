package com.gpixel.Contactenos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpixel.DescubrirActivity.Descubrir;
import com.gpixel.Login.InicioActivity;
import com.gpixel.Login.LoginActivity;
import com.gpixel.Perfil.PerfilActivity;
import com.gpixel.R;


public class ActivityNosotros extends AppCompatActivity {
    Toolbar toolbar;
    private FirebaseAuth auth;
    Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ActivityNosotros.this, InicioActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(ActivityNosotros.this, Descubrir.class);
                    startActivity(intent2);
                    finish();
                    return true;
                case R.id.navigation_profile:
                    Intent intent3 = new Intent(ActivityNosotros.this, PerfilActivity.class);
                    startActivity(intent3);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        auth = FirebaseAuth.getInstance();

        BottomNavigationView buttomNavigationView=findViewById(R.id.nav_view);
        menu=buttomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
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
            startActivity(new Intent(ActivityNosotros.this, InicioActivity.class));
            return true;
        }
        if (id == R.id.android) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(ActivityNosotros.this);
            alert.setTitle("Sign out");
            alert.setMessage("Are you sure you want to sign out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ActivityNosotros.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    auth.signOut();

                    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) {
                            }
                        }
                    };
                    startActivity(new Intent(ActivityNosotros.this, LoginActivity.class));
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



        return super.onOptionsItemSelected(item);
    }
}
