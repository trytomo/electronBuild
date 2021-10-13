package com.d2i.stockmanagement.service;

import com.d2i.stockmanagement.entity.response.LoginResponse;
import com.d2i.stockmanagement.entity.response.ServerResponse;
import com.d2i.stockmanagement.entity.request.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("api/login")
    Call<ServerResponse<LoginResponse>> login(
            @Body LoginRequest loginRequest);
}
