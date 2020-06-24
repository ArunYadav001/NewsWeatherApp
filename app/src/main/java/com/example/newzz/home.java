package com.example.newzz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<data> newsitems;
    private RequestQueue requestQueue;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Newzz");

        frameLayout = findViewById(R.id.framelayout);
        frameLayout.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL,false));
        recyclerView.setAdapter(recyclerViewAdapter);



        newsitems = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        loaditems();







        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        break;

                    case R.id.nav_gallery:
                        Intent i = new Intent(home.this, WeatherActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_slideshow:
                        String url = "https://newsinshort.000webhostapp.com";
                        Intent ii = new Intent(Intent.ACTION_VIEW);
                        ii.setData(Uri.parse(url));
                        startActivity(ii);
                       break;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings :
                frameLayout.setVisibility(View.VISIBLE);
                Fragment fragment = new tip();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            default:
                return super.onOptionsItemSelected(item);
        }
        }


//
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.framelayout);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void loaditems(){

        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=4b04653d3cea4423b6e349dc2a6afa45";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    JSONArray NewsArray = obj.getJSONArray("articles");
                    for (int i = 0 ; i<NewsArray.length(); i++){
                        data model = new data();
                        JSONObject rec = NewsArray.getJSONObject(i);
                        String heading  = NewsArray.getJSONObject(i).getString("title");
                        String imgurl = NewsArray.getJSONObject(i).getString("urlToImage");
                        String description = NewsArray.getJSONObject(i).getString("description");
                        newsitems.add(new data(heading,description,imgurl));
                    }

                    recyclerViewAdapter = new RecyclerViewAdapter(newsitems, home.this);
                    recyclerView.setAdapter(recyclerViewAdapter);



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        frameLayout.setVisibility(View.GONE);
    }
}
