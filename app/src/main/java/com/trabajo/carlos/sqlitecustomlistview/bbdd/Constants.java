package com.trabajo.carlos.sqlitecustomlistview.bbdd;

/**
 * Created by Carlos Prieto on 16/02/2017.
 */

public class Constants {

    //Columnas
    static final String ROW_ID = "id";
    static final String ROW_NAME = "name";

    //Propiedades
    static final String DB_NAME = "nombre.db";
    static final String TB_NAME = "nombre";
    static final int DB_VERSION = 1;

    //Crear tabla
    static final String CREATE_TB = "CREATE TABLE " + TB_NAME + " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROW_NAME + " TEXT NOT NULL);";

    //Borrar tabla
    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;

}
