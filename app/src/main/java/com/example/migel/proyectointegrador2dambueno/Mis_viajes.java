package com.example.migel.proyectointegrador2dambueno;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import static com.example.migel.proyectointegrador2dambueno.Buscar_viajes.VIAJE;

/**
 * Created by migel on 18/01/2017.
 */

public class Mis_viajes extends Fragment {
    private final String TAG = Mis_viajes.class.getSimpleName();
    private ListView list2;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private ChildEventListener childEvent, childEvent2;
    private Viaje viaje;
    private Query query;
    public static final String LIST = "list";
    FirebaseUser user = MainActivity.loggedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mis_viajes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        list2 = (ListView) view.findViewById(R.id.listView);

        query = database.getReference("Viajes");

        childEvent = new ChildEventListener() {
            ViajeAdapter adapter;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                viaje = dataSnapshot.getValue(Viaje.class);
                if (viaje.getUserId().equals(user.getUid())) {
                    //for(int x=0;x<viajes.size();x++) {
                    //viajes.remove(x);
                    viajes.add(viaje);
                    adapter = new ViajeAdapter(getContext(), viajes);
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
                        adapter = new ViajeAdapter(getContext(), viajes);
                        list2.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                viaje = dataSnapshot.getValue(Viaje.class);
                for (int x = 0; x < viajes.size(); x++) {
                    viajes.remove(x);
                    adapter = new ViajeAdapter(getContext(), viajes);
                    list2.setAdapter(adapter);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load Viaje.", Toast.LENGTH_SHORT).show();
            }
        };
        query.addChildEventListener(childEvent);
        ViajeAdapter adapter = new ViajeAdapter(getContext(), viajes);
        list2.setAdapter(adapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Modificar_Viaje.class);
                intent.putExtra(VIAJE,  viajes.get(position));
                startActivity(intent);
            }
        });
    }
}
