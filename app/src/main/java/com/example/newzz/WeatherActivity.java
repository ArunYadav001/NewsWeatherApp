package com.example.newzz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    private TextView temperature, region ,humidity;
    private TextView min , max;
    private TextView viewmore;
    private TextView description;
    private RequestQueue requestQueue;
    private FrameLayout framelayout;
    private EditText editText;
    private String cityname;
    private Button action;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        framelayout = findViewById(R.id.framelayout);
        framelayout.setVisibility(View.GONE);
        editText = findViewById(R.id.edit);
        action = findViewById(R.id.btn);
        temperature = findViewById(R.id.temperature);
        region = findViewById(R.id.region);
        description = findViewById(R.id.desc);
        humidity = findViewById(R.id.addHUMID);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        viewmore = findViewById(R.id.viewMore);

        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout.setVisibility(View.VISIBLE);
                Fragment fragment = new web();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });




        requestQueue = Volley.newRequestQueue(this);



        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWeather();

            }
        });




    }

    private  void loadWeather(){
        cityname = editText.getText().toString();
        final  String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q="+ cityname +  "&units=metric&appid=a8574d992448abf0bb7b31dcf113eaf2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main.getDouble("temp"));
                    String tempmin = String.valueOf(main.getDouble("temp_min"));
                    String tempmax = String.valueOf(main.getDouble("temp_max"));
                    int hum = main.getInt("humidity");
                    String desc  = object.getString("description");
                    String city = response.getString("name");



                    temperature.setText(Math.round(Float.parseFloat(temp)) + "\u2103");
                    region.setText(city);
                    description.setText(desc);
                    humidity.setText(String.valueOf(hum));
                    min.setText((tempmin) + "\u2103");
                    max.setText((tempmax)+ "\u2103");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        framelayout.setVisibility(View.GONE);
    }
}
