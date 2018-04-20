package com.example.android.mybakingapp.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            List<RecipeList> recipeLists = bundle.getParcelableArrayList("sallama");

        }






    }
}
