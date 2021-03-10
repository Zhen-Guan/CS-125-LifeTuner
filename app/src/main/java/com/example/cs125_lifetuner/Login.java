package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button button_login;
    EditText text_username, text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        text_username = findViewById(R.id.username);
        text_password = findViewById(R.id.password);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        button_login = (Button) findViewById(R.id.login_button);
        button_login.setOnClickListener(v -> {



        try {
            String username = text_username.getText().toString();
            String password = text_password.getText().toString();

            if (username.equals("")) {
                Toast.makeText(Login.this, "Please enter username", Toast.LENGTH_SHORT).show();
            }
            else if (password.equals("")) {
                Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
            else {

                Intent intent = new Intent(Login.this, info_gather.class);


                //check if username exists: if not exist
                if (dataBaseHelper.SearchIfUsernameExistOnce(username) != 1){
                    Toast.makeText(this, "username doesn't exist.", Toast.LENGTH_SHORT).show();
                } //if exist, check password match: if password not match
                else{
                    if (!dataBaseHelper.CheckIfUsernameMatchesPassword(username, password)){
                        Toast.makeText(this, "password is incorrect, Please re-enter.", Toast.LENGTH_SHORT).show();
                    }// if password matches
                    else{
                        Toast.makeText(this, "Login Successfully. Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                        intent.putExtra("gender", dataBaseHelper.GetGender(username, password));
                        startActivity(intent);
                    }
                }



            }
        } catch (Exception e){
            Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        });

        Button signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupPage();
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

    public void openInfoPage(){
        Intent intent = new Intent(this, info_gather.class);
        startActivity(intent);
    }

    public void openSignupPage(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }


}