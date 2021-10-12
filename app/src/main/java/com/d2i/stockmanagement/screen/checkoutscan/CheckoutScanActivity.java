package com.d2i.stockmanagement.screen.checkoutscan;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.d2i.stockmanagement.R;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.Objects;

public class CheckoutScanActivity extends AppCompatActivity {
    private final RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    private final Handler handler = new Handler();

    private boolean isStarted = false;

    TextView tvEpc;
    TextView tvStatus;

    BTStatus btStatus = new BTStatus();

    CheckoutScanRepository checkoutScanRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_checkout_scan);

        uhf.init(getApplicationContext());
        uhf.setBeep(true);
        checkoutScanRepository = new CheckoutScanRepository();

        initUI();

        SharedPreferences sharedPreferences = getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("bluetooth_address", "");

        if (uhf.getConnectStatus() == ConnectionStatus.DISCONNECTED) {
            Toast.makeText(getBaseContext(), "Bluetooth address: " + address, Toast.LENGTH_SHORT).show();
            uhf.connect(address, btStatus);
        } else {
            Toast.makeText(getBaseContext(), "UHF Belum connect bluetooth", Toast.LENGTH_SHORT).show();
        }

        uhf.setKeyEventCallback(keycode -> {
            if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                startThread();
            }
        });
    }

    private void initUI() {
        tvEpc = findViewById(R.id.epc);
        tvStatus = findViewById(R.id.status);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void startThread() {
        if (isStarted) {
            Toast.makeText(getBaseContext(),"Thread Already running", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getBaseContext(),"Thread starting", Toast.LENGTH_SHORT).show();
        isStarted = true;
        new TagThread().start();
    }


    class TagThread extends Thread {
        public void run() {
            handler.post(() -> {
                Toast.makeText(CheckoutScanActivity.this, "Thread Started", Toast.LENGTH_SHORT).show();
            });

            if (uhf.startInventoryTag()) {
                handler.post(() -> {
                    Toast.makeText(CheckoutScanActivity.this, "UHF Started", Toast.LENGTH_SHORT).show();
                    isStarted = false;
                });
                try {
                    UHFTAGInfo info = uhf.inventorySingleTag();

                    if (info != null) {
                        Toast.makeText(CheckoutScanActivity.this, info.getEPC(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CheckoutScanActivity.this, "UHF Belum terbaca", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CheckoutScanActivity.this, "Gagal Membaca UHF", Toast.LENGTH_SHORT).show();
                }


                if(uhf.stopInventory()) {
                    Toast.makeText(CheckoutScanActivity.this, "UHF Stopped", Toast.LENGTH_SHORT).show();
                }
            } else {
                handler.post(() -> {
                    Toast.makeText(CheckoutScanActivity.this, "UHF Failed to start", Toast.LENGTH_SHORT).show();
                    isStarted = false;
                });
            }
        }
    }

    class BTStatus implements ConnectionStatusCallback<Object> {
        @Override
        public void getStatus(final ConnectionStatus connectionStatus, final Object device1) {
            runOnUiThread(() -> {
                BluetoothDevice device = (BluetoothDevice) device1;
                if (connectionStatus == ConnectionStatus.CONNECTED) {
                    handler.post(() -> tvStatus.setText("Device: Connected"));
                } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                    handler.post(() -> {
                        tvStatus.setText("Device: Disconnected");
                    });
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uhf.disconnect();
    }
}