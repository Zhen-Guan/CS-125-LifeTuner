package com.example.cs125_lifetuner;

import androidx.annotation.NonNull;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Result_Exercise extends AppCompatActivity {
    private Button button;
    int result_calories;
    ListView Location_list, Exercise_list;
    List<String> Location, recmd_exercises;
    public static double exercise_calories = 0;
    public static List<Double> record_calories = new ArrayList<>();
    FusedLocationProviderClient client;
    private String weather;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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

        //create map of exercises
        Map<String,Integer> exercise = new HashMap<String,Integer>();
        exercise.put("Weight Lifting: general",112);
        exercise.put("Aerobics: water",149);
        exercise.put("Stretching, Hatha Yoga",149);
        exercise.put("Calisthenics: moderate",167);
        exercise.put("Riders: general",186);
        exercise.put("Aerobics: low impact",205);
        exercise.put("Stair Step Machine: general",223);
        exercise.put("Teaching aerobics",223);
        exercise.put("Weight Lifting: vigorous",223);
        exercise.put("Aerobics, Step: low impact",260);
        exercise.put("Aerobics: high impact",260);
        exercise.put("Bicycling, Stationary: moderate",260);
        exercise.put("Rowing, Stationary: moderate",260);
        exercise.put("Calisthenics: vigorous",298);
        exercise.put("Circuit Training: general",298);
        exercise.put("Rowing, Stationary: vigorous",316);
        exercise.put("Elliptical Trainer: general",335);
        exercise.put("Ski Machine: general",353);
        exercise.put("Aerobics, Step: high impact",372);
        exercise.put("Bicycling, Stationary: vigorous",391);
        exercise.put("Billiards",93);
        exercise.put("Bowling",112);
        exercise.put("Dancing: slow, waltz, foxtrot",112);
        exercise.put("Frisbee",112);
        exercise.put("Volleyball: non-competitive, general play",112);
        exercise.put("Water Volleyball",112);
        exercise.put("Archery: non-hunting",130);
        exercise.put("Golf: using cart",130);
        exercise.put("Hang Gliding",130);
        exercise.put("Curling",149);
        exercise.put("Gymnastics: general",149);
        exercise.put("Horseback Riding: general",149);
        exercise.put("Tai Chi",149);
        exercise.put("Volleyball: competitive, gymnasium play",149);
        exercise.put("Walking: 3.5 mph (17 min/mi)",149);
        exercise.put("Badminton: general",167);
        exercise.put("Walking: 4 mph (15 min/mi)",167);
        exercise.put("Kayaking",186);
        exercise.put("Skateboarding",186);
        exercise.put("Snorkeling",186);
        exercise.put("Softball: general play",186);
        exercise.put("Walking: 4.5 mph (13 min/mi)",186);
        exercise.put("Whitewater: rafting, kayaking",186);
        exercise.put("Dancing: disco, ballroom, square",205);
        exercise.put("Golf: carrying clubs",205);
        exercise.put("Dancing: Fast, ballet, twist",223);
        exercise.put("Fencing",223);
        exercise.put("Hiking: cross-country",223);
        exercise.put("Skiing: downhill",223);
        exercise.put("Swimming: general",223);
        exercise.put("Walk/Jog: jog <10 min.",223);
        exercise.put("Water Skiing",223);
        exercise.put("Wrestling",223);
        exercise.put("Basketball: wheelchair",242);
        exercise.put("Race Walking",242);
        exercise.put("Ice Skating: general",260);
        exercise.put("Racquetball: casual, general",260);
        exercise.put("Rollerblade Skating",260);
        exercise.put("Scuba or skin diving",260);
        exercise.put("Sledding, luge, toboggan",260);
        exercise.put("Soccer: general",260);
        exercise.put("Tennis: general",260);
        exercise.put("Basketball: playing a game",298);
        exercise.put("Bicycling: 12-13.9 mph",298);
        exercise.put("Football: touch, flag, general",298);
        exercise.put("Hockey: field & ice",298);
        exercise.put("Rock Climbing: rappelling",298);
        exercise.put("Running: 5 mph (12 min/mile)",298);
        exercise.put("Running: pushing wheelchair, marathon wheeling",298);
        exercise.put("Skiing: cross-country",298);
        exercise.put("Snow Shoeing",298);
        exercise.put("Swimming: backstroke",298);
        exercise.put("Volleyball: beach",298);
        exercise.put("Bicycling: BMX or mountain",316);
        exercise.put("Boxing: sparring",335);
        exercise.put("Football: competitive",335);
        exercise.put("Orienteering",335);
        exercise.put("Running: 5.2 mph (11.5 min/mile)",335);
        exercise.put("Running: cross-country",335);
        exercise.put("Bicycling: 14-15.9 mph",372);
        exercise.put("Martial Arts: judo, karate, kickbox",372);
        exercise.put("Racquetball: competitive",372);
        exercise.put("Rope Jumping",372);
        exercise.put("Running: 6 mph (10 min/mile)",372);
        exercise.put("Swimming: breaststroke",372);
        exercise.put("Swimming: laps, vigorous",372);
        exercise.put("Swimming: treading, vigorous",372);
        exercise.put("Water Polo",372);
        exercise.put("Rock Climbing: ascending",409);
        exercise.put("Running: 6.7 mph (9 min/mile)",409);
        exercise.put("Swimming: butterfly",409);
        exercise.put("Swimming: crawl",409);
        exercise.put("Bicycling: 16-19 mph",446);
        exercise.put("Handball: general",446);
        exercise.put("Running: 7.5 mph (8 min/mile)",465);
        exercise.put("Running: 8.6 mph (7 min/mile)",539);
        exercise.put("Bicycling: > 20 mph",614);
        exercise.put("Running: 10 mph (6 min/mile)",614);
        exercise.put("Planting seedlings, shrubs",149);
        exercise.put("Raking Lawn",149);
        exercise.put("Sacking grass or leaves",149);
        exercise.put("Gardening: general",167);
        exercise.put("Mowing Lawn: push, power",167);
        exercise.put("Operate Snow Blower: walking",167);
        exercise.put("Plant trees",167);
        exercise.put("Gardening: weeding",172);
        exercise.put("Carrying & stacking wood",186);
        exercise.put("Digging, spading dirt",186);
        exercise.put("Laying sod / crushed rock",186);
        exercise.put("Mowing Lawn: push, hand",205);
        exercise.put("Chopping & splitting wood",223);
        exercise.put("Shoveling Snow: by hand",223);
        List<String> data = new ArrayList<String>(exercise.keySet());





        try {
            Intent intent = getIntent();
            result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));
        }
        catch (Exception e){
            result_calories =Integer.parseInt( activity_health_evaluation.result_calories_static);
        }

        Exercise_list= findViewById((R.id.exercise_list));
        recmd_exercises = new ArrayList<>();
        double calories_needed =Result_Food.total_calorie_food-Double.parseDouble(activity_health_evaluation.need_calorie);
        double cur_calories = 0;
        while (cur_calories < calories_needed){
            int random_index = getRandomNumber(0,data.size());
            cur_calories += (exercise.get(data.get(random_index)));
            recmd_exercises.add(data.get(random_index) + "     Calories: " + String.valueOf(exercise.get(data.get(random_index))));
            data.remove(random_index);
        }
        exercise_calories = cur_calories;

        record_calories.add(Result_Food.total_calorie_food - Result_Exercise.exercise_calories);


        ArrayAdapter foodArrayAdapterMorning = new ArrayAdapter<>(Result_Exercise.this, android.R.layout.simple_list_item_1, recmd_exercises);
        Exercise_list.setAdapter(foodArrayAdapterMorning);

        Toast.makeText(this, String.valueOf(exercise_calories), Toast.LENGTH_SHORT);

        // get weather
        weather = getCurrentWeather();


        button = (Button) findViewById(R.id.button_to_home);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date = date + "; " + weather;
        TextView date_field = (TextView)findViewById(R.id.date);
        date_field.setText(date);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Result_Exercise.this , Profile_page.class);
                i.putExtra("result_calories", String.valueOf(result_calories));
                startActivity(i);
            }
        });
        //更改完毕


        // get location
        client = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //
    }



    public void openProfilePage(){
        Intent intent = new Intent(this, Profile_page.class);
        startActivity(intent);
    }

    public void openMorningPage(){
        Intent intent = new Intent(this, Result_Food.class);
        startActivity(intent);
    }

    public void openAfternoonPage(){
        Intent intent = new Intent(this, Result_Exercise.class);
        startActivity(intent);
    }


    public void openProfile(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private ArrayList<String> jsonParser(String data) {
        ArrayList<String> resultList = new ArrayList<>();
        Gson gson = new GsonBuilder().setLenient().create();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
//        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("searchResults");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
            String resultName = jsonObject2.get("name").getAsString();
            resultList.add(resultName);
        }

        return resultList;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return ;
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
        }
        final String[] res = new String[1];
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Double lat = location.getLatitude();
                    Double lng = location.getLongitude();
                    res[0] = lat.toString() + ",+" + lng.toString();
                    // pass current location to request, and then parse the response json.
                    locationRecorder(res[0]);
//                    Toast.makeText(Result_Exercise.this, res[0], Toast.LENGTH_LONG).show();
                }
            }
        });
        return;
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

    private void locationRecorder(String currentLocation) {
        // location list
        String data2 = "";
        try {
            String sURL = "https://www.mapquestapi.com/search/v2/radius?origin="+currentLocation+"&radius=1&maxMatches=4&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key=OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
//            if (weather != "Showers" || weather != "Rain" || weather != "T-Storms ") {
//                sURL = "https://www.mapquestapi.com/search/v2/radius?origin="+currentLocation+"&radius=1&maxMatches=4&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799951&outFormat=json&key=OqC1DY34U3qYSVrCzrMVqw0AlcIcJ3AX";
//            }
            URL url = new URL(sURL);
            HttpURLConnection request =(HttpURLConnection) url.openConnection();
            InputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                data2 = data2 + line;
            }
        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Location_list = findViewById(R.id.location_list);
        ArrayList<String> resultList = jsonParser(data2);
        ArrayAdapter locationArrayAdapter = new ArrayAdapter<>(Result_Exercise.this,
                android.R.layout.simple_list_item_1, resultList);

        Location_list.setAdapter(locationArrayAdapter);
    }

    private String getCurrentWeather() {
        String data = "";
        try {
            String sURL = "https://dataservice.accuweather.com/forecasts/v1/hourly/1hour/325023?apikey=%20BLrJ4NbiloLukli3XfnHWvc6VnL81KHs%20";
            URL url = new URL(sURL);
            HttpURLConnection request =(HttpURLConnection) url.openConnection();
            InputStream inputStream = request.getInputStream(); // potentially problematic
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                data = data + line;
            }
        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gsonx = new GsonBuilder().setLenient().create();
        JsonArray jsonArrayx = gsonx.fromJson(data, JsonArray.class);
        JsonObject jsonObjectx = jsonArrayx.get(0).getAsJsonObject();
        String resultName = jsonObjectx.get("IconPhrase").getAsString();
        return resultName;
    }
}


