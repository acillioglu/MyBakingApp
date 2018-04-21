package com.example.android.mybakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;
import java.util.ArrayList;
import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter{

    private Context context;



    private ArrayList<Step> stepArrayList;

    private StepsAdapterListener mListener;

    public interface StepsAdapterListener {

        void onClick(View view, int position);

    }


    public StepsAdapter(Context context, ArrayList<Step> mSteps, StepsAdapterListener mListener) {

        this.context = context;
        this.stepArrayList = mSteps;
        this.mListener = mListener;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_list, parent, false);
        return new ListViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ListViewHolder) holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mShortDesTextView;




       /* private TextView mShortDesTextView;
        private TextView mDescriptionTextView;
        private TextView mVideoUrlTextView;
        private TextView mThumbnailUrlTextView;*/

        public ListViewHolder(View itemView, StepsAdapterListener listener) {
            super(itemView);

            mShortDesTextView = (TextView) itemView.findViewById(R.id.txt_step_shortDes);


            mListener = listener;
            itemView.setOnClickListener(this);

        }


        public void bindView(int position) {


        mShortDesTextView.setText(stepArrayList.get(position).getShortDescription());

        }





        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION){

                mListener.onClick(view, pos);


            }


        }
    }















}
