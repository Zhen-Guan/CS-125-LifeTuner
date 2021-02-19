package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Result_Morning extends AppCompatActivity {
    Button button;
    Button button_map;
    ListView morning_list, lunch_dinner_list;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BMR:
                openEvaluationPage();
                break;
            case R.id.morning:
                openMorningPage();
                break;
            case R.id.evening:
                openEveningPage();
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
        setContentView(R.layout.activity_result__morning);
        button = (Button) findViewById(R.id.button_to_afternoon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLunchpage();
            }
        });

        button_map = (Button) findViewById(R.id.button_to_map);
        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result_Morning.this, mapFragment.class);
                startActivity(intent);
            }
        });


        // Show morning database
        morning_list = findViewById(R.id.morning_list);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Result_Morning.this);

        List<FoodModel> allFoodMorning = dataBaseHelper.getAllFoodMorning();

        ArrayAdapter foodArrayAdapterMorning = new ArrayAdapter<FoodModel>(Result_Morning.this, android.R.layout.simple_list_item_1, allFoodMorning);
        morning_list.setAdapter(foodArrayAdapterMorning);


        //show lunch & dinner database
        lunch_dinner_list= findViewById((R.id.lunch_dinner_list));
        List<FoodModel> allFoodLunchDinner = dataBaseHelper.getAllFoodLunchDinner();
        ArrayAdapter foodArrayAdapterLunchDinner = new ArrayAdapter<FoodModel>(Result_Morning.this, android.R.layout.simple_list_item_1, allFoodLunchDinner);
        lunch_dinner_list.setAdapter(foodArrayAdapterLunchDinner);


        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
//        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
//        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
//        animationDrawable.setEnterFadeDuration(4000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();
        //


    }



    public void openLunchpage(){
        Intent intent = new Intent(this, Result_Afternoon.class);
        startActivity(intent);
    }

    public void openEvaluationPage(){
        Intent intent = new Intent(this, activity_health_evaluation.class);
        startActivity(intent);
    }

    public void openMorningPage(){
        Intent intent = new Intent(this, Result_Morning.class);
        startActivity(intent);
    }

    public void openAfternoonPage(){
        Intent intent = new Intent(this, Result_Afternoon.class);
        startActivity(intent);
    }

    public void openEveningPage(){
        Intent intent = new Intent(this, Result_Evening.class);
        startActivity(intent);
    }

    public void openProfile(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

}