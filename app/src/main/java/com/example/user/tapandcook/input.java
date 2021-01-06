package com.example.user.tapandcook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class input extends AppCompatActivity {

    private Button search;
    private EditText ingredient;
    private ProgressBar progressBar;
    private Spinner methodSpinner;
    private Spinner timeSpinner;
    private LinearLayout layout;

    private String entered;
    private String cookingMethod;
    private String time;
    boolean connection=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        search = findViewById(R.id.button);
        ingredient = findViewById(R.id.searchIngr);
        progressBar = findViewById(R.id.progress);
        methodSpinner = findViewById(R.id.methodspinner);
        timeSpinner = findViewById(R.id.timespinner);
        layout = findViewById(R.id.linearlayout);

        //notify unable to search without internet connection
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){
            connection=true;
        }
        else connection=false;

        if (connection==false){
            Snackbar internet=Snackbar.make(layout,"No Internet Connection\n"+"Searching are not available",
                    Snackbar.LENGTH_SHORT );
            internet.show();
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entered = ingredient.getText().toString();
                cookingMethod = methodSpinner.getSelectedItem().toString();
                time = timeSpinner.getSelectedItem().toString();

                if (entered == null || entered.trim().equals("")) {
                    Toast.makeText(input.this, "Please enter something. ", Toast.LENGTH_SHORT).show();
                } else {

                    //pop up progressing window
                    final ProgressDialog loadingdialog;
                    loadingdialog = new ProgressDialog(input.this);
                    loadingdialog.setTitle("LOADING");
                    loadingdialog.setMessage("Searching for result");
                    loadingdialog.show();
                    Runnable progressRunnable = new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.cancel();
                        }
                    };
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 3000);

                    makeSearchQuery();
                }
            }
        });
    }

// create url
    void makeSearchQuery() {

        String url = Service.makeURL(entered, cookingMethod, time);

        new asyncTask().execute(url);

    }

    //allow to perform background operation
     class asyncTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

        Response response;
        ArrayList<Recipe> recipeList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response != null) {
                recipeList = Service.processResults(response);
                return recipeList;
            } else
                return null;

        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipeList) {
            if (response != null && !recipeList.isEmpty()) {
                progressBar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(input.this, listActivity.class);
                intent.putParcelableArrayListExtra("recipe", recipeList);
                startActivity(intent);
                finish();
            } else
            {
                if(connection==true)
                {Toast.makeText(input.this, "Recipe not found!", Toast.LENGTH_LONG).show();}

                else{
                    Toast.makeText(input.this, "Recipe not found!\nCheck Your Internet Connection", Toast.LENGTH_LONG).show();
                    final Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },2000);
                }

            }
        }
    }


}



