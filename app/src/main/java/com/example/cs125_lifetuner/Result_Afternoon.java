package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Result_Afternoon extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__afternoon);
        button = (Button) findViewById(R.id.button_to_dinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDinnerpage();
            }
        });
    }

    public void openDinnerpage(){
        Intent intent = new Intent(this, Result_Evening.class);
        startActivity(intent);
    }
}


