package com.softexper.cardetails.Data.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarResponse {

    @SerializedName("status")
    private long status;
    @SerializedName("data")
    private List<Data> data;

    public long getStatus() {
        return status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
