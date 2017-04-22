package com.example.shubham.volleyrequest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Belal on 11/5/2015.
 */
public interface RegisterAPI {

    @FormUrlEncoded
    @POST("api/users")
    @Headers("Content-type: application/json")

    public void insertUser(
            @Field("username") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("bloodgroup") String bloodgroup,
            @Field("address") String address,
            @Field("role") String role,
            @Field("status") String status,

            Callback<Response> callback);

}