package com.d2i.stockmanagement.entity;

import com.google.gson.annotations.SerializedName;

public class EPC {
    @SerializedName("data_epc")
    private String dataEpc;

    public String getDataEpc() {
        return dataEpc;
    }

    public void setDataEpc(String dataEpc) {
        this.dataEpc = dataEpc;
    }
}
