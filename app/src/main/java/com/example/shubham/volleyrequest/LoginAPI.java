package com.example.shubham.volleyrequest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Shubham-PC on 4/22/2017.
 */

public interface LoginAPI {

    @FormUrlEncoded
    @POST("/api/users/login/")
    @Headers("Content-type: application/json")

    public void loginUser(
            @Field("email") String email,
            @Field("password") String password,


            Callback<Response> callback);

}
