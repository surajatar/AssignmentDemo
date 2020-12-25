package com.example.githubdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubdemo.adapter.FollowAdapter;
import com.example.githubdemo.utils.APIClient;
import com.example.githubdemo.utils.ApiInterface;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FollowActivity extends AppCompatActivity {


    RecyclerView follow_recylcer;
    FollowAdapter adapter;
    ArrayList<FollowModel> followAdapterArrayList = new ArrayList<>();

    CircleImageView cv_avatar_url;
    TextView txt_user_name, tv_no_data,tv_follow;
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        String follower = "follower";
        String following = "following";

        Bundle bundle = getIntent().getExtras();
        String login = bundle.getString("login");
        String followers = bundle.getString("follower");

        cv_avatar_url = findViewById(R.id.cv_avatar_url);
        txt_user_name = findViewById(R.id.txt_user_name);
        iv_back = findViewById(R.id.iv_back);
        tv_no_data = findViewById(R.id.tv_no_data);
        tv_follow = findViewById(R.id.tv_follow);

        init();

        if (followers.equalsIgnoreCase("1")) {
            callFollowerApi(login);
            tv_follow.setText(follower);
        } else {
            callFollowingApi(login);
            tv_follow.setText(following);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        follow_recylcer = findViewById(R.id.follow_recylcer);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new FollowAdapter(this, followAdapterArrayList);
        follow_recylcer.setHasFixedSize(true);
        follow_recylcer.setLayoutManager(lLinearLayoutManager);
        follow_recylcer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void callFollowingApi(String login) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getFollowing(login);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONArray object = new JSONArray(response.body().toString());
                    if (object.length() == 0) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < object.length(); i++) {
                            FollowModel followModel = new FollowModel();
                            JSONObject object1 = object.getJSONObject(i);
                            String login = object1.getString("login");
                            String avatar_url = object1.getString("avatar_url");
                            followModel.setLogin(login);
                            followModel.setAvatar_url(avatar_url);

                            followAdapterArrayList.add(followModel);

                        }
                        follow_recylcer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void callFollowerApi(String login) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getFollower(login);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONArray object = new JSONArray(response.body().toString());
                    if (object.length() == 0) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    }else
                    {
                        for (int i = 0; i < object.length(); i++) {
                            FollowModel followModel = new FollowModel();
                            JSONObject object1 = object.getJSONObject(i);
                            String login = object1.getString("login");
                            String avatar_url = object1.getString("avatar_url");

                            followModel.setLogin(login);
                            followModel.setAvatar_url(avatar_url);

                            followAdapterArrayList.add(followModel);

                        }
                        follow_recylcer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
            }
        });
    }


}