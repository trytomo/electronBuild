package com.d2i.stockmanagement.screen.dashboard;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.entity.Product;
import com.d2i.stockmanagement.entity.response.DashboardResponse;
import com.d2i.stockmanagement.entity.response.ServerResponse;
import com.d2i.stockmanagement.screen.BaseActivity;
import com.d2i.stockmanagement.service.DashboardService;
import com.d2i.stockmanagement.utils.ApiHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardActivity extends BaseActivity {
    private TableLayout tlLatestProductsSold;
    private TextView tvProductSold;
    private TextView tvProductLost;
    private TextView tvDiamondSold;
    private TextView tvGoldSold;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initUI();
    }

    private void initUI() {
        tlLatestProductsSold = findViewById(R.id.latest_products_sold);
        tvProductSold = findViewById(R.id.product_sold);
        tvProductLost = findViewById(R.id.product_lost);
        tvDiamondSold = findViewById(R.id.diamond_sold);
        tvGoldSold = findViewById(R.id.gold_sold);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataThread.start();
    }

    private final Thread getDataThread = new Thread(() -> {

        ApiHelper apiHelper = new ApiHelper(getApplicationContext());

        Retrofit retrofit = apiHelper.getRetrofit();
        DashboardService service = retrofit.create(DashboardService.class);

        Call<ServerResponse<DashboardResponse>> call = service.get(apiHelper.getAccessToken());

        call.enqueue(new Callback<ServerResponse<DashboardResponse>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ServerResponse<DashboardResponse>> call, @NonNull Response<ServerResponse<DashboardResponse>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {
                        handler.post(() -> {
                            int productSold = response.body().getData().getProdukTerjual();
                            int productLost = response.body().getData().getProdukHilang();
                            int diamondSold = response.body().getData().getBerlianTerjual();
                            int goldSold = response.body().getData().getEmasTerjual();
                            ArrayList<Product> products = response.body().getData().getProdukTerjualTerbaru();

                            tvProductSold.setText(String.valueOf(productSold));
                            tvProductLost.setText(String.valueOf(productLost));
                            tvDiamondSold.setText(String.valueOf(diamondSold));
                            tvGoldSold.setText(String.valueOf(goldSold));

                            for (Product product : products) {
                                TableRow row = new TableRow(getBaseContext());

                                TextView rfid = new TextView(getBaseContext());
                                rfid.setText(product.getRfid());
                                row.addView(rfid);

                                TextView sku = new TextView(getBaseContext());
                                rfid.setText(product.getSku());
                                row.addView(sku);

                                TextView productName = new TextView(getBaseContext());
                                rfid.setText(product.getName());
                                row.addView(productName);

                                TextView status = new TextView(getBaseContext());
                                rfid.setText(product.getStatus());
                                row.addView(status);

                                tlLatestProductsSold.addView(row);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse<DashboardResponse>> call, @NonNull Throwable t) {

            }
        });
    });
}