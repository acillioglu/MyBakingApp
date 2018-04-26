package com.example.android.mybakingapp.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.mybakingapp.Adapter.StepsAdapter;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailMasterFragment.MyItemClick, RecipeDynamicFragment.MyButtonClick {

    private List<Step> steps;
    List<RecipeList> recipeLists;
    private boolean mTwoPane;
    private int mPosition;

    private boolean isDynamic = false;

    private static final String SIS_POSITION = "sis_position";
    private static final String SIS_IS_DYNAMIC = "sis_is_dynamic";

    private final String TAG = getClass().getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                .replace(R.id.container_detail_fragment, recipeDetailMasterFragment)
                .commit();


        if (savedInstanceState != null) {

            mPosition = savedInstanceState.getInt(SIS_POSITION);
            isDynamic = savedInstanceState.getBoolean(SIS_IS_DYNAMIC);

            if (isDynamic == true) {

                onItemClick(mPosition);


            }


        }


    }


    @Override
    public void onItemClick(int position) {


        //   Toast.makeText(this, "tiklanan : " + position, Toast.LENGTH_SHORT).show();

        RecipeDynamicFragment recipeDynamicFragment = new RecipeDynamicFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        ArrayList<RecipeList> secondRecipe = new ArrayList<>();
        secondRecipe.addAll(recipeLists);

        Bundle bundleIng = new Bundle();
        bundleIng.putParcelableArrayList("sallama2", secondRecipe);
        bundleIng.putInt("sallamapozisyon", position);


        recipeDynamicFragment.setArguments(bundleIng);


        if (mTwoPane == true) {


            fragmentManager.beginTransaction()
                    .replace(R.id.container_dynamic_fragment, recipeDynamicFragment)
                    .commit();


        } else {


            fragmentManager.beginTransaction()
                    .replace(R.id.container_detail_fragment, recipeDynamicFragment)
                    .addToBackStack("RECIPEDYNAMICFRAGMENT")
                    .commit();


        }

        mPosition = position;
        isDynamic = true;


     /*   Bundle dynamicBundle = new Bundle();


        ArrayList<Step> dynamicStep = new ArrayList<>();
        dynamicStep.addAll(steps);
        dynamicBundle.putParcelableArrayList("aa", dynamicStep);
        recipeDynamicFragment.setArguments(dynamicBundle);
        */


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putInt(SIS_POSITION, mPosition);
        outState.putBoolean(SIS_IS_DYNAMIC, isDynamic);


    }
}
