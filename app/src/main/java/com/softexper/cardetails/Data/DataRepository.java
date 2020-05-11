package com.softexper.cardetails.Data;

import com.softexper.cardetails.Data.POJO.ResponseData;
import com.softexper.cardetails.Data.POJO.data;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static final Object LOCK = new Object();
    private static DataRepository INSTANCE;
    public static RemoteDataSource mRemoteDataSource;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    mRemoteDataSource = RemoteDataSource.getInstance(RemoteDataSource.GENERAL_BASE_URL);
                    INSTANCE = new DataRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void getCarList(DataCallback<List<data>> callback, int page) {
        mRemoteDataSource.getCarList(page).enqueue(new GeneralResponseCallback<>(callback));
    }

    private String getResponseError(Response response) {
        try {
            return response.errorBody().string();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private class GeneralResponseCallback<T extends ResponseData> implements Callback<T> {

        private DataCallback callback;

        private GeneralResponseCallback(DataCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                callback.onDataReceived(response.body().getData());
            } else {
                String error = getResponseError(response);
                callback.onFailure(new Exception(error));
                callback.onFailure(error);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            callback.onFailure(t);
            callback.onFailure(t.getMessage());
        }
    }

}
