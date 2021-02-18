package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.Toast;

public class info_gather extends AppCompatActivity {


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

    private EditText weight;
    private EditText height;
    private EditText age;
    Button btn_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gather);

        weight =findViewById(R.id.weight);
        height = findViewById(R.id.height);
        age = findViewById(R.id.age);
        btn_enter = findViewById(R.id.enter);

        btn_enter.setOnClickListener(v -> {
            try
            {
                int w = Integer.parseInt(weight.getText().toString().trim());
                int h = Integer.parseInt(height.getText().toString().trim());
                int a = Integer.parseInt(age.getText().toString().trim());

                if (a > 150) {
                    Toast.makeText(info_gather.this, "Age is too large", Toast.LENGTH_SHORT).show();
                }
                else if (w > 1400) {
                    Toast.makeText(info_gather.this, "Weight is too large", Toast.LENGTH_SHORT).show();
                }
                else if (h > 272) {
                    Toast.makeText(info_gather.this, "Height is too large", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(info_gather.this, activity_health_evaluation.class);

                    final double d = (10 * w) + (6.25 * h) - 5 * a;
                    String bmr = String.valueOf(Math.round(d));
                    String target = String.valueOf((h - 80) * 0.7);
                    String calories = String.valueOf(Math.round((d) * 1.2));

                    intent.putExtra("current_weight", String.valueOf(w));
                    intent.putExtra("bmr_value", bmr);
                    intent.putExtra("target_weight", target);
                    intent.putExtra("calories", calories);

                    startActivity(intent);
                 }
            } catch (Exception e){
                Toast.makeText(info_gather.this, "Please enter all blanks", Toast.LENGTH_SHORT).show();
            }


        });

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
