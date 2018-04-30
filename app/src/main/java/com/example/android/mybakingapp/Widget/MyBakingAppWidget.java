package com.example.android.mybakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class MyBakingAppWidget extends AppWidgetProvider {

    private static final String SHARED_RECIPE = "shared_recipe";
    private static final String SHARED_POSITION = "shared_position";
    private static final String BUNDLE_NAME = "bundle_name";
    private static final String BUNDLE_RECIPE = "bundle_recipe";
    private static final String BUNDLE_WIDGETID = "bundle_widgetid";

    private static final String TAG = MyBakingAppWidget.class.getSimpleName();


    public static ArrayList<RecipeList> recipeLists;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int[] appWidgetIds, List<RecipeList> recipes, int currentRecipeId) {


        RecipeList recipe = recipes.get(currentRecipeId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_baking_app_widget);


        Intent intent = new Intent(context, WidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE, recipe);
        bundle.putInt(BUNDLE_WIDGETID, appWidgetId);
        intent.putExtra(BUNDLE_NAME, bundle);

        views.setRemoteAdapter(R.id.list_view_widget, intent);


        String mIngredientName = context.getResources().getString(R.string.app_name) + " - " + String.valueOf(recipe.getName());

        views.setTextViewText(R.id.txt_widget_ingName, mIngredientName);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        List<RecipeList> recipes;

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(SHARED_RECIPE, null);
        Type type = new TypeToken<ArrayList<RecipeList>>() {
        }.getType();
        recipeLists = gson.fromJson(json, type);

        recipes = recipeLists;
        int uPosition = sharedPrefs.getInt(SHARED_POSITION, -1);

        int currentRecipeId = 0;
        if (uPosition != -1) {
            currentRecipeId = uPosition;
        }


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, appWidgetIds, recipes, currentRecipeId);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }
}

