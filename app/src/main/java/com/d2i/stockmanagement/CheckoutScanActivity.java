package com.d2i.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHFBLE;

public class CheckoutScanActivity extends AppCompatActivity {
    public RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_scan);

        isStart = uhf.startInventoryTag();

        while (isStart) {
            Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();
        }

        if(uhf.stopInventory()) {
            isStart = false;
        }
    }
}