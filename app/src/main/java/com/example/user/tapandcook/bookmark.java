package com.example.user.tapandcook;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class bookmark extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bookmark, container, false);
        Intent intent=new Intent(getActivity(),bookmarklist.class);
        startActivity(intent);

        return view;
    }


}
