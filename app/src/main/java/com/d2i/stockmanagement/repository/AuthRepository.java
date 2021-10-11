package com.d2i.stockmanagement.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.d2i.stockmanagement.entity.LoginResponse;
import com.d2i.stockmanagement.entity.ServerResponse;
import com.d2i.stockmanagement.entity.LoginRequest;
import com.d2i.stockmanagement.service.AuthService;
import com.d2i.stockmanagement.utils.Endpoint;
import com.google.gson.Gson;
import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRepository {
    private static final String LOGIN_URL = "http://api.singgalanghs.id/api/login";
    private Endpoint endpoint;
    private LoginRequest loginRequest;
    RequestQueue MyRequestQueue;

    public AuthRepository(Context context) {
        endpoint = new Endpoint(context);
        MyRequestQueue= Volley.newRequestQueue(context);
    }

    public void login(LoginRequest loginRequest) {
//        String url = "https://api.singgalanghs.id/";
        String url = "https://cfe0-114-142-173-38.ngrok.io/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        AuthService service = retrofit.create(AuthService.class);

        Call<ServerResponse<LoginResponse>> call = service.login(loginRequest);

        call.enqueue(new Callback<ServerResponse<LoginResponse>>() {

            @Override
            public void onResponse(Call<ServerResponse<LoginResponse>> call, Response<ServerResponse<LoginResponse>> response) {
                Log.d("Login", response.body().getData().getToken());
            }

            @Override
            public void onFailure(Call<ServerResponse<LoginResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.d("Login", t.getMessage());
            }
        });

    }
}
