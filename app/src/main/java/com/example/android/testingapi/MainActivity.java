package com.example.android.testingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


     TextView Name , temperature,weathercondition,aqivalue ;
       ImageView weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Name = findViewById(R.id.cityname);
        temperature=findViewById(R.id.TemperatureTV);
        weathercondition=findViewById(R.id.WeatherConditionTV);
        aqivalue=findViewById(R.id.AqiValue);
        weather=findViewById(R.id.weathericon);









        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.weatherapi.com/v1/current.json?key=%208256777e0a494aa988a153840220202%20&q=Delhi&days=1&aqi=yes&alerts=yes";


// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String nome = response.getJSONObject("location").getString("name");
                            Name.setText(nome);
                            String temp = response.getJSONObject("current").getString("temp_c");
                            temperature.setText(temp+"°C");
                            String cond=response.getJSONObject("current").getJSONObject("condition").getString("text");
                            weathercondition.setText(cond);
                            String wind=response.getJSONObject("current").getString("wind_kph");
                            aqivalue.setText(wind+" km/h");
                            String img=response.getJSONObject("current").getJSONObject("condition").getString("icon");
                            String url = "https:".concat(img);
                            Picasso.get().load(url).into(weather);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}