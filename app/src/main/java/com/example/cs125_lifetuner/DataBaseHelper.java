package com.example.cs125_lifetuner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String FOOD_ID = "FOOD_ID";
    public static final String FOOD_NAME = "FOOD_NAME";
    public static final String FOOD_TIME = "FOOD_TIME";
    public static final String FOOD_CALORIES = "FOOD_CALORIES";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "food.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FOOD_TABLE + " (" + FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FOOD_NAME + " TEXT, " + FOOD_TIME + " TEXT, " + FOOD_CALORIES + " INT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<FoodModel> getAllFoodMorning(int calories){
        List<FoodModel> returnList = new ArrayList<>();
        //getdata from database
        String queryString = "SELECT * FROM " + FOOD_TABLE + " WHERE FOOD_TIME LIKE \"%m%\" AND FOOD_CALORIES < " + calories + " ORDER BY FOOD_CALORIES DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int foodID = cursor.getInt(0);
                String foodName = cursor.getString(1);
                String foodTime = cursor.getString(2);
                int foodCalories = cursor.getInt(3);

                FoodModel newFood = new FoodModel(foodID, foodName, foodTime, foodCalories);
                returnList.add(newFood);
            }
            while(cursor.moveToNext());

        }

        cursor.close();

        return returnList;
    }

    public List<FoodModel> getAllFoodLunchDinner(int calories){
        List<FoodModel> returnList = new ArrayList<>();
        //getdata from database
        String queryString = "SELECT * FROM " + FOOD_TABLE + " WHERE FOOD_TIME LIKE \"%ae\" AND FOOD_CALORIES < " + calories + " ORDER BY FOOD_CALORIES DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int foodID = cursor.getInt(0);
                String foodName = cursor.getString(1);
                String foodTime = cursor.getString(2);
                int foodCalories = cursor.getInt(3);

                FoodModel newFood = new FoodModel(foodID, foodName, foodTime, foodCalories);
                returnList.add(newFood);
            }
            while(cursor.moveToNext());

        }

        cursor.close();
        return returnList;
    }


    //暂时无用
    public boolean addOne(FoodModel foodModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("FOOD_NAME", foodModel.getfoodName());
        cv.put("FOOD_TIME", foodModel.getfoodTime());
        cv.put("FOOD_CALORIES", foodModel.getfoodCalories());

        long insert = db.insert(FOOD_TABLE, null, cv);
        return insert != -1;
    }
}
