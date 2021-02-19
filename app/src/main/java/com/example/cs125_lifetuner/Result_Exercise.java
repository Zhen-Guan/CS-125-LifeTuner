package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Result_Exercise extends AppCompatActivity {
    private Button button;
    int result_calories;

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
        setContentView(R.layout.activity_result__exercise);

        Intent intent = getIntent();
        result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));

        button = (Button) findViewById(R.id.button_to_evening);1
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextView date_field = (TextView)findViewById(R.id.date);
        date_field.setText(date);
//

        // 这里我 （莫昊卿） 改了一下，因为exercise page 跳到的那个“Home” Button 不稳定，有时候会跳到evaluation page， 有时候却跳到food
        // 我这里先统一改成跳到food 推荐page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Result_Exercise.this , Result_Food.class);
                i.putExtra("result_calories", String.valueOf(result_calories));
                startActivity(i);
            }
        });
        //更改完毕

        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //
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


    public void openProfile(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }
}


