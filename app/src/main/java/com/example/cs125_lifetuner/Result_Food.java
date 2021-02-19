package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Result_Food extends AppCompatActivity {
    Button button;
    //Button button_map;
    ListView morning_list, lunch_dinner_list;
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
        setContentView(R.layout.activity_result__food);

        Intent intent = getIntent();
        result_calories = Integer.parseInt(intent.getStringExtra("result_calories"));

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
        //对于早餐和中&晚餐食物推荐的卡路里分配：
        //  早餐占一天摄入的25% （采用400-600-600模型）“https://www.bbc.com/zhongwen/simp/science-43306511#:~:text=%E5%AE%83%E5%BB%BA%E8%AE%AE%E6%88%90%E5%B9%B4%E4%BA%BA%E6%AF%8F%E5%A4%A9,%E5%8D%A1%E7%9A%84%E6%91%84%E5%85%A5%E9%87%8F%E3%80%82”
        //  中晚餐的占比相当，各占37.5% 所以我们以总卡路里的37.5%来推荐中&晚餐，用户则可在中午和晚上看到同样的食谱来酌情，根据情况选择
        //  但每餐所拥有的卡路里值推荐过大，因为一般来说一个人一餐的量和种类并不单一，所以酌情进行小改。
        morning_list = findViewById(R.id.morning_list);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Result_Food.this);
        List<FoodModel> allFoodMorning = dataBaseHelper.getAllFoodMorning((int) (0.25 * result_calories / 1.86));
        ArrayAdapter foodArrayAdapterMorning = new ArrayAdapter<FoodModel>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodMorning);
        morning_list.setAdapter(foodArrayAdapterMorning);


        //show lunch & dinner database
        lunch_dinner_list= findViewById((R.id.lunch_dinner_list));
        List<FoodModel> allFoodLunchDinner = dataBaseHelper.getAllFoodLunchDinner( (int) (0.35 * result_calories / 1.5));
        ArrayAdapter foodArrayAdapterLunchDinner = new ArrayAdapter<FoodModel>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodLunchDinner);
        lunch_dinner_list.setAdapter(foodArrayAdapterLunchDinner);


        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //


    }



    public void openLunchpage(){
        Intent intent = new Intent(this, Result_Exercise.class);
        intent.putExtra("result_calories", String.valueOf(result_calories));
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