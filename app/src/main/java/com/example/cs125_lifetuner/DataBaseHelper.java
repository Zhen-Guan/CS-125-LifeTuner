package com.example.cs125_lifetuner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_USERNAME = "USER_USERNAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_GENDER = "USER_GENDER";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_USERNAME + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_GENDER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public int SearchIfUsernameExistOnce(final String username){
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_USERNAME = '" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int num = cursor.getCount();
        cursor.close();
        return num;
    }

    public boolean CheckIfUsernameMatchesPassword(final String username, final String password){

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_USERNAME = '" + username + "' AND USER_PASSWORD = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int num = cursor.getCount();
        cursor.close();
        return num == 1;
    }

    public String GetGender(final String username, final String password){
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE USER_USERNAME = '" + username + "' AND USER_PASSWORD = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        String gender = cursor.getString(3);
        cursor.close();
        return gender;
    }


//    public List<FoodModel> getAllFoodMorning(int calories){
//        List<FoodModel> returnList = new ArrayList<>();
//        //getdata from database
//        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE FOOD_TIME LIKE \"%m%\" AND FOOD_CALORIES < " + calories + " ORDER BY FOOD_CALORIES DESC";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if (cursor.moveToFirst()){
//            do{
//                int foodID = cursor.getInt(0);
//                String foodName = cursor.getString(1);
//                String foodTime = cursor.getString(2);
//                int foodCalories = cursor.getInt(3);
//
//                FoodModel newFood = new FoodModel(foodID, foodName, foodTime, foodCalories);
//                returnList.add(newFood);
//            }
//            while(cursor.moveToNext());
//
//        }
//
//        cursor.close();
//
//        return returnList;
//    }
//
//    public List<FoodModel> getAllFoodLunchDinner(int calories){
//        List<FoodModel> returnList = new ArrayList<>();
//        //getdata from database
//        String queryString = "SELECT * FROM " + FOOD_TABLE + " WHERE FOOD_TIME LIKE \"%ae\" AND FOOD_CALORIES < " + calories + " ORDER BY FOOD_CALORIES DESC";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if (cursor.moveToFirst()){
//            do{
//                int foodID = cursor.getInt(0);
//                String foodName = cursor.getString(1);
//                String foodTime = cursor.getString(2);
//                int foodCalories = cursor.getInt(3);
//
//                FoodModel newFood = new FoodModel(foodID, foodName, foodTime, foodCalories);
//                returnList.add(newFood);
//            }
//            while(cursor.moveToNext());
//
//        }
//
//        cursor.close();
//        return returnList;
//    }


    //暂时无用
    public boolean addOne(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("USER_USERNAME", userModel.getUsername());
        cv.put("USER_PASSWORD", userModel.getPassword());
        cv.put("USER_GENDER", userModel.getGender());

        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }
}
