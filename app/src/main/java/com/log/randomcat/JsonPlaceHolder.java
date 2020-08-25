package com.log.randomcat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {

    @GET("/facts/random?animal_type=cat&amount=1")
    Call<POJO> get_text();

}
