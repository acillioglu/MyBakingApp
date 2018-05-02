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
import android.widget.Toast;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter {


    private Context context;

    private ArrayList<RecipeList> recipeLists;

    private RecipeAdapterListener mListener;

    public interface RecipeAdapterListener {
        void onClick(View view, int position);
    }


    public RecipeAdapter(Context context, ArrayList<RecipeList> mRecipeLists, RecipeAdapterListener mListener) {
        this.recipeLists = mRecipeLists;
        this.context = context;
        this.mListener = mListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, parent, false);
        return new ListviewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ListviewHolder) holder).bindView(position);

    }

    @Override
    public int getItemCount() {

        return recipeLists.size();

    }

    public class ListviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_recipe_name)
        TextView mItemTextView;
        @BindView(R.id.imv_recipe_image)
        ImageView mItemImage;


        public ListviewHolder(View itemView, RecipeAdapterListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mListener = listener;
            itemView.setOnClickListener(this);


        }

        public void bindView(int position) {

            if (recipeLists.get(position).getImage() != null && !recipeLists.get(position).getImage().isEmpty()) {


                Picasso.get()
                        .load(recipeLists.get(position).getImage())
                        .placeholder(R.color.colorAccent)
                        .error(R.color.colorAccent)
                        .into(mItemImage);

            }


            mItemTextView.setText(recipeLists.get(position).getName());


        }


        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                mListener.onClick(view, pos);
            }


        }
    }


}
