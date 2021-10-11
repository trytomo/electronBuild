package com.d2i.stockmanagement.utils;

import android.content.Context;

import com.d2i.stockmanagement.R;

public class Endpoint {
    private Context context;

    public Endpoint(Context context) {
        this.context = context;
    }

    public String getUrl(String endpoint) {
        return context.getString(R.string.api_url) + "/api" + endpoint;
    }

    public String getBaseUrl() {
        return context.getString(R.string.api_url) + "/api/";
    }
}
