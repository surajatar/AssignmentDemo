package com.example.githubdemo.utils;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users?")
    Call<JsonElement> getUserDetails(
            @Query("q") String search,
            @Query("page") String page
    );

    @GET("/users/{login}")
    Call<JsonElement> getUserProfile(
            @Path("login") String login
    );

    @GET("/users/{login}/followers")
    Call<JsonElement> getFollower(
            @Path("login") String login
    );

    @GET("/users/{login}/following")
    Call<JsonElement> getFollowing(
            @Path("login") String login
    );
}