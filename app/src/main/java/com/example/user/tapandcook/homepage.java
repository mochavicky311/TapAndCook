package com.example.user.tapandcook;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Objects;

public class homepage extends AppCompatActivity {
DrawerLayout drawerlayout;
ActionBarDrawerToggle toggle;
NavigationView navigationview;
FrameLayout frame;
boolean connection=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        frame=findViewById(R.id.frame);
        drawerlayout=findViewById(R.id.drawer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toggle=new ActionBarDrawerToggle(this,drawerlayout, R.string.open, R.string.close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();


        //check internet connection of device
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){
            connection=true;
        }
        else connection=false;

        //notify user searching is not available without network access
        if (connection==false){
            Snackbar internet=Snackbar.make(frame,"No Internet Connection\n"+"Searching are not available",
                    Snackbar.LENGTH_SHORT );
            internet.show();
        }

        //set the homepage layout default to main page fragment
        main_fragment f1=new main_fragment();
        FragmentManager fm1=getFragmentManager();
        fm1.beginTransaction().replace(R.id.frame,f1).commit();


        //indicate the functions performed after clicked to the menu item in navigation drawer
        navigationview=findViewById(R.id.navigate);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:            //main menu
                        item.setChecked(true);
                        navigationview.getMenu().getItem(1).setChecked(false);
                        drawerlayout.closeDrawer(GravityCompat.START);
                        main_fragment f1=new main_fragment();
                        FragmentManager fm1=getFragmentManager();
                        fm1.beginTransaction().replace(R.id.frame,f1).commit();
                        break;

                    case R.id.item2:            //bookmark menu
                        item.setChecked(true);
                        navigationview.getMenu().getItem(0).setChecked(false);
                        drawerlayout.closeDrawer(GravityCompat.START);
                       bookmark f2=new bookmark();
                        FragmentManager fm2=getFragmentManager();
                        fm2.beginTransaction().replace(R.id.frame,f2).commit();
                        finish();
                        break;

                    case R.id.item3:            //log out menu

                        AlertDialog.Builder logoutbuilder = new AlertDialog.Builder(homepage.this);
                        logoutbuilder.setTitle("Log Out Alert");
                        logoutbuilder.setMessage("Are You Sure You Want to Log Out?");
                        logoutbuilder.setCancelable(false);
                        logoutbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(homepage.this,userlogin.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "You've successfully Log Out", Toast.LENGTH_SHORT).show();
                            }
                        });

                        logoutbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(homepage.this,homepage.class));
                                finish();
                            }
                        });
                        AlertDialog logout=logoutbuilder.create();
                        logout.show();

                        break;

                }

            return true;
            }


        });



    }
    @Override // boolean to return item selected state
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)) return true;

        return false;
    }
}
