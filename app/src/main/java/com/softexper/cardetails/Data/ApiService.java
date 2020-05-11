package com.softexper.cardetails.Data;

import com.softexper.cardetails.Data.POJO.ResponseData;
import com.softexper.cardetails.Data.POJO.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("cars?page=1")
    Call<ResponseData<List<data>>> getCarList();
}
