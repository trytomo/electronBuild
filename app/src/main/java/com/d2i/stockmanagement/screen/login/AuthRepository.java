package com.d2i.stockmanagement.screen.login;

import org.json.JSONException;
import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthRepository {
    private final String LOGIN_URL = "http://localhost:8000/auth/login";
    private OkHttpClient client = new OkHttpClient();

    public boolean login(String username, String password) throws JSONException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build();

        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
