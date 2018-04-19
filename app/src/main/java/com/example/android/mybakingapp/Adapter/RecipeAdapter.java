package com.example.android.mybakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter {


    private Context context;

    private ArrayList<RecipeList> recipeLists;


    public RecipeAdapter(Context context, ArrayList<RecipeList> mRecipeLists) {
        this.recipeLists = mRecipeLists;
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, parent, false);
        return new ListviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ListviewHolder) holder).bindView(position);

    }

    @Override
    public int getItemCount() {

        return recipeLists.size();

    }

    private class ListviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemTextView;
        private ImageView mItemImage;


        public ListviewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.txt_recipe_name);
          //  mItemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);

        }

        public void bindView(int position) {


           // mItemImage.setImageResource(BakingPics.picturePath[position]);
            mItemTextView.setText(recipeLists.get(position).getName());


        }


        @Override
        public void onClick(View view) {


        }
    }



}
