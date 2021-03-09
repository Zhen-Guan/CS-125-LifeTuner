package com.example.cs125_lifetuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Result_Exercise extends AppCompatActivity {
    private Button button;
    int result_calories;

    // mapquest
    private ListView exerciseNameList;
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
//
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        try {
            Intent intent = getIntent();
            result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));
        } catch (Exception e) {
            result_calories = Integer.parseInt(activity_health_evaluation.result_calories_static);
        }


        String data = "";
        try {
            String sURL = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key=OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
            URL url = new URL(sURL);
            HttpURLConnection request =(HttpURLConnection) url.openConnection();
            InputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "retrieve data is "+ data, Toast.LENGTH_SHORT).show();



//
//        try {
//            Toast.makeText(Result_Exercise.this, "exception  does not occurss", Toast.LENGTH_LONG);
//            //jsonParse2();
//        } catch (IOException e) {
//            Toast.makeText(Result_Exercise.this, "exception occurss", Toast.LENGTH_LONG);
//        } catch (JSONException e) {
//            Toast.makeText(Result_Exercise.this, "exception occurss", Toast.LENGTH_LONG);
//        }

//        RetrieveFeedTask a = new RetrieveFeedTask();
//        a.doInBackground();

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
        exerciseNameList = findViewById(R.id.Exercise_Location);
//        private void jsonParse2() throws IOException {
//            Toast.makeText(Result_Exercise.this, "HERE", Toast.LENGTH_LONG);
//
//            ArrayList<String> resultList = new ArrayList<>();
//            String sURL = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key= OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
//            // Connect to the URL using java's native library
//            URL url = new URL(sURL);
//            URLConnection request = url.openConnection();
//            request.connect();
//
//            // Convert to a JSON object to print data
//            JsonParser jp = new JsonParser(); //from gson
//            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
//            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
//            JsonArray jsonArray = rootobj.getAsJsonArray("searchResults");
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
//                String resultName = jsonObject.get("name").getAsString();
//                resultList.add(resultName);
//            }
//            if (!resultList.isEmpty()) {
//                Toast.makeText(Result_Exercise.this, "parsed", Toast.LENGTH_LONG);
//            } else {
//                Toast.makeText(Result_Exercise.this, "failed", Toast.LENGTH_LONG);
//            }
//        }
        // getcurrent location
//        client = LocationServices.getFusedLocationProviderClient(this);
//        Location location = getCurrentLocation();
//        if (location != null) {
//            String lat = Double.toString(location.getLatitude());
//            String lng = Double.toString(location.getLongitude());
//            Toast.makeText(this, "latitude:"+lat+"; longtitude:" + lng, Toast.LENGTH_SHORT).show();
//
//        }
//        else {
//            Toast.makeText(this, "still no latlng", Toast.LENGTH_SHORT).show();
//        }


        // mapquest
//        Location location = null;
//        jsonParse(location);
//        try {
//            jsonParse2();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
//    private void jsonParse(Location location) {
//        String url = "";
//        if (location != null) {
//            String lat = Double.toString(location.getLatitude());
//            String lng = Double.toString(location.getLongitude());
//            url = "https://www.mapquestapi.com/search/v2/radius?origin=" + lat + ",+" + lng + "&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key= OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
//        } else {
//            url = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key= OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
////            Toast.makeText(Result_Exercise.this, "here3", Toast.LENGTH_LONG).show();
//        }
//        ArrayList<String> resultList = new ArrayList<>();
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(Result_Exercise.this, "response", Toast.LENGTH_LONG).show();
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("searchResults");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String resultName = jsonObject.getString("name");
//                                resultList.add(resultName);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
////        if (resultList.isEmpty()) {
////            Toast.makeText(Result_Exercise.this, "here4", Toast.LENGTH_LONG).show();
////        }
//        ArrayAdapter arrayAdapter = new ArrayAdapter<>(Result_Exercise.this, android.R.layout.simple_list_item_1, resultList);
//        exerciseNameList.setAdapter(arrayAdapter);
//    }

//    Thread thread = new Thread(new Runnable() {public void run() {}});
//
//    }
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run(){
//                try{
//                    Toast.makeText(Result_Exercise.this, "HERE", Toast.LENGTH_LONG);
//                    ArrayList<String> resultList = new ArrayList<>();
//                    String sURL = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key=OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
//                    // Connect to the URL using java's native library
//                    URL url = new URL(sURL);
//                    HttpURLConnection request =(HttpURLConnection) url.openConnection();
//                    request.setRequestMethod("GET");
//                    request.setRequestProperty("User-Agent", "Mozilla/5.0");
//                    int responseCode = request.getResponseCode();
//                    Toast.makeText(Result_Exercise.this, "response code: " + String.valueOf(responseCode), Toast.LENGTH_LONG);
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(request.getInputStream()));
//                    String inputLine;
//                    StringBuffer response = new StringBuffer();
//                    while ((inputLine = in.readLine()) != null) {
//                        response.append(inputLine);
//                    }
//                    in.close();
//                    Toast.makeText(Result_Exercise.this, "response " + response.toString(), Toast.LENGTH_LONG);
//                    JSONObject myResponse = new JSONObject(response.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });



    private Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(this, "here1", Toast.LENGTH_LONG).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "here2", Toast.LENGTH_LONG).show();
                return null;
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
        }

        Task<Location> task = client.getLastLocation();
        final Location[] resultLocation = new Location[1];
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    resultLocation[0] = location;
                }
                else {
                    Toast.makeText(Result_Exercise.this, "here3", Toast.LENGTH_LONG).show();
                }
            }
        });

        return resultLocation[0];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    private class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {
//
//        private Exception exception;
//
//        protected Void doInBackground(Void... voids) {
//            try {
//                //jsonParse2();
//            } catch (Exception e) {
//                this.exception = e;
//
//
//                return null;
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void voids) {
//            // TODO: check this.exception
//            // TODO: do something with the feed
//        }
//    }
}


