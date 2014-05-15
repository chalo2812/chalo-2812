package com.example.agenda;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;

public class InsertarActivity extends Activity {
    
    Editable nombre;
    TextView txtNombre;
    TextView txtTelefono1;
    TextView txtTelefono2;
    TextView txtCorreo;
    TextView txtDireccion;
    TextView txtCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	nombre = (Editable) findViewById(R.id.nombre);
	txtNombre = (TextView) findViewById(R.id.txtNombre);
	txtTelefono1 = (TextView) findViewById(R.id.txtTelefono1);
	txtTelefono2 = (TextView) findViewById(R.id.txtTelefono2);
	//txtNombre = (TextView) findViewById(R.id.txtNombre);
	//txtNombre = (TextView) findViewById(R.id.txtNombre);
	//txtNombre = (TextView) findViewById(R.id.txtNombre);
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_insertar);
	

	
	
    }


    
}
