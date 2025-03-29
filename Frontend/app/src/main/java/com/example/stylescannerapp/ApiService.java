package com.example.stylescannerapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @POST("/uploadImage")
    Call<Range> uploadImageAndGetRange(@Body Image image);

}
