package com.example.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Button botonInsertar = (Button) findViewById(R.id.buttonInsertar);
	botonInsertar.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent i = new Intent(v.getContext(), InsertarActivity.class);
		startActivity(i);
	    }
	});
	Button botonMostrar = (Button) findViewById(R.id.buttonMostar);
	botonMostrar.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent i = new Intent(v.getContext(), MostrarActivity.class);
		startActivity(i);
	    }
	});
	Button botonEliminar = (Button) findViewById(R.id.botonEliminar);
	botonEliminar.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent i = new Intent(v.getContext(), EliminarActivity.class);
		startActivity(i);
	    }
	});
    }

}
