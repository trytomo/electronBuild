package com.d2i.stockmanagement.screen.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.screen.BaseActivity;
import com.d2i.stockmanagement.screen.dashboard.DashboardActivity;
import com.d2i.stockmanagement.screen.setting.SettingActivity;
import com.d2i.stockmanagement.screen.checkoutscan.CheckoutScanActivity;
import com.d2i.stockmanagement.screen.productcheck.ProductCheckActivity;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();
    }

    private void initUI() {
        RelativeLayout rlDashboard = findViewById(R.id.dashboard);
        RelativeLayout rlCheckout = findViewById(R.id.checkout);
        RelativeLayout rlCheckProduct = findViewById(R.id.check_product);
        RelativeLayout rlCatalogProduct = findViewById(R.id.catalog_product);
        RelativeLayout rlRepair = findViewById(R.id.repair);
        RelativeLayout rlSettings = findViewById(R.id.settings);

        rlDashboard.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), DashboardActivity.class);
            startActivity(intent);
        });

        rlCheckout.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), CheckoutScanActivity.class);
            startActivity(intent);
        });

        rlCheckProduct.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ProductCheckActivity.class);
            startActivity(intent);
        });

        rlCatalogProduct.setOnClickListener(view -> {

        });

        rlRepair.setOnClickListener(view -> {

        });

        rlSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), SettingActivity.class);
            startActivity(intent);
        });
    }
}