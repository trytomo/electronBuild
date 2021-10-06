package com.d2i.stockmanagement.screen.checkoutscan;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class CheckoutScanRepository {
    private final String LOGIN_URL = "http://localhost:8000/auth/login";
    private OkHttpClient client = new OkHttpClient();

    public void postUID(String epc)
    {
//        RequestBody body = new MultipartBody.Builder();
    }
}
