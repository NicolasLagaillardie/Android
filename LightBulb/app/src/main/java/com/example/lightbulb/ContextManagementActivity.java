package com.example.lightbulb;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContextManagementActivity extends Activity {

    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String light = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                lightContextHttpManager.retrieveLightContextState(light);
            }
        });
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

}