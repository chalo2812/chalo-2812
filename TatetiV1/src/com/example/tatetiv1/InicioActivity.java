package com.example.tatetiv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class InicioActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio);
        Button salida = (Button) findViewById(R.id.buttonSalida);
        Button salidaPcVsPc = (Button) findViewById(R.id.buttonPcVsPc);
        Button salidaPersonaVsPC = (Button) findViewById(R.id.buttonPvC);
        Button salidaPersonaVsPersona = (Button) findViewById(R.id.buttonPvP);
        
        salida.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		    	finish();
			}
        });
        salidaPcVsPc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent view = new Intent();
				startActivity(new Intent(view));
			}
        });
        salidaPersonaVsPC.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			
			}
        });
        salidaPersonaVsPersona.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			
			}
        });
        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void salir(){

    }
}
