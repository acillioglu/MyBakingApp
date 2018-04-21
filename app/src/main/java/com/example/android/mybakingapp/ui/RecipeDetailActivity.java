package com.example.android.mybakingapp.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private List<Step> steps;
    List<RecipeList> recipeLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);




        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            recipeLists = bundle.getParcelableArrayList("sallama");


        }

        steps = recipeLists.get(0).getSteps();







        RecipeDetailMasterFragment recipeDetailMasterFragment = new RecipeDetailMasterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        recipeDetailMasterFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.container_detail_fragment, recipeDetailMasterFragment)
                .commit();


    }


}
