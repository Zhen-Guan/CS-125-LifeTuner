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

    }


    public void openInfoPage(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

    public void openFoodRec(){
        Intent intent = new Intent(this, Result_Food.class);
        startActivity(intent);
    }

}