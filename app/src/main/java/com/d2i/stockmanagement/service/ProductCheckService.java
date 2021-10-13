package com.d2i.stockmanagement.service;

import com.d2i.stockmanagement.entity.request.TagCreateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductCheckService {
    @POST("/")
    Call<Void> post(@Body TagCreateRequest request);
}
