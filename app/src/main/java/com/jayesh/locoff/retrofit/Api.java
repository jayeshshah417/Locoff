package com.jayesh.locoff.retrofit;

import com.jayesh.locoff.utils.OConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    @GET(OConstants.URLDATA)
    Call<ResultModel> getData(
            @Query("page") int page
    );

}

