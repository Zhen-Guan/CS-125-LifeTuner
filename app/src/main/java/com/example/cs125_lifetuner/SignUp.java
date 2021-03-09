package com.example.cs125_lifetuner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Button button_register;
    String gender;
    EditText text_username, text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        ((RadioButton) findViewById(R.id.radio_male)).setChecked(true);
//        ((RadioButton) findViewById(R.id.radio_female)).setChecked(false);
        text_username = findViewById(R.id.new_username);
        text_password = findViewById(R.id.new_password);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        button_register = findViewById(R.id.register);
        button_register.setOnClickListener(v -> {

            if ( !((RadioButton) findViewById(R.id.radio_female)).isChecked() && !((RadioButton) findViewById(R.id.radio_male)).isChecked()){
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();

            }

            else{
                try {
                    gender = ((RadioButton) findViewById(R.id.radio_male)).isChecked() ? "male" : "female";
                    String username = text_username.getText().toString();
                    String password = text_password.getText().toString();

                    if (username.equals("")) {
                        Toast.makeText(SignUp.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    }
                    else if (password.equals("")) {
                        Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Intent intent = new Intent(SignUp.this, info_gather.class);
                        intent.putExtra("gender", gender);

                        //save into database
                        UserModel userModel = new UserModel(-1, username, password, gender);
                        if (dataBaseHelper.SearchIfUsernameExistOnce(username) == 1){
                            Toast.makeText(this, "username already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        };
                        dataBaseHelper.addOne(userModel);
                        Toast.makeText(this, "Sign up Successfully. Welcome, " + username +"!", Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                    }
                } catch (Exception e){
                    Toast.makeText(SignUp.this, "Please enter all blanks", Toast.LENGTH_SHORT).show();
                }
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

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_female:
//                if (checked)
//                    gender = "female";
//                Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.radio_male:
//                if (checked)
//                    gender = "male";
//                break;
//        }
//    }


}