package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Result_Food extends AppCompatActivity {
    Button button;
    //Button button_map;
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
        setContentView(R.layout.activity_result__food);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextView date_field = (TextView)findViewById(R.id.date);
        date_field.setText(date);

        button = (Button) findViewById(R.id.button_to_afternoon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLunchpage();
            }
        });

//        button_map = (Button) findViewById(R.id.button_to_map);
//        button_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Result_Morning.this, recmap.class);
//                startActivity(intent);
//            }
//        });


        // Show morning database
        morning_list = findViewById(R.id.morning_list);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Result_Food.this);

        List<FoodModel> allFoodMorning = dataBaseHelper.getAllFoodMorning();

        ArrayAdapter foodArrayAdapterMorning = new ArrayAdapter<FoodModel>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodMorning);
        morning_list.setAdapter(foodArrayAdapterMorning);


        //show lunch & dinner database
        lunch_dinner_list= findViewById((R.id.lunch_dinner_list));
        List<FoodModel> allFoodLunchDinner = dataBaseHelper.getAllFoodLunchDinner();
        ArrayAdapter foodArrayAdapterLunchDinner = new ArrayAdapter<FoodModel>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodLunchDinner);
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
        Intent intent = new Intent(this, Result_Exercise.class);
        startActivity(intent);
    }

    public void openEvaluationPage(){
        Intent intent = new Intent(this, activity_health_evaluation.class);
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

    public void openEveningPage(){
        Intent intent = new Intent(this, Result_Evening.class);
        startActivity(intent);
    }

    public void openProfile(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

}