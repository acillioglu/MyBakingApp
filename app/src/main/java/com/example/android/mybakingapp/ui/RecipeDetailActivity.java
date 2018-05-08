package com.example.android.mybakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailMasterFragment.MyItemClick, RecipeDynamicFragment.MyButtonClick {

    private static final String SIS_POSITION = "sis_position";
    private static final String SIS_IS_DYNAMIC = "sis_is_dynamic";
    private static final String BUNDLE_KEY_RECIPE = "bundle_key_recipe";
    private static final String BUNDLE_KEY_POSITION = "bundle_key_position";
    private static final String BUNDLE_KEY_SECOND_RECIPE = "bundle_key_second_recipe";
    private static final String STACK_RECIPE_DETAIL = "stack_recipe detail";
    List<RecipeList> recipeLists;
    FragmentManager fragmentManager;
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    private boolean mTwoPane = false;
    private int mPosition;
    private boolean isDynamic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        mTwoPane = getResources().getBoolean(R.bool.is_tablet);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            recipeLists = bundle.getParcelableArrayList(BUNDLE_KEY_RECIPE);


        }


        if (savedInstanceState == null) {

            RecipeDetailMasterFragment recipeDetailMasterFragment = new RecipeDetailMasterFragment();

            fragmentManager = getSupportFragmentManager();

            recipeDetailMasterFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.container_detail_fragment, recipeDetailMasterFragment)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

        }


        if (savedInstanceState != null) {

            mPosition = savedInstanceState.getInt(SIS_POSITION);
            isDynamic = savedInstanceState.getBoolean(SIS_IS_DYNAMIC);

            if (isDynamic == true) {

                onItemClick(mPosition);

            }


        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeLists.get(0).getName());


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


    }


    @Override
    public void onItemClick(int position) {


        RecipeDynamicFragment recipeDynamicFragment = new RecipeDynamicFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        ArrayList<RecipeList> secondRecipe = new ArrayList<>();
        secondRecipe.addAll(recipeLists);

        Bundle bundleIng = new Bundle();
        bundleIng.putParcelableArrayList(BUNDLE_KEY_SECOND_RECIPE, secondRecipe);
        bundleIng.putInt(BUNDLE_KEY_POSITION, position);

        recipeDynamicFragment.setArguments(bundleIng);


        if (mTwoPane == true) {

            fragmentManager.beginTransaction()
                    .replace(R.id.container_dynamic_fragment, recipeDynamicFragment)
                    .commit();
        } else {

            fragmentManager.beginTransaction()
                    .replace(R.id.container_detail_fragment, recipeDynamicFragment)
                    .addToBackStack(null)
                    .commit();
        }

        RecipeDynamicFragment.mCurrentPlayerPosition = 0;
        RecipeDynamicFragment.playWhenReady = true;

        mPosition = position;
        isDynamic = true;


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SIS_POSITION, mPosition);
        outState.putBoolean(SIS_IS_DYNAMIC, isDynamic);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        FragmentManager fm = getSupportFragmentManager();

        if (findViewById(R.id.container_dynamic_fragment) == null) {

            if (fm.getBackStackEntryCount() >= 1) {

                fm.popBackStack(STACK_RECIPE_DETAIL, 0);
            } else {
                finish();
            }

        } else {
            finish();
        }

    }


}
