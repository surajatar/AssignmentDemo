package com.example.githubdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubdemo.FollowActivity;
import com.example.githubdemo.ProfileActivity;
import com.example.githubdemo.R;
import com.example.githubdemo.SearchUserActivity;
import com.example.githubdemo.model.UserSearchModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.MyViewHolder> {


    private ArrayList<UserSearchModel> searchUserArrylist;
    private Context mContext;
    UserSearchModel userSearchModel;

    public UserSearchAdapter(SearchUserActivity searchUserActivity, ArrayList<UserSearchModel> searchUserArrylist) {
        this.mContext = searchUserActivity;
        this.searchUserArrylist = searchUserArrylist;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        userSearchModel = searchUserArrylist.get(position);
        String login = userSearchModel.getLogin();
        String avatar_url = userSearchModel.getAvatar_url();

        holder.txt_user_name.setText(login);


        Glide.with(mContext)
                .load(avatar_url)
                .into(holder.cv_profile);

        holder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("login", searchUserArrylist.get(position).getLogin());
                mContext.startActivity(intent);
            }
        });
        holder.txt_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FollowActivity.class);
                intent.putExtra("follower", "1");
                intent.putExtra("login", searchUserArrylist.get(position).getLogin());
                mContext.startActivity(intent);
            }
        });
        
        holder.txt_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FollowActivity.class);
                intent.putExtra("follower", "2");
                intent.putExtra("login", searchUserArrylist.get(position).getLogin());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchUserArrylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cv_profile;
        TextView txt_user_name, txt_follower, txt_following;
        LinearLayout ll_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_profile = itemView.findViewById(R.id.avatar_url);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            ll_user = itemView.findViewById(R.id.ll_user);
            txt_follower = itemView.findViewById(R.id.txt_follower);
            txt_following = itemView.findViewById(R.id.txt_following);
        }
    }
}

