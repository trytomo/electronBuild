package com.d2i.stockmanagement.entity.request;

import com.d2i.stockmanagement.entity.EPC;

import java.util.ArrayList;

public class TagCreateRequest {
    private ArrayList<EPC> data;

    public ArrayList<EPC> getData() {
        return data;
    }

    public void setData(ArrayList<EPC> data) {
        this.data = data;
    }
}
