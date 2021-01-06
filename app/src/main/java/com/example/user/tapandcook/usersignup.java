package com.example.user.tapandcook;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class usersignup extends AppCompatActivity {
    DBHandler dbHandler;
    EditText username;
    EditText Email;
    EditText Password;
    EditText confirmpassword;
    Button signup;
    String name;
    String email;
    String password;
    String confirmpass;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHandler=new DBHandler(this);
        username=findViewById(R.id.sign_up_id);
        Email=findViewById(R.id.useremail);
        Password=findViewById(R.id.sign_up_password);
        confirmpassword=findViewById(R.id.confirmpassword);
        signup=findViewById(R.id.addaccount);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=username.getText().toString().trim();
                email=Email.getText().toString().trim();
                password=Password.getText().toString().trim();
                confirmpass=confirmpassword.getText().toString().trim();
                Username=username.getText().toString().trim();

                //validate name, email, password
                    if (validation(name, email, password, confirmpass) == true) {

                        //if email doesn't exist: this is a new account
                           if (dbHandler.checkHandler(email) == false) {
                               dbHandler.insertHandler(new User(name, password, email));
                               Snackbar.make(signup,"Register successfully!",Snackbar.LENGTH_LONG).show();
                           }
                         else{
                            Toast.makeText(usersignup.this, "User with this email already exists", Toast.LENGTH_LONG).show();
                       }
                    }
                else{
                    Toast.makeText(usersignup.this,"Register unsuccessfully!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Boolean validation(String name,String email,String password,String confirmpass){
        Boolean valid=false;

        //notify length of userID and password must at least 6 characters
        if(name.length()<5&&password.length()<5){
            valid=false;
            Toast.makeText(usersignup.this,"Username and password must contain at least 6 characters",Toast.LENGTH_LONG).show();
        }

        //notify either one field is empty
        else if(name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmpass.isEmpty()){
            valid=false;
            Toast.makeText(usersignup.this,"Please Enter username,email and password!",Toast.LENGTH_LONG).show();
        }

        //notify password not match with confirmation password
        else if (!password.equals(confirmpass)){
            valid=false;
            Toast.makeText(usersignup.this,"Password are not same!",Toast.LENGTH_LONG).show();
        }

        //notify format of email is invalid
        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()==false){
            valid=false;
            Toast.makeText(usersignup.this,"Please enter valid email address!",Toast.LENGTH_LONG).show();
        }
        else{
            valid=true;
        }

        return valid;
    }
}
