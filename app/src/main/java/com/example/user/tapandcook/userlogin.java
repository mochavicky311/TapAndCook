package com.example.user.tapandcook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class userlogin extends AppCompatActivity {
    Button login;
    Button signup;
    EditText username;
    EditText password;
    String Password;
    String name;
    DBHandler dbHandler;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        dbHandler=new DBHandler(this);
        login=findViewById(R.id.loginbutton);
        signup=findViewById(R.id.signupbutton);
        username=findViewById(R.id.useridtext);
        password=findViewById(R.id.passwordtext);

        //set event of login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=username.getText().toString().trim();
                Password=password.getText().toString().trim();

                //if input is not null
                if (name.length() > 0 && Password.length() > 0) {

                    //if input is matched to database's result: correct password and userID
                    if (dbHandler.loginHandler(name, Password)) {
                        Toast.makeText(userlogin.this, "Successfully Logged in!", Toast.LENGTH_LONG).show();
                        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("name",name);
                        editor.commit();
                        startActivity(new Intent(userlogin.this, homepage.class));

                        finish();
                    }
                    else {
                        Toast.makeText(userlogin.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                    }
                        dbHandler.close();
                }
                else
                    Toast.makeText(userlogin.this, "Please Enter username and password!", Toast.LENGTH_LONG).show();

        }});

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(userlogin.this,usersignup.class));
               }
        });


    }

}

