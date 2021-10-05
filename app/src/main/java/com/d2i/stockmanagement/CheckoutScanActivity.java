package com.d2i.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;
import com.rscja.deviceapi.interfaces.KeyEventCallback;

import java.util.ArrayList;
import java.util.List;

public class CheckoutScanActivity extends AppCompatActivity {
    public RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    boolean isRunning = false;
    boolean isScanning = false;
    boolean isExit = false;
    boolean loopFlag = false;

    ListView listView;
    ArrayAdapter<String> listAdapter;

    BTStatus btStatus = new BTStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_scan);

        uhf.init(getApplicationContext());

        listView = findViewById(R.id.list_item);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("bluetooth_address", "");
        uhf.connect(address, btStatus);

        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(view -> {
            startThread();
        });

        stopButton.setOnClickListener(view -> {
            isRunning = false;
            isScanning = false;
            isExit = false;
            loopFlag = false;
        });

        uhf.startInventoryTag();

        uhf.setKeyEventCallback(keycode -> {
            Log.d("TEST", "  keycode =" + keycode + "   ,isExit=" + isExit);
            if (!isExit && uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                if (loopFlag) {
                    stopInventory();
                } else {
                    startThread();
                }
            }
        });
    }

    public void startThread() {
        if (isRunning) {
            return;
        }
        isRunning = true;
//        cbFilter.setChecked(false);
        new TagThread().start();
    }

    class TagThread extends Thread {
        public void run() {
            if (uhf.startInventoryTag()) {
                loopFlag = true;
                isScanning = true;
                //success
            } else {
                //fail
            }

            isRunning = false;

            while (loopFlag) {
                List<UHFTAGInfo> list = getUHFInfo();

                if (list == null || list.size() == 0) {
                    SystemClock.sleep(1);
                } else {
                    for (UHFTAGInfo info: list) {
                        uhf.setBeep(true);
                        Log.i("EPC", info.getEPC());
                    }
                }
            }
            stopInventory();
        }
    }

    private void stopInventory() {
        loopFlag = false;
//        cancelInventoryTask();
        boolean result = uhf.stopInventory();
        if(isScanning) {
            ConnectionStatus connectionStatus = uhf.getConnectStatus();

            if (result || connectionStatus == ConnectionStatus.DISCONNECTED) {
                //success
            } else {
                //failed
            }
            if (connectionStatus == ConnectionStatus.CONNECTED) {
            }
            isScanning = false;
        }
    }

    private synchronized List<UHFTAGInfo> getUHFInfo() {
        List<UHFTAGInfo> list = uhf.readTagFromBufferList();
        return list;
    }

    class BTStatus implements ConnectionStatusCallback<Object> {
        @Override
        public void getStatus(final ConnectionStatus connectionStatus, final Object device1) {
            runOnUiThread(() -> {
                BluetoothDevice device = (BluetoothDevice) device1;
                if (connectionStatus == ConnectionStatus.CONNECTED) {
                    Log.i("TEST", "CONNECTED");
                } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                    Log.i("TEST", "DISCONNECTED");
                }
            });
        }
    }
}