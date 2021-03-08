package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

        try {
            Intent intent = getIntent();
            result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));
        }
        catch (Exception e){
            result_calories =Integer.parseInt( activity_health_evaluation.result_calories_static);
        }


        button = (Button) findViewById(R.id.button_to_evening);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
        // mapquest
//        jsonParse();

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

    // mapquest
    private void jsonParse() {
        String url = "https://www.mapquestapi.com/search/v2/radius?origin=33.646875,+-117.840508&radius=1&maxMatches=3&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799101&outFormat=json&key=KEY";
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
}


