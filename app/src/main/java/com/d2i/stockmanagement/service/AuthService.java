package com.d2i.stockmanagement.service;

import com.d2i.stockmanagement.entity.LoginResponse;
import com.d2i.stockmanagement.entity.ServerResponse;
import com.d2i.stockmanagement.entity.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @POST("api/login")
    Call<ServerResponse<LoginResponse>> login(
            @Body LoginRequest loginRequest);
}
