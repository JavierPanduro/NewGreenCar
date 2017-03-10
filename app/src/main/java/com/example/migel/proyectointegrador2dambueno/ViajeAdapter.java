package com.example.migel.proyectointegrador2dambueno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by migel on 19/02/2017.
 */

public class ViajeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Viaje> Viajes;

    public ViajeAdapter(Context context, ArrayList<Viaje> Viajes) {
        this.context = context;
        this.Viajes = Viajes;
    }

    @Override
    public int getCount() {
        return Viajes.size();
    }

    @Override
    public Object getItem(int position) {
        return Viajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itembuscarviaje,null);
            holder=new ViewHolder();
            holder.origen=(TextView) convertView.findViewById(R.id.txtOrigen);
            holder.destino=(TextView) convertView.findViewById(R.id.txtDestino);
            holder.fecha=(TextView) convertView.findViewById(R.id.txtFecha);
            holder.hora=(TextView) convertView.findViewById(R.id.txtHora);
            holder.precio=(TextView) convertView.findViewById(R.id.txtPrecio);
            holder.plaza = (TextView) convertView.findViewById(R.id.txtPlazas);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.origen.setText(" "+ Viajes.get(position).getOrigen());
        holder.destino.setText(" "+ Viajes.get(position).getDestino());
        holder.fecha.setText(" "+Viajes.get(position).getFecha());
        holder.hora.setText(" "+Viajes.get(position).getHora());
        holder.precio.setText(" "+ Viajes.get(position).getPrecio());
        holder.plaza.setText(" "+ Viajes.get(position).getPlazasDisponible());
        return convertView;
    }



    public static class ViewHolder{
        TextView origen;
        TextView destino;
        TextView fecha;
        TextView hora;
        TextView precio;
        TextView plaza;
    }
}
