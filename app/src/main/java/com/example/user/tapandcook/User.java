package com.example.user.tapandcook;


//get user information
public class User {

    private int userID;
    private String username;
    private String password;
    private String email;


    //constructors
    public User(String username, String password, String email)
    {

        this.username=username;
        this.password=password;
        this.email=email;

    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


}
