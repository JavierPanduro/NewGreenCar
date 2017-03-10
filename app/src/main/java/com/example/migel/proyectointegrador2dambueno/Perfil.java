package com.example.migel.proyectointegrador2dambueno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {
    private final String TAG = Mis_viajes.class.getSimpleName();
    private ListView list2;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private ChildEventListener childEvent, childEvent2;
    private Viaje viaje;
    private Query query, query2;
    public static final String LIST = "list";
    FirebaseUser user = MainActivity.loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        list2 = (ListView) findViewById(R.id.listUsuario);
        query2 = database.getReference("Reservas");


        childEvent2 = new ChildEventListener() {
            ViajeAdapter adapter;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                //for(int x=0;x<viajes.size();x++) {
                //viajes.remove(x);
                if (viaje.getUserId().equals(user.getUid())) {
                    viajes.add(viaje);
                    adapter = new ViajeAdapter(getBaseContext(), viajes);
                    list2.setAdapter(adapter);
                    //}
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                for (int x = 0; x < viajes.size(); x++) {
                    viajes.remove(x);
                    if (viaje.getUserId().equals(user.getUid())) {
                        viajes.add(viaje);
                        adapter = new ViajeAdapter(getBaseContext(), viajes);
                        list2.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                viaje = dataSnapshot.getValue(Viaje.class);
                for (int x = 0; x < viajes.size(); x++) {
                    viajes.remove(x);
                    adapter = new ViajeAdapter(getBaseContext(), viajes);
                    list2.setAdapter(adapter);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getBaseContext(), "Failed to load Viaje.", Toast.LENGTH_SHORT).show();
            }
        };
        query2.addChildEventListener(childEvent2);
        ViajeAdapter adapter = new ViajeAdapter(getBaseContext(), viajes);
        list2.setAdapter(adapter);
    }
}
