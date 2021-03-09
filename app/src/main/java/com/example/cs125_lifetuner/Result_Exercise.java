package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Result_Exercise extends AppCompatActivity {
    private Button button;
    int result_calories;

    // mapquest
    private ListView exerciseNameList = findViewById(R.id.Exercise_Location);
    FusedLocationProviderClient client;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.User_profile:
                openProfilePage();
                break;
            case R.id.morning:
                openMorningPage();
                break;
            case R.id.afternoon:
                openAfternoonPage();
                break;
            case R.id.profile:
                openProfile();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__exercise);

        try {
            Intent intent = getIntent();
            result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));
        } catch (Exception e) {
            result_calories = Integer.parseInt(activity_health_evaluation.result_calories_static);
        }


        button = (Button) findViewById(R.id.button_to_evening);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextView date_field = (TextView) findViewById(R.id.date);
        date_field.setText(date);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Result_Exercise.this, Profile_page.class);
                i.putExtra("result_calories", String.valueOf(result_calories));
                startActivity(i);
            }
        });
        //更改完毕
        // getcurrent location
//        client = LocationServices.getFusedLocationProviderClient(this);
//        Location location = getCurrentLocation();
//        String lat = Double.toString(location.getLatitude());
//        String lng = Double.toString(location.getLongitude());
//        Toast.makeText(this, "latitude:"+lat+"; longtitude:" + lng, Toast.LENGTH_SHORT).show();


        // mapquest
//        jsonParse(location);

        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //
    }


    public void openProfilePage() {
        Intent intent = new Intent(this, Profile_page.class);
        startActivity(intent);
    }

    public void openMorningPage() {
        Intent intent = new Intent(this, Result_Food.class);
        startActivity(intent);
    }

    public void openAfternoonPage() {
        Intent intent = new Intent(this, Result_Exercise.class);
        startActivity(intent);
    }


    public void openProfile() {
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

    // mapquest
    private void jsonParse(Location location) {
        String lat = Double.toString(location.getLatitude());
        String lng = Double.toString(location.getLongitude());
        String url = "https://www.mapquestapi.com/search/v2/radius?origin=" + lat + ",+" + lng + "&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key= OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX ";

//        String url = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key= OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX ";
        ArrayList<String> resultList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("searchResults");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String resultName = jsonObject.getString("name");
                                resultList.add(resultName);
                            }
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

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(Result_Exercise.this, android.R.layout.simple_list_item_1, resultList);
        exerciseNameList.setAdapter(arrayAdapter);
    }

    private Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Task<Location> task = client.getLastLocation();
        final Location[] resultLocation = new Location[1];
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    resultLocation[0] = location;
                }
            }
        });

        return resultLocation[0];
    }
}


