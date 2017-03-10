package com.example.migel.proyectointegrador2dambueno;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Modificar_Viaje extends AppCompatActivity {
    Button modificar, borrar;
    EditText salida,precio;
    static EditText fecha;
    static EditText hora;
    //RadioButton fumadorSi, fumadorNo;
    //RadioGroup fumar;
    String fum;
    Spinner destino, plazas;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Intent intent;
    Viaje travel;
    private DatabaseReference travelsData;
    public static final String TAG=MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__viaje);
        modificar = (Button) findViewById(R.id.btnModViaje);
        borrar = (Button)findViewById(R.id.btnBorrarViaje);
        salida = (EditText) findViewById(R.id.salida);
        destino = (Spinner) findViewById(R.id.destino);
        plazas = (Spinner) findViewById(R.id.plazasRes);
        //fumadorSi = (RadioButton) findViewById(R.id.rbsi);
        //fumadorNo = (RadioButton)findViewById(R.id.rbno);
        //fumar = (RadioGroup) findViewById(R.id.radiogroup);
        precio = (EditText)findViewById(R.id.etPrecio);
        fecha = (EditText) findViewById(R.id.txtFecha);
        fecha.setKeyListener(null);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Publicar_viaje.DateDialog().show(getFragmentManager(), "DatePickerInFull");
            }
        });
        hora = (EditText) findViewById(R.id.txtHora);
        hora.setKeyListener(null);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new Publicar_viaje.TimeDialog().show(getFragmentManager(), "TimePickerInFull");
            }
        });

        intent=getIntent();
        travel =intent.getParcelableExtra(Buscar_viajes.VIAJE);

        travelsData = database.getInstance().getReference("Viajes");
        salida.setText(travel.getOrigen());
        destino.setOnItemSelectedListener(null);
        fecha.setText(travel.getFecha());
        hora.setText(travel.getHora());
        precio.setText(travel.getPrecio());
        plazas.setOnItemSelectedListener(null);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarViaje();
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarViaje();
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

    public void borrarViaje(){
        String key2 = travel.getKey();
        database.getReference("Viajes").child(key2).setValue(null);
        Toast.makeText(this, R.string.borrar, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void actualizarViaje(){
        database.getReference("Viajes");
        String origen = salida.getText().toString();
        String destino1 = destino.getSelectedItem().toString();
        String fecha1=fecha.getText().toString();
        String hora1=hora.getText().toString();
        String precio1 = precio.getText().toString();
        int plaza = Integer.parseInt(plazas.getSelectedItem().toString());
        travel.setOrigen(origen);
        travel.setDestino(destino1);
        travel.setHora(hora1);
        travel.setFecha(fecha1);
        travel.setPrecio(precio1);
        //viaje.setFumador(fum);
        travel.setPlazasDisponible(plaza);
        String key = travel.getKey();
        database.getReference("Viajes").child(key).setValue(travel);
        Toast.makeText(this, R.string.modif, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
