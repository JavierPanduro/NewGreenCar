package com.example.migel.proyectointegrador2dambueno;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by migel on 19/02/2017.
 */

public class Viaje implements Parcelable {

    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private String precio;
    //private boolean fumador;
    private int plazasDisponible;
    private int plazasReservada;
    private String userId;
    private String key;
    private String uid;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Viaje() {

    }

    public Viaje(String destino, String userId, String uid, String precio, int plazasReservada, int plazasDisponible, String key, String hora, String fecha, String origen) {
        this.destino = destino;
        this.userId = userId;
        this.uid = uid;
        this.precio = precio;
        this.plazasReservada = plazasReservada;
        this.plazasDisponible = plazasDisponible;
        this.key = key;
        this.hora = hora;
        this.fecha = fecha;
        this.origen = origen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOrigen() { return origen;
    }

    public void setOrigen(String origen) {        this.origen = origen;
    }

    public String getDestino() {        return destino;
    }

    public void setDestino(String destino) {        this.destino = destino;
    }

    public String getPrecio() {        return precio;
    }

    public void setPrecio(String precio) {        this.precio = precio;
    }

    /*public boolean isFumador() {        return fumador;
    }

    public void setFumador(boolean fumador) {        this.fumador = fumador;
    }*/

    public int getPlazasDisponible() {
        return plazasDisponible;
    }

    public void setPlazasDisponible(int plazasDisponible) {
        this.plazasDisponible = plazasDisponible;
    }

    public int getPlazasReservada() {
        return plazasReservada;
    }

    public void setPlazasReservada(int plazasReservada) {
        this.plazasReservada = plazasReservada;
    }

    public String getUserId() {        return userId;
    }

    public void setUserId(String userId) {        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(origen);
        dest.writeString(destino);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(precio);
        dest.writeString(key);
        dest.writeInt(plazasDisponible);
        dest.writeInt(plazasReservada);
        dest.writeString(userId);
    }
    public Viaje(Parcel in) {
        origen = in.readString();
        destino = in.readString();
        fecha = in.readString();
        hora = in.readString();
        precio = in.readString();
        key = in.readString();
        plazasDisponible = in.readInt();
        plazasReservada = in.readInt();
        userId = in.readString();
    }
    public static final Creator<Viaje> CREATOR = new Creator<Viaje>() {
        @Override
        public Viaje createFromParcel(Parcel in) {
            return new Viaje(in);
        }
        @Override
        public Viaje[] newArray(int size) {
            return new Viaje[size];
        }
    };
}
