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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by migel on 18/01/2017.
 */

public class Buscar_viajes extends Fragment {
    public static final String TAG = Buscar_viajes.class.getSimpleName();
    public static final String VIAJE = "Viaje";

    ListView listView;
    ArrayList<Viaje> Viajes;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebase.getReference("Viajes");
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference("Viajes");
    Viaje travel;
    FirebaseUser user = MainActivity.loggedUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buscar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);
        Viajes = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> childrenIterator = dataSnapshot.getChildren().iterator();
                while (childrenIterator.hasNext()) {
                    travel = childrenIterator.next().getValue(Viaje.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(valueEventListener);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Viaje travel = dataSnapshot.getValue(Viaje.class);
                if (!travel.getUserId().equals(user.getUid()) && (travel.getPlazasDisponible())>0) {
                    Viajes.add(travel);

                    ViajeAdapter travelAdapter = new ViajeAdapter(getContext(), Viajes);
                    listView.setAdapter(travelAdapter);

                    Log.d(TAG, " onChildAdded: " + dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Viaje travel = dataSnapshot.getValue(Viaje.class);
                if (!travel.getUserId().equals(user.getUid()) && (travel.getPlazasDisponible())>0) {
                    for(int x=0;x<Viajes.size();x++) {
                        Viajes.remove(x);
                        Viajes.add(travel);
                        ViajeAdapter adapter = new ViajeAdapter(getContext(), Viajes);
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, " onChildRemoved: " + dataSnapshot.getKey());
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, " onChildMoved: " + dataSnapshot.getKey());
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, " postComments:onCancelled ", databaseError.toException());
                /*Toast.makeText(getContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();*/
            }
        };
        databaseReference.addChildEventListener(childEventListener);
        ViajeAdapter travelAdapter=new ViajeAdapter(getContext(), Viajes);
        listView.setAdapter(travelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ReservarActivity.class);
                intent.putExtra(VIAJE,  Viajes.get(position));
                startActivity(intent);
            }
        });
    }

}
