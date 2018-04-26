package com.example.android.mybakingapp.ui;


import android.app.ActionBar;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.android.mybakingapp.Adapter.IngredientAdapter;
import com.example.android.mybakingapp.Adapter.RecipeAdapter;
import com.example.android.mybakingapp.Adapter.StepsAdapter;
import com.example.android.mybakingapp.Model.Ingredient;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailMasterFragment extends Fragment {


    List<RecipeList> recipeLists;

    private static final String BUNDLE_KEY_RECIPE = "bundle_key_recipe";

    @BindView(R.id.recyclerview_ingredients)
    RecyclerView recyclerIng;

    @BindView(R.id.recyclerview_stepsShortDes)
    RecyclerView recyclerStepsShortDes;


    interface MyItemClick {

        public void onItemClick(int position);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_master, container, false);

        ButterKnife.bind(this,rootView);


        recipeLists = getArguments().getParcelableArrayList(BUNDLE_KEY_RECIPE);

        getActivity().setTitle(recipeLists.get(0).getName());


        ArrayList<Step> steps = new ArrayList<>();
        steps.addAll(recipeLists.get(0).getSteps());

        ArrayList<Ingredient> ıngredients = new ArrayList<>();
        ıngredients.addAll(recipeLists.get(0).getIngredients());


        final StepsAdapter.StepsAdapterListener listener = new StepsAdapter.StepsAdapterListener() {
            @Override
            public void onClick(View view, int position) {

                MyItemClick myItemClick = (MyItemClick) getActivity();
                myItemClick.onItemClick(position);


            }
        };



        recyclerIng.setNestedScrollingEnabled(false);
        recyclerIng.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientAdapter ıngredientAdapter = new IngredientAdapter(getContext(), ıngredients);
        recyclerIng.setAdapter(ıngredientAdapter);



        recyclerStepsShortDes.setNestedScrollingEnabled(false);
        recyclerStepsShortDes.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), steps, listener);
        recyclerStepsShortDes.setAdapter(stepsAdapter);






        return rootView;


    }


}
