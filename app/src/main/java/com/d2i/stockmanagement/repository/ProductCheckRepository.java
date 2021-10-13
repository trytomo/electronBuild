package com.d2i.stockmanagement.repository;

import android.content.Context;
import android.util.Log;

import com.d2i.stockmanagement.entity.EPC;
import com.d2i.stockmanagement.entity.request.TagCreateRequest;
import com.d2i.stockmanagement.service.ProductCheckService;
import com.d2i.stockmanagement.utils.Endpoint;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCheckRepository {
    private final ProductCheckService service;

    public ProductCheckRepository(Context context) {
        Endpoint endpoint = new Endpoint(context);
        String baseUrl = "https://singgalang-adonis.herokuapp.com/";

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        service = retrofit.create(ProductCheckService.class);
    }

    public void createMany(ArrayList<EPC> epcs) {
        TagCreateRequest request = new TagCreateRequest();
        request.setData(epcs);

        Call<Void> call = service.post(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("EPC Tag", "Success to insert into Database");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("EPC Tag", "Failed to insert into Database");
            }
        });
    }
}
