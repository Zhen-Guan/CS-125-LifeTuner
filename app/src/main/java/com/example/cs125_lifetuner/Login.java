package com.example.cs125_lifetuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference foodReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootNode = FirebaseDatabase.getInstance("https://cs125-group31-default-rtdb.firebaseio.com/");
        foodReference = rootNode.getReference("food");

        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoPage();
            }
        });

        Button signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupPage();
            }
        });

        ArrayList<FoodModel> foodlist = new ArrayList<>();
        foodReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot foodSnap : snapshot.getChildren()){
                    FoodModel food = foodSnap.getValue(FoodModel.class);
                    Toast.makeText(Login.this, "1", Toast.LENGTH_SHORT).show();
                    foodlist.add(food);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(this, foodlist.toString(), Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, foodlist.toString(), Toast.LENGTH_SHORT).show();
    }

    public void openInfoPage(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

    public void openSignupPage(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }


}