package com.example.migel.proyectointegrador2dambueno;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;



public class Login extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener =  new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null && user.getEmail() != null) {
                FirebaseAuth.getInstance().removeAuthStateListener(authListener);
                Log.d("Login", "Sesion iniciada");
                Toast toast = Toast.makeText(Login.this, R.string.sesioninic, Toast.LENGTH_LONG);
                toast.show();
                MainActivity.loggedUser = user;
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Log.d("Login", "Sesion cerrada");
            }
        }
    };
    Button btnEntrar;
    EditText etEmail, etContraseña;
    TextView registrar, titulo,subtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        registrar = (TextView) findViewById(R.id.registrar);
        etEmail = (EditText) findViewById(R.id.etRegEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        titulo = (TextView) findViewById(R.id.titulo);
        subtitulo = (TextView) findViewById(R.id.subtitulo);

        Typeface face = Typeface.createFromAsset(getAssets(),"font/University.otf");
        titulo.setTypeface(face);
        Typeface face2 = Typeface.createFromAsset(getAssets(),"font/Legend M54.ttf");
        subtitulo.setTypeface(face2);

        FirebaseAuth.getInstance().addAuthStateListener(authListener);
    }

    public void registrar(View v){
        Intent intent = new Intent(Login.this, Registrar.class);
        startActivity(intent);
        //finish();
    }

    public void entrar (View v){

        if (etEmail.getText().toString().equals("") || etContraseña.getText().toString().equals("")){
            Toast toast = Toast.makeText(this, R.string.rellenocampos, Toast.LENGTH_SHORT);
            toast.show();
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.getText().toString(), etContraseña.getText().toString())
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, R.string.sesionFireKO,
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                //Correcto, entrara en el listener de usuario autenticado
                            }
                        }
                    });

        }
    }
}

