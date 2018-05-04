package com.example.android.mybakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybakingapp.Adapter.RecipeAdapter;
import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Network.BakingApi;
import com.example.android.mybakingapp.Network.RetrofitBuilder;
import com.example.android.mybakingapp.R;
import com.example.android.mybakingapp.Widget.MyBakingAppWidget;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeFragment extends Fragment {

    private static Retrofit retrofit = null;
    private boolean isTablet = false;
    private static final String BUNDLE_KEY_RECIPE = "bundle_key_recipe";
    private static final String SHARED_RECIPE = "shared_recipe";
    private static final String SHARED_POSITION = "shared_position";

    private ArrayList<RecipeList> mRecipeLists;

    @BindView(R.id.recyclerview_recipe)
    RecyclerView recyclerView;



    public RecipeFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this,rootView);

        isTablet = getResources().getBoolean(R.bool.is_tablet);


        if (isTablet == true) {

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }else{

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            }





        final RecipeAdapter.RecipeAdapterListener listener = new RecipeAdapter.RecipeAdapterListener() {
            @Override
            public void onClick(View view, int position) {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SHARED_POSITION,position);
                editor.commit();




                int[] ids = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), MyBakingAppWidget.class));
                MyBakingAppWidget myWidget = new MyBakingAppWidget();
                myWidget.onUpdate(getContext(), AppWidgetManager.getInstance(getContext()),ids);




                Bundle bundle = new Bundle();

                ArrayList<RecipeList> selectedRecipe = new ArrayList<>();
                selectedRecipe.add(mRecipeLists.get(position));
                bundle.putParcelableArrayList(BUNDLE_KEY_RECIPE, selectedRecipe);
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);


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


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();

                    String json  = gson.toJson(mRecipeLists);
                    editor.putString(SHARED_RECIPE, json);
                    editor.commit();



                }

            }

            @Override
            public void onFailure(Call<ArrayList<RecipeList>> call, Throwable t) {

                if (t instanceof IOException) {
                    showSnackbar(recyclerView, getResources().getString(R.string.main_str_interneterror), Snackbar.LENGTH_INDEFINITE);
                }



            }
        });





        return rootView;

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void showSnackbar(View view, String message, int duration) {


        final Snackbar snackbar = Snackbar.make(view, message, duration);

        snackbar.setAction(getResources().getString(R.string.main_str_tryagain), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline() != true) {
                    showSnackbar(recyclerView, getResources().getString(R.string.main_str_interneterror), Snackbar.LENGTH_INDEFINITE);

                } else {
                    snackbar.dismiss();
                }

            }
        });

        snackbar.show();
    }







}
