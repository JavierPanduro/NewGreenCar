package com.example.migel.proyectointegrador2dambueno;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrar extends AppCompatActivity {

    Button registrar;
    EditText etRegEmail, etRegContra;
    TextView titulo,eslogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        registrar = (Button) findViewById(R.id.registrar);
        etRegEmail = (EditText) findViewById(R.id.etRegEmail);
        etRegContra = (EditText) findViewById(R.id.etRegContra);
        titulo=(TextView)findViewById(R.id.titulo);
        eslogan=(TextView)findViewById(R.id.eslogan);


        Typeface face = Typeface.createFromAsset(getAssets(),"font/University.otf");
        titulo.setTypeface(face);
        Typeface face2 = Typeface.createFromAsset(getAssets(),"font/Legend M54.ttf");
        eslogan.setTypeface(face2);

}



public void registrarReg(View v) {
    if (etRegEmail.getText().toString().equals("") || etRegContra.getText().toString().equals("")) {
        Toast toast = Toast.makeText(Registrar.this, R.string.rellenocampos, Toast.LENGTH_SHORT);
        toast.show();

    } else if (etRegContra.getText().toString().trim().length() < 8) {
        Toast toast = Toast.makeText(Registrar.this, R.string.caracterescontra, Toast.LENGTH_LONG);
        toast.show();
    } else {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(etRegEmail.getText().toString(), etRegContra.getText().toString())
                .addOnCompleteListener(Registrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Registrar.this, R.string.registrok,
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registrar.this, Login.class));
                            finish();
                        } else {

                            Toast.makeText(Registrar.this, R.string.registro_ko,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

}
