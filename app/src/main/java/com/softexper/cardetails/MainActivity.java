package com.softexper.cardetails;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softexper.cardetails.Data.DataCallback;
import com.softexper.cardetails.Data.DataRepository;
import com.softexper.cardetails.Data.POJO.CarResponse;
import com.softexper.cardetails.Data.POJO.Data;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView carsRecyclerView;
    private CarsAdapter carsAdapter;
    private int page = 1;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        carsRecyclerView = findViewById(R.id.car_recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        carsAdapter = new CarsAdapter(new ArrayList<>(), this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        carsRecyclerView.setLayoutManager(linearLayoutManager);
        carsRecyclerView.setHasFixedSize(false);
        carsRecyclerView.setAdapter(carsAdapter);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRecyclerViewData(1);
            }
        });
    }

    private void getRecyclerViewData(int page) {
        DataCallback<CarResponse> dataCallback = new DataCallback<CarResponse>() {
            @Override
            public void onDataReceived(CarResponse carResponse) {
                carsAdapter.setCarsList(carResponse.getData());
                carsAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String s) {
                Log.d("Error", s + " ");
            }
        };
        DataRepository.getInstance().getCarList(dataCallback, page);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getRecyclerViewData(1);
    }

}
