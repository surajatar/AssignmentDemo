package com.example.githubdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubdemo.FollowActivity;
import com.example.githubdemo.FollowModel;
import com.example.githubdemo.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {

    Context mConext;
    ArrayList<FollowModel> followModelArrayList;
    FollowModel followModel;


    public FollowAdapter(FollowActivity followActivity, ArrayList<FollowModel> followAdapterArrayList) {
        this.mConext = followActivity;
        this.followModelArrayList = followAdapterArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mConext).inflate(R.layout.list_follower, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.MyViewHolder holder, int position) {
        followModel = followModelArrayList.get(position);

        String login = followModel.getLogin();
        String image = followModel.getAvatar_url();

        holder.txt_user_name.setText(login);

        Glide.with(mConext)
                .load(image)
                .into(holder.cv_profile);

    }

    @Override
    public int getItemCount() {
        return followModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cv_profile;
        TextView txt_user_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            cv_profile = itemView.findViewById(R.id.avatar_url);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
        }
    }
}
