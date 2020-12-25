package com.example.githubdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubdemo.adapter.UserSearchAdapter;
import com.example.githubdemo.model.UserSearchModel;
import com.example.githubdemo.utils.APIClient;
import com.example.githubdemo.utils.ApiInterface;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchUserActivity extends AppCompatActivity {


    RecyclerView rc_search_user;
    EditText searchView;
    ArrayList<UserSearchModel> SearchUserArrylist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);


        init();

    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    private void init() {
        rc_search_user = findViewById(R.id.rc_search_user);
        searchView = findViewById(R.id.searchView);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        UserSearchAdapter adapter = new UserSearchAdapter(this, SearchUserArrylist);
        rc_search_user.setAdapter(adapter);
        rc_search_user.setHasFixedSize(true);
        rc_search_user.setLayoutManager(lLinearLayoutManager);

        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (isOnline()) {
                    callMatchesApi(searchView.getText().toString().trim());
                    closeKeyboard();
                } else {
                    Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

    }

    public void callMatchesApi(String search) {
        SearchUserArrylist.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiService.getUserDetails(search, "1");
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    JSONArray items = object.getJSONArray("items");


                    if (items.length() == 0) {
                        Toast.makeText(SearchUserActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject jsonObject = items.getJSONObject(i);
                            UserSearchModel userSearchModel = new UserSearchModel();
                            String loging = jsonObject.getString("login");
                            String avatar_url = jsonObject.getString("avatar_url");
                            userSearchModel.setLogin(loging);
                            userSearchModel.setAvatar_url(avatar_url);
                            SearchUserArrylist.add(userSearchModel);
                        }
                        init();
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}