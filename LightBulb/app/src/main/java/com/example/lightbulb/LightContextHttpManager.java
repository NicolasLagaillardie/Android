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

public class LightContextHttpManager {

    private MainContextManagementActivity MainContextManagementActivity;

    public LightContextHttpManager(MainContextManagementActivity MainContextManagementActivity) {
        this.MainContextManagementActivity = MainContextManagementActivity;
    }

    private void warningMessage(VolleyError error) {

        CharSequence text = error.toString();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(MainContextManagementActivity, text, duration * 10);
        toast.show();
    }

    public void checkValue(String text) {

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(MainContextManagementActivity, text, duration * 10);
        toast.show();
    }

    public void retrieveAllLights() {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/lights/";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonArrayRequest contextRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            List listLights = new ArrayList();

                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject rec = response.getJSONObject(i);
                                int id = Integer.parseInt(rec.get("id").toString());
                                listLights.add(id);
                            }

                            MainContextManagementActivity.onUpdateLightList(listLights);

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

    public void retrieveLightContextState(final String light) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/lights/" + light;

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String status = response.get("status").toString();
                            int level = Integer.parseInt(response.get("level").toString());
                            int roomId = Integer.parseInt(response.get("roomId").toString());

                            MainContextManagementActivity.onUpdateLight(new LightContextState(id, status, level, roomId));

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

    public void switchLight(final String light) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/lights/" + light + "/switch";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String status = response.get("status").toString();
                            int level = Integer.parseInt(response.get("level").toString());
                            int roomId = Integer.parseInt(response.get("roomId").toString());

                            MainContextManagementActivity.onUpdateLight(new LightContextState(id, status, level, roomId));

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

    public void deleteLight(final String light) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/lights/" + light;

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        StringRequest contextRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        MainContextManagementActivity.onDeleteLight();
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

    public void addLight(final JSONObject light) {

        String url = "https://faircorp-paul-breugnot.cleverapps.io/api/lights/";

        RequestQueue queue = Volley.newRequestQueue(MainContextManagementActivity);

        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.POST, url, light, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.get("id").toString());
                            String status = response.get("status").toString();
                            int level = Integer.parseInt(response.get("level").toString());
                            int roomId = Integer.parseInt(response.get("roomId").toString());

                            MainContextManagementActivity.onUpdateLight(new LightContextState(id, status, level, roomId));

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
