package com.d2i.stockmanagement.screen.productcheck;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.entity.InventoryTag;
import com.d2i.stockmanagement.utils.BluetoothHelper;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductCheckActivity extends AppCompatActivity {
    private final String TAG = "ProductCheckActivity";

    RecyclerView rvScannedProduct;
    BluetoothHelper bluetoothHelper;
    TextView tvStatus;
    TableAdapter tableAdapterScanned;
    TableAdapter tableAdapterServer;

    private final Set<UHFTAGInfo> scannedProducts = new LinkedHashSet<>();
    private final RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    private final Handler handler = new Handler();
    private final BTStatus btStatus = new BTStatus();

    Boolean loopFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_product_check);

        bluetoothHelper = new BluetoothHelper(this);

        initUHF();
        initUI();
        initScannedProduct();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (uhf.getConnectStatus() == ConnectionStatus.DISCONNECTED) {
            SharedPreferences sharedPreferences = getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
            String address = sharedPreferences.getString("bluetooth_address", "");
            uhf.connect(address, btStatus);
        }
    }

    private void initUI() {
        rvScannedProduct = findViewById(R.id.product_scanned);
        tvStatus = findViewById(R.id.status);
    }

    private void initUHF() {
        String address = bluetoothHelper.getAddress();

        uhf.init(getApplicationContext());
        uhf.connect(address, btStatus);
        uhf.setKeyEventCallback(keyCode -> {
            if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                if (loopFlag) {
                    uhf.stopInventory();
                } else {
                    new TagThread().start();
                }
            }
        });
    }

    private void initScannedProduct() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ArrayList<InventoryTag> inventoryList = new ArrayList<>();
        tableAdapterScanned = new TableAdapter(inventoryList);
        rvScannedProduct.setAdapter(tableAdapterScanned);
        rvScannedProduct.setLayoutManager(layoutManager);
    }

    private synchronized List<UHFTAGInfo> getUHFInfo() {
        return uhf.readTagFromBufferList();
    }

    private void stopInventory() {
        loopFlag = false;
    }

    class BTStatus implements ConnectionStatusCallback<Object> {
        @Override
        public void getStatus(final ConnectionStatus connectionStatus, final Object device1) {
            runOnUiThread(() -> {
                if (connectionStatus == ConnectionStatus.CONNECTED) {
                    handler.post(() -> tvStatus.setText("Device: Connected"));
                } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                    handler.post(() -> tvStatus.setText("Device: Disconnected"));
                }
            });
        }
    }

    class TagThread extends Thread {
        @SuppressLint("NotifyDataSetChanged")
        public void run() {
            if (uhf.startInventoryTag()) {
                loopFlag = true;
                handler.post(() -> {
                    Log.d(TAG, "Inventory tag successfully started");
                });
            } else {
                handler.post(() -> {
                    Log.d(TAG, "Failed to start inventory tag");
                });
            }

            long startTime = System.currentTimeMillis();
            while (loopFlag) {
                List<UHFTAGInfo> list = getUHFInfo();

                if (list == null || list.size() == 0) {
                    SystemClock.sleep(1);
                } else {
                    handler.post(() -> {
                        scannedProducts.addAll(list);
//                        tableAdapterScanned.notifyDataSetChanged();
                    });
                }

                if (System.currentTimeMillis() - startTime > 100) {
                    handler.post(() -> {
                        Log.i(TAG, "Nothing happened");
                    });
                }
            }
            stopInventory();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        uhf.disconnect();
    }
}