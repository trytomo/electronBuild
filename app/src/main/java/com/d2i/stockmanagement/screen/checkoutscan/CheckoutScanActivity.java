package com.d2i.stockmanagement.screen.checkoutscan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.d2i.stockmanagement.R;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.List;
import java.util.Objects;

public class CheckoutScanActivity extends AppCompatActivity {
    private final RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    private final Handler handler = new Handler();

    boolean isRunning = false;
    boolean isScanning = false;

    TextView tvEpc;
    TextView tvStatus;

    BTStatus btStatus = new BTStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_checkout_scan);

        uhf.init(getApplicationContext());
        uhf.setBeep(true);

        initUI();

        SharedPreferences sharedPreferences = getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("bluetooth_address", "");
        uhf.connect(address, btStatus);

        uhf.setKeyEventCallback(keycode -> {
            if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                isScanning = true;
                startThread();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        tvEpc = findViewById(R.id.epc);
        tvStatus = findViewById(R.id.status);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startThread() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        new TagThread().start();
    }

    class TagThread extends Thread {
        public void run() {
            Log.d("TEST", "Thread Running");
            isRunning = false;

            if (uhf.startInventoryTag()) {
                isScanning = true;
            }

            List<UHFTAGInfo> list = uhf.readTagFromBufferList();

            if (!isScanning && list.size() == 0) {
                Log.d("TEST", "Do if tag not found");
                SystemClock.sleep(1);
            } else {
                uhf.stopInventory();
                if (list.get(0) != null) {
                    handler.post(() -> tvEpc.setText(list.get(0).getEPC()));
//                    checkoutScanRepository.postUID(list.get(0).getEPC());
                }
            }
            isScanning = false;
        }
    }

    class BTStatus implements ConnectionStatusCallback<Object> {
        @SuppressLint("SetTextI18n")
        @Override
        public void getStatus(final ConnectionStatus connectionStatus, final Object device1) {
            runOnUiThread(() -> {
                BluetoothDevice device = (BluetoothDevice) device1;
                if (connectionStatus == ConnectionStatus.CONNECTED) {
                    handler.post(() -> tvStatus.setText("Device: Connected"));
                } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                    handler.post(() -> tvStatus.setText("Device: Disconnected"));
                }
            });
        }
    }
}