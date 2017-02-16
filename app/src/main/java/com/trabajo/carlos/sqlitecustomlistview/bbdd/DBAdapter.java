package com.trabajo.carlos.sqlitecustomlistview.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Carlos Prieto on 16/02/2017.
 */

public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    //Abrir conexion
    public void openDB()
    {
        try
        {

            db = helper.getWritableDatabase();

        }catch (SQLException e)
        {

        }
    }
    //Cerrar DB
    public void closeDB()
    {
        try
        {

            helper.close();

        }catch (SQLException e)
        {

        }
    }

    //Insertar
    public boolean add(String name)
    {
        try
        {

            ContentValues cv = new ContentValues();
            cv.put(Constants.ROW_NAME, name);

            long result = db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    //Select
    public Cursor retrieve()
    {
        String[] columns = {Constants.ROW_ID, Constants.ROW_NAME};

        Cursor c = db.query(Constants.TB_NAME, columns, null, null, null, null, null);
        return c;
    }

    //Editar
    public boolean update(String newName,int id)
    {
        try
        {

            ContentValues cv = new ContentValues();
            cv.put(Constants.ROW_NAME, newName);


            int result = db.update(Constants.TB_NAME, cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});

            if(result > 0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;

    }

    //Eliminar
    public boolean delete(int id)
    {
        try
        {

            int result = db.delete(Constants.TB_NAME, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});

            if(result > 0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

}
