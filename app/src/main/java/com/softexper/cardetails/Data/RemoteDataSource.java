package com.softexper.cardetails.Data;

import com.softexper.cardetails.Data.POJO.CarResponse;
import com.softexper.cardetails.Data.POJO.ResponseData;
import com.softexper.cardetails.Data.POJO.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    private static ApiService mApiService;
    private static final Object LOCK = new Object();
    private static RemoteDataSource INSTANCE;
    public static final String GENERAL_BASE_URL = "http://demo1286023.mockable.io/api/v1/";
    private static Retrofit retrofit;

    private RemoteDataSource(String base_url) {
        buildApiService(base_url);
    }

    public static void buildApiService(String base_url){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static RemoteDataSource getInstance(String base_url) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataSource(base_url);
                }
            }
            return INSTANCE;
        }else {
            buildApiService(base_url);
            return INSTANCE;
        }
    }

    public Call<CarResponse> getCarList(int page) {
        return mApiService.getCarList(page);
    }
}
