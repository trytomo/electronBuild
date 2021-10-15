package com.d2i.stockmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.d2i.stockmanagement.R;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    Context context;
    private String baseUrl;

    public ApiHelper(Context ctx) {
        context = ctx;
        baseUrl = ctx.getString(R.string.api_url);
    }

    public void setBaseUrl(String url) {
        baseUrl = url;
    }

    public Retrofit getRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public String getAccessToken () {
        SharedPreferences sharedPreferences = context.getSharedPreferences("authentication", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public void setAccessToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("authentication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "Bearer " + token);
        editor.apply();
    }
}
