package com.softexper.cardetails.Data;

import com.softexper.cardetails.Data.POJO.CarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cars")
    Call<CarResponse> getCarList(@Query("page") int page);
}
