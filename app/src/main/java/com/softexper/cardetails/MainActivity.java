package com.softexper.cardetails;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softexper.cardetails.Data.DataCallback;
import com.softexper.cardetails.Data.DataRepository;
import com.softexper.cardetails.Data.POJO.data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView carsRecyclerView;
    private CarsAdapter carsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        carsRecyclerView = findViewById(R.id.car_recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        carsAdapter = new CarsAdapter(new ArrayList<>(), this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carsRecyclerView.setHasFixedSize(false);
        carsRecyclerView.setAdapter(carsAdapter);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRecyclerViewData();
            }
        });
    }

    private void getRecyclerViewData(){
        DataCallback<List<data>> dataCallback = new DataCallback<List<data>>() {
            @Override
            public void onDataReceived(List<data> dataList) {
                Log.d("dataListSize", dataList.size() + " ");
                carsAdapter.setCarsList(dataList);
                carsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String s) {
                Log.d("Error", s + " ");
            }
        };
        DataRepository.getInstance().getCarList(dataCallback, 1);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getRecyclerViewData();
    }
}
