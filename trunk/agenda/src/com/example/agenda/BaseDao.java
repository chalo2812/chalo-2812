package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDao extends SQLiteOpenHelper {

    public BaseDao(Context context, String name, CursorFactory factory, int version) {
	// TODO: por default es super(context, name, factory, version);
	super(context, name, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
	String crearTabla = "CREATE TABLE contacto (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
			"nombre VARCHAR(100) NOT NULL," +
			"telefono1 VARCHAR(50),  " +
			"telefono2 VARCHAR(50),  "+
			"correo VARCHAR(100),  " +
			"direccion VARCHAR(200),  " +
			"celular VARCHAR(50))";
	db.execSQL(crearTabla);
    }
    

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("drop table if exist contactos");
    }
    
    public boolean insertarContacto(ContactoDTO contacto){
	boolean finalizo = false;
	ContentValues datos = new ContentValues();
	try {
	    datos.put("id", contacto.getId());
	    datos.put("nombre", contacto.getNombre());
	    datos.put("telefono1", contacto.getTelefono1());
	    datos.put("telefono2", contacto.getTelefono2());
	    datos.put("correo", contacto.getCorreo());
	    datos.put("direccion", contacto.getDireccion());
	    datos.put("celular", contacto.getCeluar());
	    this.getWritableDatabase().insert("contacto", "id,nombre,telefono1,telefono2,correo,direccion,celular", datos);
	} catch (Exception e) {

	}
	
	return finalizo;
    }

}
