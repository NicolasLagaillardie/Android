package com.example.lightbulb;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ContextManagementActivity extends Activity {

    private DrawerLayout mDrawerLayout;

    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        final Spinner spinner = (Spinner) findViewById(R.id.lights_spinner);

        lightContextHttpManager.retrieveAllLights();

        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
/*                String light = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();*/
                String light = spinner.getSelectedItem().toString();
                lightContextHttpManager.retrieveLightContextState(light);
            }
        });

        ((Button) findViewById(R.id.buttonSwitchLight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String light = spinner.getSelectedItem().toString();
                lightContextHttpManager.switchLight(light);
            }
        });
        ((Button) findViewById(R.id.buttonDelete)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String light = spinner.getSelectedItem().toString();
                lightContextHttpManager.deleteLight(light);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onUpdate(LightContextState context){

        ((TextView) findViewById(R.id.textViewLightValue)).setText(String.valueOf(context.getLevel()));
        ((TextView) findViewById(R.id.textViewRoomIdValue)).setText(String.valueOf(context.getRoomId()));

        if(context.getStatus().equals("ON")){
            ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_on);
        } else {
            ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);
        }

    }

    public void onUpdateList(List listLights){

        Spinner spinner = (Spinner) findViewById(R.id.lights_spinner);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listLights
        );

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

    }

    public void onDelete(){

        ((TextView) findViewById(R.id.textViewLightValue)).setText("");
        ((TextView) findViewById(R.id.textViewRoomIdValue)).setText("");

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);

    }

}