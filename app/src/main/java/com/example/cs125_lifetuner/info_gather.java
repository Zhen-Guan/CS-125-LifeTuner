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

public class info_gather extends AppCompatActivity implements View.OnClickListener {


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gather);

        Button button = (Button) findViewById(R.id.enter);
        getId();

        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //
    }
    private void getId(){
        weight=findViewById(R.id.weight);
        height = findViewById(R.id.height);
        age = findViewById(R.id.age);
        Button button_send = findViewById(R.id.enter);
        button_send.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.enter:
                Intent intent=new Intent(info_gather.this,activity_health_evaluation.class);
                String weight_new=weight.getText().toString().trim();
                int w = Integer.parseInt(weight_new);
                String height_new=height.getText().toString().trim();
                int h = Integer.parseInt(height_new);
                String age_new=age.getText().toString().trim();
                int a = Integer.parseInt(age_new);
                final double d = (10 * w) + (6.25 * h) - 5 * a;
                String bmr = String.valueOf(d);
                String target = String.valueOf((h-80)*0.7);
                String calories = String.valueOf((d) *1.2);
                intent.putExtra("current_weight",weight_new+"");
                intent.putExtra("data",bmr+"");
                intent.putExtra("target_weight",target+"");
                intent.putExtra("calories",calories+"");
                startActivity(intent);
        }
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
