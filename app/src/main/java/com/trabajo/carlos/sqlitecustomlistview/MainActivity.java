package com.trabajo.carlos.sqlitecustomlistview;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.trabajo.carlos.sqlitecustomlistview.bbdd.DBAdapter;
import com.trabajo.carlos.sqlitecustomlistview.datos.Persona;
import com.trabajo.carlos.sqlitecustomlistview.lista.CustomAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lsvLista;
    private EditText nameEditText;
    private Button btnSave, btnRecoger;
    private ArrayList<Persona> personas = new ArrayList<>();
    private CustomAdapter adapter;
    final Boolean forUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lsvLista = (ListView) findViewById(R.id.lsvLista);
        adapter = new CustomAdapter(this,personas);

        this.getPersonas();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(false);
            }
        });
    }

    private void displayDialog(Boolean forUpdate)
    {

        Dialog d = new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);

        nameEditText = (EditText) d.findViewById(R.id.edtName);
        btnSave = (Button) d.findViewById(R.id.btnSave);
        btnRecoger = (Button) d.findViewById(R.id.btnRecoger);

        if(!forUpdate)
        {

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save(nameEditText.getText().toString());
                }
            });

            btnRecoger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPersonas();
                }
            });

        }else {

            //SET SELECTED TEXT
            nameEditText.setText(adapter.getSelectedItemName());

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(nameEditText.getText().toString());
                }
            });

            btnRecoger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPersonas();
                }
            });
        }

        d.show();

    }

    //SAVE
    private void save(String name)
    {

        DBAdapter db = new DBAdapter(this);
        db.openDB();

        boolean saved = db.add(name);

        if(saved)
        {

            nameEditText.setText("");
            getPersonas();

        }else {

            Toast.makeText(this, "No se puede guardar", Toast.LENGTH_SHORT).show();

        }

    }

    private void getPersonas()
    {

        personas.clear();
        DBAdapter db = new DBAdapter(this);
        db.openDB();

        Cursor c = db.retrieve();
        Persona persona = null;

        while (c.moveToNext())
        {

            int id = c.getInt(0);
            String name = c.getString(1);

            persona = new Persona();
            persona.setId(id);
            persona.setName(name);

            personas.add(persona);

        }

        db.closeDB();
        lsvLista.setAdapter(adapter);

    }

    //UPDATE OR EDIT
    private void update(String newName)
    {
        //GET id persona
        int id = adapter.getSelectedItemID();

        //UPDATE IN DB
        DBAdapter db = new DBAdapter(this);
        db.openDB();

        boolean updated = db.update(newName,id);
        db.closeDB();

        if(updated)
        {

            nameEditText.setText(newName);
            getPersonas();

        }else {

            Toast.makeText(this, "No se puede actualizar", Toast.LENGTH_SHORT).show();

        }

    }

    private void delete()
    {
        //GET ID
        int id = adapter.getSelectedItemID();

        //DELETE FROM DB
        DBAdapter db = new DBAdapter(this);
        db.openDB();

        boolean deleted = db.delete(id);
        db.closeDB();

        if(deleted)
        {

            getPersonas();

        }else {

            Toast.makeText(this, "No se puede borrar", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        CharSequence title = item.getTitle();
        if(title == "NEW")
        {

            displayDialog(!forUpdate);


        }else  if(title == "EDIT")
        {

            displayDialog(forUpdate);


        }else  if(title == "DELETE")
        {

            delete();

        }

        return super.onContextItemSelected(item);

    }

}
