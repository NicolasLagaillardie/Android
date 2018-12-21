package com.example.lightbulb;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomContextManagementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout mainDrawerLayout;
    private Toolbar toolbar;

    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mainDrawerLayout = findViewById(R.id.drawer_layout);

        // FOR ROOMS -------------------------------------------------------------------------------

        final Spinner spinnerRoom = (Spinner) findViewById(R.id.rooms_spinner);

        roomContextHttpManager.retrieveAllRooms();

        ((Button) findViewById(R.id.buttonSwitchRoomLights)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String room = spinnerRoom.getSelectedItem().toString();
                roomContextHttpManager.switchLightRoom(room);
            }
        });
        ((Button) findViewById(R.id.buttonDeleteRoom)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String room = spinnerRoom.getSelectedItem().toString();
                roomContextHttpManager.deleteRoom(room);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            LinearLayout mainLayout = (LinearLayout) findViewById(android.R.id.home);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);

        } else if (id == R.id.nav_lights) {

            LinearLayout mainLayout = (LinearLayout) findViewById(android.R.id.home);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_main, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);

        } else if (id == R.id.nav_rooms) {

            LinearLayout mainLayout = (LinearLayout) findViewById(android.R.id.home);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.rooms_layout, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);

        } else if (id == R.id.nav_buildings) {

            LinearLayout mainLayout = (LinearLayout) findViewById(android.R.id.home);
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.buildings_layout, null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String room = parent.getItemAtPosition(pos).toString();
        roomContextHttpManager.retrieveLightContextState(room);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onUpdateRoom(RoomContextState context) {

        ((TextView) findViewById(R.id.textViewNameValue)).setText(String.valueOf(context.getName()));
        ((TextView) findViewById(R.id.textViewFloorValue)).setText(String.valueOf(context.getFloor()));
        ((TextView) findViewById(R.id.textViewBuildingIdValue)).setText(String.valueOf(context.getBuildingId()));

    }

    public void onUpdateRoomList(List listRooms) {

        Spinner spinner = (Spinner) findViewById(R.id.rooms_spinner);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listRooms
        );

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

    }

    public void onDeleteRoom() {

        ((TextView) findViewById(R.id.textViewNameValue)).setText("");
        ((TextView) findViewById(R.id.textViewFloorValue)).setText("");
        ((TextView) findViewById(R.id.textViewBuildingIdValue)).setText("");

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);

        roomContextHttpManager.retrieveAllRooms();

    }

}