package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChart_Calorie_Intake extends AppCompatActivity {


    // x axis -> times
    // bar chart -> elements in the arraylist
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart__calorie__intake);

        BarChart barChart = findViewById(R.id.BarChartIntake);

        ArrayList<BarEntry> calorie = new ArrayList<>();
        ArrayList<Double> CalorieList = new ArrayList<Double>();
        ArrayList<String> date = new ArrayList<String>();
        //y axis data
//        CalorieList.add(700.50);
//        CalorieList.add(800.50);
//        CalorieList.add(900.50);
//        CalorieList.add(1000.50);
//        CalorieList.add(700.50);
//


        for (int i = 0; i < Result_Exercise.record_calories.size(); i++){
            date.add(String.valueOf(i));
            CalorieList.add(Result_Exercise.record_calories.get(i));
        }
        //x axis data
//        date.add("2021-02-01");
//        date.add("2021-02-02");
//        date.add("2021-02-03");
//        date.add("2021-02-04");
//        date.add("2021-02-06");

        for (int i = 0; i < CalorieList.size(); i++) {
            calorie.add(new BarEntry(i,CalorieList.get(i).floatValue()));
        }

        BarDataSet barDataSet = new BarDataSet(calorie, "Calorie Intake");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(date));
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getDescription().setText("");
        barChart.animateY(2000);


    }
}