package com.d2i.stockmanagement.service;

import com.d2i.stockmanagement.entity.response.DashboardResponse;
import com.d2i.stockmanagement.entity.response.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface DashboardService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/getProductsSoldDetailMobile")
    Call<ServerResponse<DashboardResponse>> get(@Header("Authorization") String authorization);
}
