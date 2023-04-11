package com.example.wirelesscontrolofhomeappliance;  //package name

import android.os.Bundle;          //all the imported packages and modules
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {  //MainActivity class derived from AppCompatActivity

    //request URL and device name
    private final static String url = "https://cloud.boltiot.com/remote/7547fc00-a568-4cc8-8614-89128ed4fa39/";
    private final static String deviceName = "BOLT14885065";
    int state;    //variable storing the state of relay switch
    RequestQueue requestQueue;   //request queue from Volley
    private TextView textViewResponse;   //for responses to be displayed
    private Button button;  //switch button

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //onCreate method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   //initializing activity_main.xml layout
        textViewResponse = findViewById(R.id.textView5);
        button = findViewById(R.id.button);
        initialize();                   //call to method
        setButtonLabel();               //call to method
        String writeUrl = url + "digitalMultiWrite?deviceName=" + deviceName + "&pins=1,4&states=";
        button.setOnClickListener(new View.OnClickListener() { //actions to take when button is clicked
            @Override
            public void onClick(View view) {
                ProgressBar progressBar = findViewById(R.id.progressBar); //display circular loader
                button.setVisibility(View.INVISIBLE);                     //and hide button
                progressBar.setVisibility(View.VISIBLE);
                JsonObjectRequest jsonObjectRequest;
                if (state == 1){          //if relay on then switch off
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                            (writeUrl + "LOW,LOW"), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    textViewResponse.setText(response.toString());
                                    state = 0;
                                    setButtonLabel();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Could not connect!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });  //generating JSON object request
                } else {       //if relay is off then switch it on
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                            (writeUrl + "HIGH,HIGH"), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    textViewResponse.setText(response.toString());
                                    state = 1;
                                    setButtonLabel();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Internet Connection Error!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });           //generating JSON object request
                }
                requestQueue.add(jsonObjectRequest);      //sending the request
                new CountDownTimer(5000, 5000) {  //For showing
                    @Override                                              //the button again
                    public void onTick(long l) {}                          //after 5 seconds
                                                                           //and removing the
                    @Override                                             //circular loader
                    public void onFinish() {
                        progressBar.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });
    }

    private void initialize() {   //method called when app is launched
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url + "digitalRead?deviceName=" + deviceName + "&pin=1",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    state = Integer.parseInt(response.getString("value"));
                    textViewResponse.setText(getString(R.string.ok_response));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Could not connect!",
                            Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Could not connect!", Toast.LENGTH_SHORT).show();
            }
        });           //request to check the status status of ESP8266 Wifi module
        requestQueue.add(jsonObjectRequest);        //sending the request
    }
    private void setButtonLabel(){          //setting the button label according to the state of
        if (state == 1) button.setText(getString(R.string.on));       //the relay
        else button.setText(getString(R.string.off));
    }
}   //class end