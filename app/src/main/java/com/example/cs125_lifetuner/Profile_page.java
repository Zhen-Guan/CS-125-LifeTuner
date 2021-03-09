package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class Profile_page extends AppCompatActivity {
    public String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        TextView bmr_result = findViewById(R.id.profile_bmr_value);
        TextView target_w = findViewById(R.id.profile_target_weight_value);
        TextView needed_ca = findViewById(R.id.profile_needed_calories);
        TextView cur_weight = findViewById(R.id.profile_current_weight_value);

        bmr_result.setText(activity_health_evaluation.BMR_value);
        target_w.setText(activity_health_evaluation.target_weight__);
        needed_ca.setText(activity_health_evaluation.need_calorie);
        cur_weight.setText(activity_health_evaluation.current_weight);


        Button fill_button = (Button) findViewById(R.id.fill_information);
        fill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoPage();
            }
        });

        Button rec_pages_button = (Button) findViewById(R.id.view_recommendation);
        rec_pages_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodRec();
            }
        });

        Button weight_changes = (Button) findViewById(R.id.view_weight_changes);
        weight_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeightChangesPage();
            }
        });

        Button intake_changes = (Button) findViewById(R.id.intake_calories);
        intake_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntakeCalorePage();
            }
        });

    }

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


    public void openInfoPage(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

    public void openFoodRec(){
        Intent intent = new Intent(this, Result_Food.class);
        startActivity(intent);
    }

    public void openWeightChangesPage(){
        Intent intent = new Intent(this, LineChart_Weight_Changed.class);
        startActivity(intent);
    }

    public void openIntakeCalorePage(){
        Intent intent = new Intent(this, BarChart_Calorie_Intake.class);
        startActivity(intent);
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

}