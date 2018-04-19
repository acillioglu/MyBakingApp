package com.example.android.mybakingapp.Network;

import com.example.android.mybakingapp.Model.RecipeList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingApi {

    @GET("baking.json")
    Call<ArrayList<RecipeList>> getRecipeList();


}
