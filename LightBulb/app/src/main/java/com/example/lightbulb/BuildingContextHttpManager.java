package com.example.lightbulb;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuildingContextHttpManager {

    private MainContextManagementActivity MainContextManagementActivity;

    public BuildingContextHttpManager(MainContextManagementActivity MainContextManagementActivity) {
        this.MainContextManagementActivity = MainContextManagementActivity;
    }

    private void warningMessage(VolleyError error) {

        CharSequence text = error.toString();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(MainContextManagementActivity, text, duration * 10);
        toast.show();
    }

    public void retrieveAllBuildings() {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/buildings/";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            List listBuildings = new ArrayList();

                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject rec = response.getJSONObject(i);
                                int id = Integer.parseInt(rec.get("id").toString());
                                listBuildings.add(id);
                            }

                            MainContextManagementActivity.onUpdateBuildingList(listBuildings);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warningMessage(error);
                    }
                });
        queue.add(contextRequest);

    }

    public void retrieveBuildingContextState(final String building) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/buildings/" + building;

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String name = response.get("name").toString();

                            MainContextManagementActivity.onUpdateBuilding(new BuildingContextState(id, name));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warningMessage(error);
                    }
                });
        queue.add(contextRequest);

    }

    public void switchLightBuilding(final String building) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/rooms/" + building + "/switch";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String name = response.get("name").toString();
                            int floor = Integer.parseInt(response.get("floor").toString());
                            int buildingId = Integer.parseInt(response.get("buildingId").toString());

                            MainContextManagementActivity.onUpdateRoom(new RoomContextState(id, name, floor, buildingId));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warningMessage(error);
                    }
                });
        queue.add(contextRequest);

    }

    public void deleteBuilding(final String building) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/building/" + building;

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        StringRequest contextRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        MainContextManagementActivity.onDeleteRoom();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warningMessage(error);
                    }
                }
        );
        queue.add(contextRequest);

    }

    public void addBuilding(final JSONObject room) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/building/";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.POST, url, room, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String name = response.get("name").toString();

                            MainContextManagementActivity.onUpdateBuilding(new BuildingContextState(id, name));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warningMessage(error);
                    }
                });
        queue.add(contextRequest);

    }
}
