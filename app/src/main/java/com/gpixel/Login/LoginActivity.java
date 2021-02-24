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
import com.gpixel.R;


public class LoginActivity extends AppCompatActivity {
    private TextView tvTitle;
    private EditText Email, Password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnRegistrar, btnLogin,btnRecuperarPs;





    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, InicioActivity.class));
            finish();
        }

        setContentView(R.layout.loginactivity);
        tvTitle = findViewById(R.id.tvSign);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRegistrar = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRecuperarPs = findViewById(R.id.btn_reset_password);

        Typeface face = getResources().getFont(R.font.montserrat);
        Email.setTypeface(face);
        Password.setTypeface(face);
        btnRegistrar.setTypeface(face);
        btnLogin.setTypeface(face);
        btnRecuperarPs.setTypeface(face);
        btnRecuperarPs.getBackground().setAlpha(0);
        tvTitle.setTypeface(face);

        //FIREBASE
        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ActivityRegistrar.class));
            }
        });

        btnRecuperarPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Type an Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Type a password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //AUTENTIFICACION
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        Password.setError(getString(R.string.msj_no_registrado));
                                    }
                                    else{
                                        Password.setError("Wrong password. Try again!");
                                    }
                                } else {
                                    Intent i =new Intent(LoginActivity.this, InicioActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });
            }
        });

    }
    }



