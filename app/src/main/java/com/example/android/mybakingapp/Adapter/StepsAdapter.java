package com.example.android.mybakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter {

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

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_step_shortDes)
        TextView mShortDesTextView;
        @BindView(R.id.imv_step_thumbnail)
        ImageView mThumbnailImageView;


       /* private TextView mShortDesTextView;
        private TextView mDescriptionTextView;
        private TextView mVideoUrlTextView;
        private TextView mThumbnailUrlTextView;*/

        public ListViewHolder(View itemView, StepsAdapterListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mListener = listener;
            itemView.setOnClickListener(this);

        }


        public void bindView(int position) {

            String shortDescription = stepArrayList.get(position).getShortDescription();
            String thumbnailUrl = stepArrayList.get(position).getThumbnailURL();

            mShortDesTextView.setText(position + " . " + shortDescription);


            if (!thumbnailUrl.isEmpty()) {
                Picasso.get()
                        .load(thumbnailUrl)
                        .error(R.drawable.img_novide)
                        .into(mThumbnailImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                                mThumbnailImageView.setVisibility(View.GONE);

                            }
                        });


            } else {
                mThumbnailImageView.setVisibility(View.GONE);
            }


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
