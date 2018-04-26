package com.example.android.mybakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.Ingredient;
import com.example.android.mybakingapp.R;


import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter{

    private Context context;

    private ArrayList<Ingredient> ıngredients;


    public IngredientAdapter(Context context, ArrayList<Ingredient> ıngredients) {
        this.context = context;
        this.ıngredients = ıngredients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients_list, parent, false);
        return  new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ListViewHolder) holder).bindView(position);


    }

    @Override
    public int getItemCount() {
        return ıngredients.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredientsTextView;




        public ListViewHolder(View itemView) {
            super(itemView);

            mIngredientsTextView = (TextView) itemView.findViewById(R.id.txt_ingredients);



        }

        public void bindView(int position) {

            String mIngredient = ıngredients.get(position).getIngredient();
            String mMeasure = ıngredients.get(position).getMeasure();
            Double mQuantity = ıngredients.get(position).getQuantity();

            mIngredientsTextView.setText("• " + mIngredient +" (" + String.valueOf(mQuantity) + mMeasure + ")");


        }
    }




}
