package com.trabajo.carlos.sqlitecustomlistview.lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.trabajo.carlos.sqlitecustomlistview.R;
import com.trabajo.carlos.sqlitecustomlistview.datos.Persona;

import java.util.ArrayList;

/**
 * Created by Carlos Prieto on 16/02/2017.
 */

public class CustomAdapter extends BaseAdapter{

    Context c;
    ArrayList<Persona> personas;
    LayoutInflater inflater;
    Persona persona;

    public CustomAdapter(Context c, ArrayList<Persona> personas) {
        this.c = c;
        this.personas = personas;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Object getItem(int position) {
        return personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null)
        {

            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        if(convertView == null)
        {

            convertView=inflater.inflate(R.layout.fila_lista, parent, false);

        }

        //BIND DATA
        MyViewHolder holder = new MyViewHolder(convertView);
        holder.nameTxt.setText(personas.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, personas.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {

                persona = (Persona) getItem(position);

            }
        });

        return convertView;
    }

    //EXPOSE NAME AND ID
    public int getSelectedItemID()
    {
        return persona.getId();
    }
    public String getSelectedItemName()
    {
        return persona.getName();
    }

}
