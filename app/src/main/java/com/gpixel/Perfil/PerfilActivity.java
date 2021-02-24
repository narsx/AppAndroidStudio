package com.gpixel.Perfil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gpixel.Contactenos.ActivityNosotros;
import com.gpixel.DescubrirActivity.Descubrir;
import com.gpixel.Login.InicioActivity;
import com.gpixel.Login.JuegoActivity;
import com.gpixel.Login.LoginActivity;
import com.gpixel.R;
import com.gpixel.Usuario.Aplicacion;
import com.gpixel.Usuario.UsuarioPojo;
import com.gpixel.javabeans.Comentario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {
    Toolbar toolbar;
    private FirebaseAuth auth;
    UsuarioPojo us2;
    String nuevoNombre;
    private ImageView foto;
    private static final int PICK_IMAGE_REQUEST = 1;
    Menu menu;
    private Button btnCambiarContraseña, btndeleteUser,
            cambiarPs, quitar, registrarse;
    private TextView email;
    private EditText EmailViejo, password, newPassword, cambiarNombre;
    private ArrayList<Comentario>alcom;
    private ProgressBar progressBar;
    public static final int RC_PHOTO_ADJ = 2;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mFotoStorageRef;
    Uri uri;
    UsuarioPojo usuario;
    Aplicacion app;
    private DatabaseReference dbr;
    private DatabaseReference dbr2;

    UsuarioPojo us;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(PerfilActivity.this, Descubrir.class);
                    startActivity(intent2);
                    finish();
                    return true;
                case R.id.navigation_profile:
                    Intent intent3 = new Intent(PerfilActivity.this, PerfilActivity.class);
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
        setContentView(R.layout.activity_perfil);
        app = (Aplicacion) getApplication();
        us = app.getUs();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFotoStorageRef = mFirebaseStorage.getReference().child("fotos");
        cambiarNombre = findViewById(R.id.etCambiarNombre);
        foto = findViewById(R.id.fotoPerfil);


        alcom=new ArrayList<>();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });
        email = (TextView) findViewById(R.id.useremail);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {

                    startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        dbr = FirebaseDatabase.getInstance().getReference().child("Perfil");
        dbr2 = FirebaseDatabase.getInstance().getReference().child("Comentario");
        btnCambiarContraseña = (Button) findViewById(R.id.btnChangePwd);

        btndeleteUser = (Button) findViewById(R.id.btnDeleteUser);

        cambiarPs = (Button) findViewById(R.id.changePass);

        quitar = (Button) findViewById(R.id.remove);

        EmailViejo = (EditText) findViewById(R.id.old_email);

        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);

        EmailViejo.setVisibility(View.GONE);
        cambiarNombre.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);

        cambiarPs.setVisibility(View.GONE);

        quitar.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


        btnCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailViejo.setVisibility(View.GONE);

                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);

                cambiarPs.setVisibility(View.VISIBLE);

                quitar.setVisibility(View.GONE);
                cambiarNombre.setVisibility(View.GONE);

            }
        });

        cambiarPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Your password has to be 6 characters minimun!");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PerfilActivity.this, "Your password has been successfully changed!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(PerfilActivity.this, "Failed to change the password", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        btndeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PerfilActivity.this, "Your account has been deleted!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PerfilActivity.this, LoginActivity.class));

                                        if (user.getEmail().equals(us.getEmail())) {
                                            dbr.child(us.getIdusuario()).removeValue();
                                        }
                                        alcom=app.getAlcom();


                                        /*for(int i=0;i<alcom.size();i++){
                                            if(alcom.get(i).getUsuario().getIdusuario().equals(us.getIdusuario())){
                                                dbr2.child(alcom.get(i).getId()).removeValue();
                                            }
                                        }

                                           */
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(PerfilActivity.this, "Failed to delete your account", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });



        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        auth = FirebaseAuth.getInstance();

        BottomNavigationView buttomNavigationView = findViewById(R.id.nav_view);
        menu = buttomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            uri = data.getData();
            StorageReference fotoRef = mFotoStorageRef.child(uri.getLastPathSegment());
            UploadTask ut = fotoRef.putFile(uri);
            ut.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            UsuarioPojo fm = app.getUs();
                            String idUsuario=fm.getIdusuario();
                            String usuario=  fm.getUsuario();
                            dbr.child(idUsuario).removeValue();
                            UsuarioPojo fm2 = new UsuarioPojo(fm.getEmail(),fm.getUsuario(),fm.getIdusuario(),uri.toString());
                            dbr.child(idUsuario).setValue(fm2);

                            Map<String, Object> mapa = new HashMap<String, Object>();
                            mapa.put(idUsuario, fm2);
                            //   dbR.updateChildren(mapa);
                            Glide.with(foto.getContext())
                                    .load(fm2.getIdFoto())
                                    .into(foto);
                        }
                    });
                }
            });
            foto.setImageURI( uri );


        }


    }
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        email.setText(us.getUsuario());
        if(us.getIdFoto()!=null){
            new JuegoActivity.DownloadImageTask((ImageView) findViewById(R.id.fotoPerfil))
                    .execute(us.getIdFoto());
        }
    }

    public void signOut() {
        auth.signOut();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                startActivity(new Intent(PerfilActivity.this, InicioActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.atras) {
            startActivity(new Intent(PerfilActivity.this, InicioActivity.class));
            return true;
        }
        if (id == R.id.android) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(PerfilActivity.this);
            alert.setTitle("Sign out");
            alert.setMessage("Are you sure you want to sign out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PerfilActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    auth.signOut();

                    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) {
                            }
                        }
                    };
                    startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
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
            startActivity(new Intent(PerfilActivity.this, ActivityNosotros.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void CambiarNombre(View v) {
        cambiarNombre.setVisibility(View.VISIBLE);
        //cambiarPs.setVisibility(View.VISIBLE);
        quitar.setVisibility(View.VISIBLE);
        cambiarPs.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);


        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoNombre = cambiarNombre.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if (us != null) {

                    us2=new UsuarioPojo(us.getEmail(),nuevoNombre,us.getIdusuario(),us.getIdFoto());


                    dbr.child(us2.getIdusuario()).setValue(us2);
                    email.setText(nuevoNombre);

                    app.setUs(us2);


                cambiarNombre.setText("");
                }
            }
        });
    }

    public void enviarFoto(View v) {
        /*Foto msj = new Foto(null);
        String clave = dbR.push().getKey();
        dbR.child(clave).setValue(msj);
        */
        /*abrirá un selector de archivos para ayudarnos a elegir entre cualquier imagen JPEG almacenada localmente en el dispositivo */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "Complete la acción usando"), RC_PHOTO_ADJ);

    }
    private void AbrirGaleria() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
}