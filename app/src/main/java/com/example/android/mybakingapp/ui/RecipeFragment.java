package com.example.android.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.android.mybakingapp.Adapter.RecipeAdapter;
import com.example.android.mybakingapp.Model.Ingredient;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Network.BakingApi;
import com.example.android.mybakingapp.Network.RetrofitBuilder;
import com.example.android.mybakingapp.R;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeFragment extends Fragment {

    private static Retrofit retrofit = null;

    private ArrayList<RecipeList> mRecipeLists;

    public RecipeFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_recipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        final RecipeAdapter.RecipeAdapterListener listener = new RecipeAdapter.RecipeAdapterListener() {
            @Override
            public void onClick(View view, int position) {


                //  Toast.makeText(getContext(), mRecipeLists.get(position).getName(), Toast.LENGTH_SHORT).show();

                Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();

                List<Ingredient> ıngredients = mRecipeLists.get(position).getIngredients();


            }
        };


        BakingApi bakingApi = RetrofitBuilder.Retrieve();
        Call<ArrayList<RecipeList>> baking = bakingApi.getRecipeList();


        baking.enqueue(new Callback<ArrayList<RecipeList>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeList>> call, Response<ArrayList<RecipeList>> response) {


                if (response.isSuccessful()) {

                    mRecipeLists = response.body();

                    RecipeAdapter listAdapter = new RecipeAdapter(getContext(), mRecipeLists, listener);
                    recyclerView.setAdapter(listAdapter);


                }

            }

            @Override
            public void onFailure(Call<ArrayList<RecipeList>> call, Throwable t) {

                Log.v("onfailure", String.valueOf(t));

            }
        });


        return view;
    }
}
