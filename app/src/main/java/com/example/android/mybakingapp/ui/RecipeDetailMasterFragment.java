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


import com.example.android.mybakingapp.Adapter.RecipeAdapter;
import com.example.android.mybakingapp.Adapter.StepsAdapter;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDetailMasterFragment extends Fragment {


    List<RecipeList> recipeLists;

    List<Step> stepList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_master, container, false);


        recipeLists = getArguments().getParcelableArrayList("sallama");

        getActivity().setTitle(recipeLists.get(0).getName());


       ArrayList<Step> steps = new ArrayList<>();



            steps.addAll(recipeLists.get(0).getSteps());



        final StepsAdapter.StepsAdapterListener listener = new StepsAdapter.StepsAdapterListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_stepsShortDes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        StepsAdapter stepsAdapter = new StepsAdapter(getContext(),steps, listener );

        recyclerView.setAdapter(stepsAdapter);







        return rootView;


    }









}
