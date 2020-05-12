package com.softexper.cardetails;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softexper.cardetails.Data.DataCallback;
import com.softexper.cardetails.Data.DataRepository;
import com.softexper.cardetails.Data.POJO.CarResponse;
import com.softexper.cardetails.Data.POJO.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView carsRecyclerView;
    private CarsAdapter carsAdapter;
    private int page = 1;
    LinearLayoutManager linearLayoutManager;
    private boolean isLoading = false;
    List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        carsRecyclerView = findViewById(R.id.car_recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        carsAdapter = new CarsAdapter(dataList, this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        carsRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        carsRecyclerView.setLayoutManager(linearLayoutManager);
        carsRecyclerView.setHasFixedSize(false);
        carsRecyclerView.setAdapter(carsAdapter);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRecyclerViewData(page);
            }
        });
    }

    private void getRecyclerViewData(int page) {
        DataCallback<CarResponse> dataCallback = new DataCallback<CarResponse>() {
            @Override
            public void onDataReceived(CarResponse carResponse) {
                if (carResponse.getData() != null){
                    if (page == 1){
                        dataList.clear();
                    }
                    for (int i = 0 ; i < carResponse.getData().size(); i++){
                        dataList.add(carResponse.getData().get(i));
                        isLoading = false;
                        Toast.makeText(MainActivity.this, "Page " + page + " Loaded!", Toast.LENGTH_SHORT).show();
                    }
                }
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
        page = 1;
        getRecyclerViewData(page);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 10
                        && page < 4) {
                    page = page + 1;
                    getRecyclerViewData(page);
                    isLoading = true;
                    Toast.makeText(MainActivity.this, "Loading next Page!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}
