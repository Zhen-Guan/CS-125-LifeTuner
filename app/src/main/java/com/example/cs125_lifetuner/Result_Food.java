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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Result_Food extends AppCompatActivity {
    Button button;
    ListView morning_list, lunch_dinner_list;
    int result_calories;
    List<FoodModel> allFoodMorning, allFoodLunchDinner;


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

        //Get list view
        morning_list = findViewById(R.id.morning_list);
        lunch_dinner_list= findViewById((R.id.lunch_dinner_list));

        //Create list array for Breakfast/ lunch&dinner
        allFoodMorning = new ArrayList<>();
        allFoodLunchDinner = new ArrayList<>();

        //Get morning diet Array
        try{
            JSONObject jsonObject = new JSONObject(getFoodArray());
            JSONArray jsonArray = jsonObject.getJSONArray("food");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String foodName = object.getString("FOOD_NAME");
                String foodTime = object.getString("FOOD_TIME");
                int foodCalories = object.getInt("FOOD_CALORIES");

                if (foodCalories > 0.25 * result_calories / 1.86){
                    continue;
                }

                FoodModel newFood;
                if (foodTime.equals("m") || foodTime.equals("mae") ){
                    newFood = new FoodModel(i, foodName, foodTime, foodCalories);
                }
                else {
                    continue;
                }
                allFoodMorning.add(newFood);
            }
            //Descending Sort
            Collections.sort(allFoodMorning, (lhs, rhs) -> Integer.compare(rhs.getfoodCalories(), lhs.getfoodCalories()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Get lunch & dinner diet Array
        try{
            JSONObject jsonObject = new JSONObject(getFoodArray());
            JSONArray jsonArray = jsonObject.getJSONArray("food");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String foodName = object.getString("FOOD_NAME");
                String foodTime = object.getString("FOOD_TIME");
                int foodCalories = object.getInt("FOOD_CALORIES");

                if (foodCalories > 0.35 * result_calories / 1.5){
                    continue;
                }

                FoodModel newFood;
                if (foodTime.equals("ae") || foodTime.equals("mae") ){
                    newFood = new FoodModel(i, foodName, foodTime, foodCalories);
                }
                else {
                    continue;
                }
                allFoodLunchDinner.add(newFood);
            }
            //Descending Sort
            Collections.sort(allFoodLunchDinner, (lhs, rhs) -> Integer.compare(rhs.getfoodCalories(), lhs.getfoodCalories()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Show morning database
        //对于早餐和中&晚餐食物推荐的卡路里分配：
        //  早餐占一天摄入的25% （采用400-600-600模型）“https://www.bbc.com/zhongwen/simp/science-43306511#:~:text=%E5%AE%83%E5%BB%BA%E8%AE%AE%E6%88%90%E5%B9%B4%E4%BA%BA%E6%AF%8F%E5%A4%A9,%E5%8D%A1%E7%9A%84%E6%91%84%E5%85%A5%E9%87%8F%E3%80%82”
        //  中晚餐的占比相当，各占37.5% 所以我们以总卡路里的37.5%来推荐中&晚餐，用户则可在中午和晚上看到同样的食谱来酌情，根据情况选择
        //  但每餐所拥有的卡路里值推荐过大，因为一般来说一个人一餐的量和种类并不单一，所以酌情进行小改。
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(Result_Food.this);
//        List<FoodModel> allFoodMorning = dataBaseHelper.getAllFoodMorning((int) (0.25 * result_calories / 1.86));

        //Show the elements in the Breakfast array
        ArrayAdapter foodArrayAdapterMorning = new ArrayAdapter<>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodMorning);
        morning_list.setAdapter(foodArrayAdapterMorning);

        //show lunch & dinner database
//        List<FoodModel> allFoodLunchDinner = dataBaseHelper.getAllFoodLunchDinner( (int) (0.35 * result_calories / 1.5));

        //Show the elements in the LunchDinner array
        ArrayAdapter foodArrayAdapterLunchDinner = new ArrayAdapter<>(Result_Food.this, android.R.layout.simple_list_item_1, allFoodLunchDinner);
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

    public String getFoodArray(){
        return "{\n" +
                "  \"food\" : [ {\n" +
                "    \"FOOD_CALORIES\" : 140,\n" +
                "    \"FOOD_NAME\" : \"Bagel\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 225,\n" +
                "    \"FOOD_NAME\" : \"Biscuit digestives\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 125,\n" +
                "    \"FOOD_NAME\" : \"Jaffa cake\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 96,\n" +
                "    \"FOOD_NAME\" : \"Bread white (thick slice)\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 88,\n" +
                "    \"FOOD_NAME\" : \"Bread wholemeal (thick)\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 250,\n" +
                "    \"FOOD_NAME\" : \"Chapatis\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 130,\n" +
                "    \"FOOD_NAME\" : \"Cornflakes\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 48,\n" +
                "    \"FOOD_NAME\" : \"Crackerbread\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 75,\n" +
                "    \"FOOD_NAME\" : \"Cream crackers\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 93,\n" +
                "    \"FOOD_NAME\" : \"Crumpets\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 320,\n" +
                "    \"FOOD_NAME\" : \"Fruit Flapjacks\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 238,\n" +
                "    \"FOOD_NAME\" : \"Boiled Macaroni\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 195,\n" +
                "    \"FOOD_NAME\" : \"Muesli\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 300,\n" +
                "    \"FOOD_NAME\" : \"Naan bread\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 175,\n" +
                "    \"FOOD_NAME\" : \"Boiled Noodles\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 330,\n" +
                "    \"FOOD_NAME\" : \"Normal Boiled Pasta\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 315,\n" +
                "    \"FOOD_NAME\" : \"Wholemeal Boiled Pasta\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 193,\n" +
                "    \"FOOD_NAME\" : \"Porridge oats\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Boiled Potatoes\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 420,\n" +
                "    \"FOOD_NAME\" : \"Roasted Potatoes\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 420,\n" +
                "    \"FOOD_NAME\" : \"White Boiled Rice\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 500,\n" +
                "    \"FOOD_NAME\" : \"Egg fried Rice\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 405,\n" +
                "    \"FOOD_NAME\" : \"Brown Rice\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 56,\n" +
                "    \"FOOD_NAME\" : \"Rice cakes\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 74,\n" +
                "    \"FOOD_NAME\" : \"Ryvita Multi grain\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Ryvita + seed & Oats\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 303,\n" +
                "    \"FOOD_NAME\" : \"Boiled Spaghetti\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 360,\n" +
                "    \"FOOD_NAME\" : \"Anchovies tinned\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 375,\n" +
                "    \"FOOD_NAME\" : \"Bacon average fried\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 235,\n" +
                "    \"FOOD_NAME\" : \"Bacon average grilled\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 328,\n" +
                "    \"FOOD_NAME\" : \"Roasted Beef\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 385,\n" +
                "    \"FOOD_NAME\" : \"Beef burgers frozen\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 250,\n" +
                "    \"FOOD_NAME\" : \"Chicken\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 50,\n" +
                "    \"FOOD_NAME\" : \"Cockles\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Fresh Cod\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 440,\n" +
                "    \"FOOD_NAME\" : \"Cod chip shop food\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Fresh Crab\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 480,\n" +
                "    \"FOOD_NAME\" : \"Roasted Duck\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 240,\n" +
                "    \"FOOD_NAME\" : \"Fish cake\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 185,\n" +
                "    \"FOOD_NAME\" : \"Fish fingers\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 360,\n" +
                "    \"FOOD_NAME\" : \"Gammon\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Fresh Haddock\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 220,\n" +
                "    \"FOOD_NAME\" : \"Fresh Halibut\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 36,\n" +
                "    \"FOOD_NAME\" : \"Ham (3 slice)\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 325,\n" +
                "    \"FOOD_NAME\" : \"Grilled Fresh Herring\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 240,\n" +
                "    \"FOOD_NAME\" : \"Kidney\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 145,\n" +
                "    \"FOOD_NAME\" : \"Kipper\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Liver\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Liver pate\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 650,\n" +
                "    \"FOOD_NAME\" : \"Roasted Lamb\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 250,\n" +
                "    \"FOOD_NAME\" : \"Boiled Lobster\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 540,\n" +
                "    \"FOOD_NAME\" : \"Luncheon meat\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 360,\n" +
                "    \"FOOD_NAME\" : \"Mackeral\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 90,\n" +
                "    \"FOOD_NAME\" : \"Mussels\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 260,\n" +
                "    \"FOOD_NAME\" : \"Roasted Pheasant\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 140,\n" +
                "    \"FOOD_NAME\" : \"Tinned Pilchards\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 180,\n" +
                "    \"FOOD_NAME\" : \"Prawns\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 420,\n" +
                "    \"FOOD_NAME\" : \"Pork\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 530,\n" +
                "    \"FOOD_NAME\" : \"Pork pie\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 270,\n" +
                "    \"FOOD_NAME\" : \"Rabbit\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 255,\n" +
                "    \"FOOD_NAME\" : \"Fresh Salmon\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 270,\n" +
                "    \"FOOD_NAME\" : \"Sardines tinned in oil\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Sardines in tomato sauce\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 430,\n" +
                "    \"FOOD_NAME\" : \"Fried Sausage pork\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 270,\n" +
                "    \"FOOD_NAME\" : \"Grilled Sausage pork\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 460,\n" +
                "    \"FOOD_NAME\" : \"Sausage roll\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 540,\n" +
                "    \"FOOD_NAME\" : \"Scampi fried in oil\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 570,\n" +
                "    \"FOOD_NAME\" : \"Steak & kidney pie\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 280,\n" +
                "    \"FOOD_NAME\" : \"Taramasalata\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Trout fresh\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Tuna tinned water\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Tuna tinned oil\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 320,\n" +
                "    \"FOOD_NAME\" : \"Turkey\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 420,\n" +
                "    \"FOOD_NAME\" : \"Veal\",\n" +
                "    \"FOOD_TIME\" : \"ae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 170,\n" +
                "    \"FOOD_NAME\" : \"Baked beans\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 180,\n" +
                "    \"FOOD_NAME\" : \"Boiled Beans\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 27,\n" +
                "    \"FOOD_NAME\" : \"Broccoli\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 15,\n" +
                "    \"FOOD_NAME\" : \"Boiled Cabbage\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 16,\n" +
                "    \"FOOD_NAME\" : \"Boiled Carrot\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 20,\n" +
                "    \"FOOD_NAME\" : \"Boiled Caulifrower\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 5,\n" +
                "    \"FOOD_NAME\" : \"Boiled Celery\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 8,\n" +
                "    \"FOOD_NAME\" : \"Courgette\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 3,\n" +
                "    \"FOOD_NAME\" : \"Cucumber\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Dates\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 10,\n" +
                "    \"FOOD_NAME\" : \"Boiled Leek\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 150,\n" +
                "    \"FOOD_NAME\" : \"Boiled Lentils\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 4,\n" +
                "    \"FOOD_NAME\" : \"Lettuce\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 12,\n" +
                "    \"FOOD_NAME\" : \"Boiled Mushrooms\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 180,\n" +
                "    \"FOOD_NAME\" : \"Fried Mushrooms\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 14,\n" +
                "    \"FOOD_NAME\" : \"Boiled Onion\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 49,\n" +
                "    \"FOOD_NAME\" : \"One red Onion\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 134,\n" +
                "    \"FOOD_NAME\" : \"Fried Onion\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Peas\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Dried & Boiled Peas\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 8,\n" +
                "    \"FOOD_NAME\" : \"Spinach\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 95,\n" +
                "    \"FOOD_NAME\" : \"Sweetcorn\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 30,\n" +
                "    \"FOOD_NAME\" : \"Tomato\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 17,\n" +
                "    \"FOOD_NAME\" : \"Tomato cherry (3)\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 85,\n" +
                "    \"FOOD_NAME\" : \"Tomato puree\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 5,\n" +
                "    \"FOOD_NAME\" : \"Watercress\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 110,\n" +
                "    \"FOOD_NAME\" : \"Average Cheese (25g)\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 130,\n" +
                "    \"FOOD_NAME\" : \"Reduced Fat Cheedar\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Cheese Spreads\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 45,\n" +
                "    \"FOOD_NAME\" : \"Low Fat Cottage Cheese\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 55,\n" +
                "    \"FOOD_NAME\" : \"Cottage cheese\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 310,\n" +
                "    \"FOOD_NAME\" : \"Cream cheese\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 148,\n" +
                "    \"FOOD_NAME\" : \"Fresh Half Cream\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 460,\n" +
                "    \"FOOD_NAME\" : \"Single Fresh Cream\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 660,\n" +
                "    \"FOOD_NAME\" : \"Double Fresh Cream\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 210,\n" +
                "    \"FOOD_NAME\" : \"Custard\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 90,\n" +
                "    \"FOOD_NAME\" : \"Egg (1)\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 120,\n" +
                "    \"FOOD_NAME\" : \"Fried Egg\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 125,\n" +
                "    \"FOOD_NAME\" : \"Fromage frais\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 200,\n" +
                "    \"FOOD_NAME\" : \"Ice cream\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 175,\n" +
                "    \"FOOD_NAME\" : \"Whole Milk\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 125,\n" +
                "    \"FOOD_NAME\" : \"Semi-skimmed Milk\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 95,\n" +
                "    \"FOOD_NAME\" : \"Skimmed Milk\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 90,\n" +
                "    \"FOOD_NAME\" : \"Soya Milk\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 120,\n" +
                "    \"FOOD_NAME\" : \"Mousse flavored\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 340,\n" +
                "    \"FOOD_NAME\" : \"Omelette with cheese\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 335,\n" +
                "    \"FOOD_NAME\" : \"Trifle with cream\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 90,\n" +
                "    \"FOOD_NAME\" : \"Natural Yogurt\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 70,\n" +
                "    \"FOOD_NAME\" : \"Reduced Fat Yogurt\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 112,\n" +
                "    \"FOOD_NAME\" : \"Butter\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 325,\n" +
                "    \"FOOD_NAME\" : \"Bombay mix\",\n" +
                "    \"FOOD_TIME\" : \"m\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 250,\n" +
                "    \"FOOD_NAME\" : \"Chocolate\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 125,\n" +
                "    \"FOOD_NAME\" : \"Corn snack\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 1000,\n" +
                "    \"FOOD_NAME\" : \"Crisps (chips US) average\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 42,\n" +
                "    \"FOOD_NAME\" : \"Honey\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 175,\n" +
                "    \"FOOD_NAME\" : \"Jam\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 750,\n" +
                "    \"FOOD_NAME\" : \"Lard\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 125,\n" +
                "    \"FOOD_NAME\" : \"Low fat spread\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 175,\n" +
                "    \"FOOD_NAME\" : \"Margarine\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 370,\n" +
                "    \"FOOD_NAME\" : \"Mars bar\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 135,\n" +
                "    \"FOOD_NAME\" : \"Oils (1 Spoon)\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 310,\n" +
                "    \"FOOD_NAME\" : \"Popcorn average\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 35,\n" +
                "    \"FOOD_NAME\" : \"White Sugar\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 150,\n" +
                "    \"FOOD_NAME\" : \"Boiled Sweets\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  }, {\n" +
                "    \"FOOD_CALORIES\" : 320,\n" +
                "    \"FOOD_NAME\" : \"Toffee\",\n" +
                "    \"FOOD_TIME\" : \"mae\"\n" +
                "  } ]\n" +
                "}\n";
    }

}