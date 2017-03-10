package com.example.migel.proyectointegrador2dambueno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservarActivity extends AppCompatActivity {
    TextView origen,destino,fecha,hora,plazas,precio,quitarPlaza;
    Button reservar;
    Intent intent;
    Viaje travel;
    private FirebaseDatabase travelsData = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        origen =(TextView) findViewById(R.id.origenRes);
        destino =(TextView) findViewById(R.id.destinoRes);
        fecha =(TextView) findViewById(R.id.fechaRes);
        hora =(TextView) findViewById(R.id.horaRes);
        //fumador =(TextView) findViewById(R.id.fumadorRes);
        plazas =(TextView) findViewById(R.id.plazasRes);
        precio =(TextView) findViewById(R.id.precioRes);
        reservar =(Button)findViewById(R.id.btnReservarRes);
        quitarPlaza = (EditText) findViewById(R.id.RestarViaje);

        intent=getIntent();
        travel =intent.getParcelableExtra(Buscar_viajes.VIAJE);



        origen.setText(" "+travel.getOrigen());
        destino.setText(" "+travel.getDestino());
        fecha.setText(" "+travel.getFecha());
        hora.setText(" "+travel.getHora());
        precio.setText(" "+travel.getPrecio());
        plazas.setText(" "+travel.getPlazasDisponible());

    }

    public void reservar (View v){
        String key = travel.getKey();
        Log.d(TAG, key);
        travel.setPlazasDisponible(travel.getPlazasDisponible() - Integer.parseInt(quitarPlaza.getText().toString().trim()));
        travelsData.getReference("Viajes").child(key).setValue(travel);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = travelsData.getInstance().getReference();
        String keyR = myRef.child("posts").push().getKey();
        travel.setPlazasReservada(Integer.parseInt(quitarPlaza.getText().toString()));
        travelsData.getReference("Reservas").child(keyR).setValue(travel);
        Toast.makeText(ReservarActivity.this, R.string.reservar_ok, Toast.LENGTH_LONG).show();
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
