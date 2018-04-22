package com.example.android.mybakingapp.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.mybakingapp.Adapter.StepsAdapter;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private List<Step> steps;
    List<RecipeList> recipeLists;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        if (findViewById(R.id.sw600_linear_layout) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;

        }


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
