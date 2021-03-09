package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class info_gather extends AppCompatActivity {

    EditText weight, height, age;
    Button btn_enter;
    String gender;
    public static ArrayList<String> weight_list = new ArrayList<String>();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gather);

//        try {
//            Intent oldintent = getIntent();
//            gender = oldintent.getStringExtra("gender");
//            if (gender.equals("male")) {
//                ((RadioButton) findViewById(R.id.radio_male)).setChecked(true);
//                ((RadioButton) findViewById(R.id.radio_female)).setChecked(false);
//            } else if (gender.equals("female")) {
//                ((RadioButton) findViewById(R.id.radio_male)).setChecked(false);
//                ((RadioButton) findViewById(R.id.radio_female)).setChecked(true);
//            }
//        }
//        catch (Exception e){
//            Toast.makeText(info_gather.this, "Error occurs", Toast.LENGTH_SHORT).show();
//        }

        //先找到所有view
        weight =findViewById(R.id.weight);
        height = findViewById(R.id.height);
        age = findViewById(R.id.age);
        btn_enter = findViewById(R.id.enter);

        Intent oldintent = getIntent();
        gender = oldintent.getStringExtra("gender");

        //采集玩家数据完毕后的enter按钮
        btn_enter.setOnClickListener(v -> {

            try {
                int w = Integer.parseInt(weight.getText().toString().trim());
                int h = Integer.parseInt(height.getText().toString().trim());
                int a = Integer.parseInt(age.getText().toString().trim());
                weight_list.add(String.valueOf(w));

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

                    // we use Mifflin-St Jeor Equation
                    final double d = (gender.equals("male")) ? (10*w + 6.25*h - 5*a + 5) : (10*w + 6.25*h - 5*a -161);
                    // we use J. D. Robinson Formula (1983)
                    double target_weight = (gender.equals("male")) ? ((h > 152) ? 52 + (h-152)*1.9/2.5 : 52) : (h > 152) ? (49 + (h-152)*1.7/2.5) : 49;

                    String bmr = String.valueOf(Math.round(d));
                    String target = String.valueOf(target_weight);
                    String calories = String.valueOf(Math.round((d) * 1.2));

                    intent.putExtra("current_weight", String.valueOf(w));
                    intent.putExtra("bmr_value", bmr);
                    intent.putExtra("target_weight", target);
                    intent.putExtra("calories", calories);
                    startActivity(intent);
                 }
            } catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(info_gather.this, "Please enter all blanks", Toast.LENGTH_SHORT).show();
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_female:
                if (checked)
                    gender = "female";
                    break;
            case R.id.radio_male:
                if (checked)
                    gender = "male";
                    break;
        }
    }



    public void openProfilePage(){
        Intent intent = new Intent(this, Profile_page.class);
        intent.putExtra("gender", gender);
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

    public void openProfile(){
        Intent intent = new Intent(this, info_gather.class);
        intent.putExtra("gender", gender);
        startActivity(intent);
    }
}
