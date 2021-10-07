package com.d2i.stockmanagement.screen.productcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.TextView;

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

public class ProductCheckActivity extends AppCompatActivity {
    RecyclerView rvScannedProduct;
    BluetoothHelper bluetoothHelper;
    TextView tvStatus;

    private final Set<InventoryTag> scannedProducts = new LinkedHashSet<>();
    private final RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    private final Handler handler = new Handler();
    private final BTStatus btStatus = new BTStatus();

    Boolean isScanning = false;

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
                if (isScanning) {
                    isScanning = false;
                    uhf.stopInventory();
                } else {
                    isScanning = true;
                    new TagThread().start();
                }
            }
        });
    }


    private void initScannedProduct() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ArrayList<InventoryTag> inventoryList = new ArrayList<>(scannedProducts);
        TableAdapter tableAdapter = new TableAdapter(inventoryList);
        rvScannedProduct.setAdapter(tableAdapter);
        rvScannedProduct.setLayoutManager(layoutManager);
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
        public void run() {
            uhf.startInventoryTag();

            while (isScanning) {
                List<UHFTAGInfo> list = uhf.readTagFromBufferList();

                if (list.size() == 0) {
                    SystemClock.sleep(1);
                } else {
                    for (UHFTAGInfo info : list) {
                        InventoryTag tag = new InventoryTag();
                        tag.setNo(1);
                        tag.setRfid(info.getEPC());
                        tag.setProductName("ya");
                        tag.setStatus("oke");
                        handler.post(() -> {
                            scannedProducts.add(tag);
                        });
                    }
                }
            }

        }
    }
}