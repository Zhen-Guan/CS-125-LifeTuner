package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MainActivity extends AppCompatActivity {


    // creating a variable for
    // our Firebase Database.

    // creating a variable for our
    // Database Reference for Firebase.
    private DatabaseReference mReference;
    private TextView retriveTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retriveTV = findViewById(R.id.hello_world);

        // calling method
        // for getting data.
        getdata();

        //背景代码 每次建立新的activity都可以把这一段复制到onCreate方法中
//        LinearLayout background_Layout = (LinearLayout) findViewById(R.id.main_container);
//        AnimationDrawable animationDrawable = (AnimationDrawable) background_Layout.getBackground();
//        animationDrawable.setEnterFadeDuration(4000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();
        //
    }

    private void getdata() {
        mReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference content = mReference.child("content");

        content.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value = snapshot.getValue(String.class);
                // after getting the value we are setting
                // our value to our text view in below line.
                retriveTV.setText(value);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}