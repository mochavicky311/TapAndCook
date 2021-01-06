package com.example.user.tapandcook;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class main_fragment extends Fragment {
    Button inputbutton;
    Button western,pastry,asian,vege,dessert,japan,mexico;
    BottomNavigationView bottomNavigation;
    String category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_fragment,
                container, false);

        inputbutton=view.findViewById(R.id.input);
        western=view.findViewById(R.id.western);
        pastry=view.findViewById(R.id.pastry);
        asian=view.findViewById(R.id.asian);
        vege=view.findViewById(R.id.vege);
        dessert=view.findViewById(R.id.dessert);
        japan=view.findViewById(R.id.japan);
        mexico=view.findViewById(R.id.mexico);

        //set the query keyword and pop up message for different category
        western.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("western cooking");
                loading("Western cuisine recipe");
            }
        });

        asian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("asian cooking");
                loading("Asian cuisine recipe");
            }
        });

        vege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("vegetarian");
                loading("Vegetarian cuisine recipe");
            }
        });

        japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("japanese cuisine");
                loading("Japanese cuisine recipe");
            }
        });

        pastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("pastry");
                loading("Pastry recipe");
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("dessert");
                loading("Dessert recipe");
            }
        });

        mexico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuerycategory("mexican food");
                loading("Mexican cuisine recipe");
            }
        });

          inputbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),input.class);
                startActivity(intent);
            }
        });

        return view;
    }


   void makeSearchQuerycategory(String category) {

       String BASE_URL = "api.edamam.com";
       String API_KEY = "a8c06627d8819deb9e96ecf5823c0ce6";
       String API_ID = "667ff726";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(BASE_URL)
                .appendPath("search")
                .appendQueryParameter("q", category)
                .appendQueryParameter("app_id", API_ID)
                .appendQueryParameter("app_key", API_KEY);

        String url = builder.build().toString();

        new main_fragment.asyncTaskcategory().execute(url);

    }

    //allow to perform background operation
    class asyncTaskcategory extends AsyncTask<String, Void, ArrayList<Recipe>> {

        Response response;
        ArrayList<Recipe> recipeList = new ArrayList<>();

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

                Intent intent = new Intent(getActivity(), listActivity.class);
                intent.putParcelableArrayListExtra("recipe", recipeList);
                startActivity(intent);
            }
            else
                Toast.makeText(getActivity(), "Recipe not found!", Toast.LENGTH_LONG).show();

        }

    }

    //pop up progressing dialog
    void loading(String category){
        final ProgressDialog loadingdialog;
        loadingdialog = new ProgressDialog(getActivity());
        loadingdialog.setTitle("Searching for "+ category);
        loadingdialog.setMessage("You can get more recipes by inputting the ingredients");
        loadingdialog.show();
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                loadingdialog.cancel();
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
    }



}



