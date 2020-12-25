package com.example.githubdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.githubdemo.utils.APIClient;
import com.example.githubdemo.utils.ApiInterface;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView cv_avatar_url;
    TextView txt_user_name, txt_user_full_name, txt_location, txt_follower, txt_following, txt_pub_repo, txt_public_gits, txt_last_update;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        String login = bundle.getString("login");

        txt_last_update = findViewById(R.id.txt_last_update);
        txt_public_gits = findViewById(R.id.txt_public_gits);
        txt_pub_repo = findViewById(R.id.txt_pub_repo);
        txt_following = findViewById(R.id.txt_following);
        txt_follower = findViewById(R.id.txt_follower);
        txt_location = findViewById(R.id.txt_location);
        txt_user_full_name = findViewById(R.id.txt_user_full_name);
        txt_user_name = findViewById(R.id.txt_user_name);
        cv_avatar_url = findViewById(R.id.cv_avatar_url);
        iv_back = findViewById(R.id.iv_back);

        callMatchesApi(login);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void callMatchesApi(String login) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getUserProfile(login);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    String login = object.getString("login");
                    String name = object.getString("name");
                    String avatar_url = object.getString("avatar_url");
                    String location = object.getString("location");
                    String followers = object.getString("followers");
                    String following = object.getString("following");
                    String public_repos = object.getString("public_repos");
                    String public_gists = object.getString("public_gists");
                    String updated_at = object.getString("updated_at");

                    txt_last_update.setText(updated_at);
                    txt_public_gits.setText(public_gists);
                    txt_pub_repo.setText(public_repos);
                    txt_following.setText(following);
                    txt_follower.setText(followers);
                    txt_location.setText(location);
                    txt_user_name.setText(login);
                    txt_user_full_name.setText(name);

                    Glide.with(ProfileActivity.this)
                            .load(avatar_url)
                            .into(cv_avatar_url);


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