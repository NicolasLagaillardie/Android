package com.example.lightbulb;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class MainContextManagementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mainDrawerLayout;
    private Toolbar toolbar;

    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);
    BuildingContextHttpManager buildingContextHttpManager= new BuildingContextHttpManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mainDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // FOR LIGHTS ------------------------------------------------------------------------------
        final Spinner spinnerLight = (Spinner) findViewById(R.id.lights_spinner);

        spinnerLight.setOnItemSelectedListener(this);

        lightContextHttpManager.retrieveAllLights();

        ((Button) findViewById(R.id.buttonSwitchLight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String light = spinnerLight.getSelectedItem().toString();
                lightContextHttpManager.switchLight(light);
            }
        });
        ((Button) findViewById(R.id.buttonDeleteLight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String light = spinnerLight.getSelectedItem().toString();
                lightContextHttpManager.deleteLight(light);
            }
        });

    }

    @Override
    public void onBackPressed() {
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainDrawerLayout = findViewById(R.id.drawer_layout);
                mainDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);

        if (id == R.id.nav_lights) {
            vf.setDisplayedChild(0);
            // FOR LIGHTS ------------------------------------------------------------------------------
            final Spinner spinnerLight = (Spinner) findViewById(R.id.lights_spinner);

            spinnerLight.setOnItemSelectedListener(this);

            lightContextHttpManager.retrieveAllLights();

            ((Button) findViewById(R.id.buttonSwitchLight)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String light = spinnerLight.getSelectedItem().toString();
                    lightContextHttpManager.switchLight(light);
                }
            });
            ((Button) findViewById(R.id.buttonDeleteLight)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String light = spinnerLight.getSelectedItem().toString();
                    lightContextHttpManager.deleteLight(light);
                }
            });
        } else if (id == R.id.nav_rooms) {
            vf.setDisplayedChild(1);
            // FOR ROOMS ------------------------------------------------------------------------------
            final Spinner spinnerRoom = (Spinner) findViewById(R.id.rooms_spinner);

            spinnerRoom.setOnItemSelectedListener(this);

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
        } else if (id == R.id.nav_buildings) {
            vf.setDisplayedChild(2);
            // FOR ROOMS ------------------------------------------------------------------------------
            final Spinner spinnerBuilding = (Spinner) findViewById(R.id.buildings_spinner);

            spinnerBuilding.setOnItemSelectedListener(this);

            buildingContextHttpManager.retrieveAllBuildings();

            ((Button) findViewById(R.id.buttonSwitchBuildingLights)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String room = spinnerBuilding.getSelectedItem().toString();
                    buildingContextHttpManager.switchLightBuilding(room);
                }
            });
            ((Button) findViewById(R.id.buttonDeleteBuilding)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String room = spinnerBuilding.getSelectedItem().toString();
                    buildingContextHttpManager.deleteBuilding(room);
                }
            });
        }

        mainDrawerLayout = findViewById(R.id.drawer_layout);
        mainDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // FOR LIGHTS ------------------------------------------------------------------------------
        String light = parent.getItemAtPosition(pos).toString();
        lightContextHttpManager.retrieveLightContextState(light);
        // FOR ROOMS ------------------------------------------------------------------------------
        String room = parent.getItemAtPosition(pos).toString();
        roomContextHttpManager.retrieveRoomContextState(room);
        // FOR BUILDINGS ------------------------------------------------------------------------------
        String building = parent.getItemAtPosition(pos).toString();
        buildingContextHttpManager.retrieveBuildingContextState(building);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onUpdateLight(LightContextState context) {

        ((TextView) findViewById(R.id.textViewLightValue)).setText(String.valueOf(context.getLevel()));
        ((TextView) findViewById(R.id.textViewRoomIdValue)).setText(String.valueOf(context.getRoomId()));

        if (context.getStatus().equals("ON")) {
            ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_on);
        } else {
            ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);
        }

    }

    public void onUpdateLightList(List listLights) {

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

    public void onDeleteLight() {

        ((TextView) findViewById(R.id.textViewLightValue)).setText("");
        ((TextView) findViewById(R.id.textViewRoomIdValue)).setText("");

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);

        lightContextHttpManager.retrieveAllLights();

    }

    public void onUpdateRoom(RoomContextState context) {

        ((TextView) findViewById(R.id.textViewNameRoomValue)).setText(String.valueOf(context.getName()));
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

        ((TextView) findViewById(R.id.textViewNameRoomValue)).setText("");
        ((TextView) findViewById(R.id.textViewFloorValue)).setText("");
        ((TextView) findViewById(R.id.textViewBuildingIdValue)).setText("");

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);

        roomContextHttpManager.retrieveAllRooms();

    }

    public void onUpdateBuilding(BuildingContextState context) {

        ((TextView) findViewById(R.id.textViewNameBuildingValue)).setText(String.valueOf(context.getName()));

    }

    public void onUpdateBuildingList(List listBuildings) {

        Spinner spinner = (Spinner) findViewById(R.id.buildings_spinner);

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listBuildings
        );

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

    }

    public void onDeleteBuilding() {

        ((TextView) findViewById(R.id.textViewNameBuildingValue)).setText("");

        ((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.ic_bulb_off);

        buildingContextHttpManager.retrieveAllBuildings();

    }

}