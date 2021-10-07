package com.d2i.stockmanagement.screen.checkoutscan;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutScanRepository {
    private final String POST_UID_URL = "https://ed43-111-223-252-19.ngrok.io/api/epc";

    private OkHttpClient client = new OkHttpClient();

    public boolean postUID(String epc)
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("epc", epc)
                .build();

        Request request = new Request.Builder()
                .url(POST_UID_URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return true;
        } catch (IOException e) {
//            e.printStackTrace();
            return false;
        }
    }
}
