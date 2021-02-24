package com.gpixel.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpixel.R;
import com.gpixel.Usuario.Aplicacion;
import com.gpixel.Usuario.UsuarioPojo;
import com.gpixel.javabeans.Usuario;


public class ActivityRegistrar extends AppCompatActivity {

    private TextView tvSign;
    private EditText inputEmail, inputPassword,inputUser;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference dbR;

    private String url;
    private String email;
    private String password;
    private String user;
    Aplicacion app;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        app = (Aplicacion) getApplication();

        auth = FirebaseAuth.getInstance();
        url = "https://firebasestorage.googleapis.com/v0/b/gpixel-c4928.appspot.com/o/fotos%2F38?alt=media&token=b01d6285-2823-4b2f-ac6f-366db35808ad";

        tvSign = findViewById(R.id.tvSign);
        btnSignUp =findViewById(R.id.btnCreate);
        inputEmail =  findViewById(R.id.etEmail);
        inputPassword =findViewById(R.id.etPass);
        inputUser=findViewById(R.id.etName);
        progressBar = findViewById(R.id.progresbarreg);
        dbR= FirebaseDatabase.getInstance().getReference().child("Perfil");

        Typeface face = getResources().getFont(R.font.montserrat);
        tvSign.setTypeface(face);
        btnSignUp.setTypeface(face);
        inputEmail.setTypeface(face);
        inputPassword.setTypeface(face);
        inputUser.setTypeface(face);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                user=inputUser.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Type your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Type your contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Your password is too short! 6 characters minimun!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(ActivityRegistrar.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(ActivityRegistrar.this, "Sign in completed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(ActivityRegistrar.this, "Failed Sign in." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    String id= dbR.push().getKey();
                                    String mail=auth.getCurrentUser().getEmail();

                                    UsuarioPojo usuarioPojo = new UsuarioPojo(mail,user,id, url);

                                    dbR.child(id).setValue(usuarioPojo);

                                    app.setIdusuario(id);
                                    app.setUsuario(mail);
                                    app.setUrlfoto("sample/avatars");

                                    Intent i=new Intent(ActivityRegistrar.this, InicioActivity.class);

                                    startActivity(i);

                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        }
    }


