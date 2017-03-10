package com.example.migel.proyectointegrador2dambueno;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * Created by migel on 18/01/2017.
 */

public class Publicar_viaje extends Fragment {
    private DatabaseReference mDatabase;
    FloatingActionButton btnPublicar;
    EditText salida,precio;
    static EditText fecha;
    static EditText hora;
    //RadioButton fumadorSi, fumadorNo;
    //RadioGroup fumar;
    String fum;

    Spinner destino, plazas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publicar, container, false);
        return rootView;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnPublicar = (FloatingActionButton) view.findViewById(R.id.publicarViaje);
        salida = (EditText) view.findViewById(R.id.salida);
        destino = (Spinner) view.findViewById(R.id.destino);
        plazas = (Spinner) view.findViewById(R.id.plazasRes);
        //fumadorSi = (RadioButton) view.findViewById(R.id.rbsi);
        //fumadorNo = (RadioButton) view.findViewById(R.id.rbno);
        //fumar = (RadioGroup) view.findViewById(R.id.radiogroup);
        precio = (EditText) view.findViewById(R.id.etPrecio);
        //fum = (TextView)view.findViewById(R.id.fumador);
        fecha = (EditText) view.findViewById(R.id.txtFecha);
        fecha.setKeyListener(null);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateDialog().show(getFragmentManager(), "DatePickerInFull");
            }
        });
        hora = (EditText) view.findViewById(R.id.txtHora);
        hora.setKeyListener(null);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeDialog().show(getFragmentManager(), "TimePickerInFull");
            }
        });


        btnPublicar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (salida.getText().toString().equals("") || precio.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), R.string.rellenocampos, Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Publicando", "Publicando viaje...");
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    String origen = salida.getText().toString();
                    String destino1 = destino.getSelectedItem().toString();
                    String fecha1=fecha.getText().toString();
                    String hora1=hora.getText().toString();
                    String precio1 = precio.getText().toString();
                    /*if(fumadorSi.isChecked()){
                        fum = String.valueOf(R.string.si);
                    }else{
                        fum = String.valueOf(R.string.no);
                    }*/
                    int plaza = Integer.parseInt(plazas.getSelectedItem().toString());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String key = mDatabase.child("Viajes").push().getKey();
                    Viaje viaje = new Viaje();
                    viaje.setOrigen(origen);
                    viaje.setDestino(destino1);
                    viaje.setHora(hora1);
                    viaje.setFecha(fecha1);
                    viaje.setPrecio(precio1);
                    //viaje.setFumador(fum);
                    viaje.setPlazasDisponible(plaza);
                    viaje.setPlazasReservada(0);
                    viaje.setUserId(MainActivity.loggedUser.getUid());
                    viaje.setKey(key);
                    mDatabase.child("Viajes").child(key).setValue(viaje, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            progressDialog.hide();
                            if (databaseError == null && databaseReference != null) {
                                Toast.makeText(getContext(), "Viaje publicado correctamente", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Error al publicar viaje", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    salida.setText("");
                    destino.setOnItemSelectedListener(null);
                    fecha.setText("");
                    hora.setText("");
                    precio.setText("");
                    plazas.setOnItemSelectedListener(null);
                }
            }
        });
    }

        public static class DateDialog  extends DialogFragment implements DatePickerDialog.OnDateSetListener{
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Obtener fecha actual
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Retornar en nueva instancia del dialogo selector de fecha
                return new DatePickerDialog(getActivity(), this,year,month,day);
            }
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month < 10){
                    fecha.setText(dayOfMonth + "/0"+ (month+1) + "/"+ year);
                }else{
                    fecha.setText(dayOfMonth + "/"+ (month+1) + "/"+ year);
                }
            }


        }

        public static class TimeDialog extends DialogFragment  implements TimePickerDialog.OnTimeSetListener{
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Iniciar con el tiempo actual
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // Retornar en nueva instancia del dialogo selector de tiempo
                return new TimePickerDialog(getActivity(), this ,hour,minute, DateFormat.is24HourFormat(getActivity()));
            }
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                if(minute<10){
                    hora.setText(hour + ":0" + minute);
                }
                hora.setText(hour + ":" + minute);
            }
        }
    }


