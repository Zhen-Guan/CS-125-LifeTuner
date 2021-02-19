package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
        Intent intent = getIntent();
        TextView dmr=findViewById(R.id.bmr_value);
        TextView cur_weight=findViewById(R.id.current_weight_value);
        TextView tar_weight=findViewById(R.id.target_weight_value);
        TextView cal=findViewById(R.id.needed_calories);

        dmr.setText(intent.getStringExtra("bmr_value"));
        cur_weight.setText(intent.getStringExtra("current_weight"));
        tar_weight.setText(intent.getStringExtra("target_weight"));
        cal.setText(intent.getStringExtra("calories"));


        //设置activity level spinner
        Spinner activity_level_spinner = (Spinner) findViewById(R.id.activity_level_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> al_adapter = ArrayAdapter.createFromResource(this,
                R.array.activiy_level_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        al_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        activity_level_spinner.setAdapter(al_adapter);


        //设置weight loss spinner
        Spinner weight_loss_spinner = (Spinner) findViewById(R.id.weight_loss_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> wl_adapter = ArrayAdapter.createFromResource(this,
                R.array.target_weight_loss_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        wl_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        weight_loss_spinner.setAdapter(wl_adapter);


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