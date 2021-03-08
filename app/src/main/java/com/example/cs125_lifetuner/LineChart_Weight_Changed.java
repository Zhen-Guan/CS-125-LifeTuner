package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class LineChart_Weight_Changed extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart__weight__changed);

        LineChartView lineChartView = findViewById(R.id.weight_changed);


        ArrayList<String> yAxisData = new ArrayList<String>();
        ArrayList<String> axisData = new ArrayList<String>();


        for (int j = 0; j < info_gather.weight_list.size(); j++){
            yAxisData.add(info_gather.weight_list.get(j));
            axisData.add(String.valueOf(j));
        }


        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));


        for(int i = 0; i < axisData.size(); i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData.get(i)));
        }

        for (int i = 0; i < yAxisData.size(); i++){
            yAxisValues.add(new PointValue(i, Float.parseFloat(yAxisData.get(i))));
        }

        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        axis.setTextSize(12);
        axis.setName("Time");

        axis.setTextColor(Color.parseColor("#03A9F4"));
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(12);
        yAxis.setName("Weight");

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top =110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);


    }
}