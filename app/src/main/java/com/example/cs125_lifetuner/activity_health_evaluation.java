package com.example.cs125_lifetuner;

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

public class activity_health_evaluation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_get_recommendation;
    String selected_activity_level, selected_weight_loss;
    int result_calories, base_calories;

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
        setContentView(R.layout.activity_health_evaluation);

        btn_get_recommendation = findViewById(R.id.get_recommendation);

        btn_get_recommendation.setOnClickListener(v -> {

            Intent i = new Intent(this, Result_Food.class);
            i.putExtra("result_calories", String.valueOf(result_calories));
            startActivity(i);
        });

        selected_activity_level = "Sedentary: little or no exercise";
        selected_weight_loss = "Maintain Weight";

        Intent intent = getIntent();
        TextView dmr=findViewById(R.id.bmr_value);
        TextView cur_weight=findViewById(R.id.current_weight_value);
        TextView tar_weight=findViewById(R.id.target_weight_value);
        TextView cal=findViewById(R.id.needed_calories);

        dmr.setText(intent.getStringExtra("bmr_value"));
        cur_weight.setText(intent.getStringExtra("current_weight"));
        tar_weight.setText(intent.getStringExtra("target_weight"));

        base_calories = Integer.parseInt(intent.getStringExtra("calories"));
        result_calories = base_calories;
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
        activity_level_spinner.setOnItemSelectedListener(this);
        //


        //设置weight loss spinner
        Spinner weight_loss_spinner = (Spinner) findViewById(R.id.weight_loss_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> wl_adapter = ArrayAdapter.createFromResource(this,
                R.array.target_weight_loss_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        wl_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        weight_loss_spinner.setAdapter(wl_adapter);
        weight_loss_spinner.setOnItemSelectedListener(this);
        //


        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(0).equals("Sedentary: little or no exercise")) {
            selected_activity_level = parent.getItemAtPosition(position).toString();
            changeCalTextAl(position);
        }
        else if (parent.getItemAtPosition(0).equals("Maintain Weight")) {
            selected_weight_loss = parent.getItemAtPosition(position).toString();
            changeCalTextWl(position);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selected_activity_level = "Sedentary: little or no exercise";
        selected_weight_loss = "Maintain Weight";

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

    public void changeCalTextAl(int position){
        TextView cal=findViewById(R.id.needed_calories);
        if (position == 0){
            result_calories = (int) (base_calories * CheckCurrentWL());
            cal.setText((String.valueOf(result_calories)));

        }
        else if (position == 1){
            result_calories = (int) (base_calories * 1.2 * CheckCurrentWL());
            cal.setText((String.valueOf(result_calories)));
        }
        else if (position == 2){
            result_calories = (int) (base_calories * 1.345 * CheckCurrentWL());
            cal.setText((String.valueOf(result_calories)));
        }
        else if (position == 3){
            result_calories = (int) (base_calories * 1.465 * CheckCurrentWL());
            cal.setText((String.valueOf(result_calories)));
        }
        else if (position == 4){
            result_calories = (int) (base_calories * 1.551 * CheckCurrentWL());
            cal.setText((String.valueOf(result_calories)));
        }
    }

    public void changeCalTextWl(int position){
        TextView cal=findViewById(R.id.needed_calories);
        if (position == 0){
            result_calories = (int) (base_calories * CheckCurrentAL());
            cal.setText(String.valueOf(result_calories));
        }
        else if (position == 1){
            result_calories = (int) (base_calories * CheckCurrentAL() + 500);
            cal.setText(String.valueOf(result_calories));
        }
        else if (position == 2){
            result_calories = (int) (base_calories * 0.882 * CheckCurrentAL());
            cal.setText(String.valueOf(result_calories));
        }
        else if (position == 3){
            result_calories = (int) (base_calories * 0.764 * CheckCurrentAL());
            cal.setText(String.valueOf(result_calories));
        }
        else if (position == 4){
            result_calories = (int) (base_calories * 0.571 * CheckCurrentAL());
            cal.setText(String.valueOf(result_calories));
        }
    }


    public double CheckCurrentAL(){
        switch (selected_activity_level) {
            case "Sedentary: little or no exercise":
                return 1.0;
            case "Light: exercise 1 to 3 times a week":
                return 1.2;
            case "Moderate: exercise 4 to 5 times a week":
                return 1.345;
            case "Active: daily ex. or INTENSE exercise 3 times a week":
                return 1.465;
            case "Very Active: INTENSE exercise 6 to 7 times a week":
                return 1.55;
        }
        return 0;
    }

    public double CheckCurrentWL(){
        switch (selected_weight_loss) {
            case "Maintain Weight":
            case "Increase Muscles!":
                return 1.0;
            case "Mild Weight Loss (0.25 kg per week)":
                return 0.87;
            case "Weight Loss (0.5 kg per week)":
                return 0.75;
            case "Extreme Weight Loss (1 kg per week)":
                return 0.56;
        }
        return 0;
    }

}