package com.example.lightbulb;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LightContextHttpManager {

    private ContextManagementActivity contextManagementActivity;

    public LightContextHttpManager(ContextManagementActivity contextManagementActivity) {
        this.contextManagementActivity = contextManagementActivity;
    }

    public void retrieveLightContextState(final String light){

        String url =  "https://faircorp-paul-breugnot.cleverapps.io/api/lights/" + light;

        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String status = response.get("status").toString();
                            int level = Integer.parseInt(response.get("level").toString());
                            int roomId = Integer.parseInt(response.get("roomId").toString());

                            contextManagementActivity.onUpdate(new LightContextState(id, status, level, roomId));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);

    }

    public void switchLight(final String light){

        String url =  "https://faircorp-paul-breugnot.cleverapps.io/api/lights/" + light + "/switch";

        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String status = response.get("status").toString();
                            int level = Integer.parseInt(response.get("level").toString());
                            int roomId = Integer.parseInt(response.get("roomId").toString());

                            contextManagementActivity.onUpdate(new LightContextState(id, status, level, roomId));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);

    }
}
