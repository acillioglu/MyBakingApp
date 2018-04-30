package com.example.android.mybakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;

public class WidgetService extends RemoteViewsService {

    private final static String TAG = WidgetService.class.getSimpleName();
    private static final String BUNDLE_NAME = "bundle_name";
    private static final String BUNDLE_RECIPE = "bundle_recipe";
    private static final String BUNDLE_WIDGETID = "bundle_widgetid";


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent ıntent) {

        RecipeList recipe = null;
        int appWidgetId = -1;
        if (ıntent.hasExtra(BUNDLE_NAME)) {

            Bundle bundle = ıntent.getBundleExtra(BUNDLE_NAME);
            recipe = bundle.getParcelable(BUNDLE_RECIPE);
            appWidgetId = bundle.getInt(BUNDLE_WIDGETID);


        }


        return new ListViewWidgetFactory(getApplicationContext(), recipe, appWidgetId);
    }


    class ListViewWidgetFactory implements WidgetService.RemoteViewsFactory {

        private final Context context;
        private RecipeList recipe;
        int appWidgetId = -1;

        public ListViewWidgetFactory(Context context, RecipeList recipe, int appWidgetId) {
            this.context = context;
            this.recipe = recipe;
            this.appWidgetId = appWidgetId;
        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            if (recipe == null) return 0;
            if (recipe.getIngredients() == null) return 0;
            return recipe.getIngredients().size();

        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews rv = null;

            rv = new RemoteViews(context.getPackageName(), R.layout.widget_text_view);
            rv.setTextViewText(R.id.txt_widget_ing, String.valueOf(recipe.getIngredients().get(i).getIngredient()));
            rv.setTextViewText(R.id.txt_widget_quantity, " ( " + String.valueOf(recipe.getIngredients().get(i).getQuantity()));
            rv.setTextViewText(R.id.txt_widget_measure, String.valueOf(recipe.getIngredients().get(i).getMeasure()) + " )");


            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }


}
