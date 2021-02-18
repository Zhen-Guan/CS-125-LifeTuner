package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_health_evaluation extends AppCompatActivity {

    Button btn_get_recommendation;

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
        setContentView(R.layout.activity_health_evaluation);

        btn_get_recommendation = findViewById(R.id.get_recommendation);
        Intent intent=getIntent();
        TextView dmr=findViewById(R.id.bmr_value);
        dmr.setText(intent.getStringExtra("bmr_value"));
        TextView cur_weight=findViewById(R.id.current_weight_value);
        cur_weight.setText(intent.getStringExtra("current_weight"));
        TextView tar_weight=findViewById(R.id.target_weight_value);
        tar_weight.setText(intent.getStringExtra("target_weight"));
        TextView cal=findViewById(R.id.bmr_value2);
        cal.setText(intent.getStringExtra("calories"));


        btn_get_recommendation.setOnClickListener(v -> {
            Intent i = new Intent(this, Result_Morning.class);
            startActivity(i);
        });





        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
//        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
//        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
//        animationDrawable.setEnterFadeDuration(4000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();
        //
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