package com.d2i.stockmanagement.screen.productcheck;

import androidx.annotation.NonNull;
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

import com.d2i.stockmanagement.R;
import com.d2i.stockmanagement.entity.EPC;
import com.d2i.stockmanagement.entity.InventoryTag;
import com.d2i.stockmanagement.entity.request.TagCreateRequest;
import com.d2i.stockmanagement.service.ProductCheckService;
import com.d2i.stockmanagement.utils.ApiHelper;
import com.d2i.stockmanagement.utils.BluetoothHelper;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductCheckActivity extends AppCompatActivity {
    RecyclerView rvScannedProduct;
    BluetoothHelper bluetoothHelper;
    TextView tvStatus;
    TableAdapter tableAdapterScanned;
    TableAdapter tableAdapterServer;

    ArrayList<UHFTAGInfo> scannedProducts = new ArrayList<>();
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

    @SuppressLint("NotifyDataSetChanged")
    private void initUHF() {
        String address = bluetoothHelper.getAddress();

        uhf.init(getApplicationContext());
        uhf.connect(address, btStatus);
        uhf.setKeyEventCallback(keyCode -> {
            if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                if (loopFlag) {
                    loopFlag = false;
                    uhf.stopInventory();
                    checkProducts();
                } else {
                    new TagThread().start();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void checkProducts() {
        ArrayList<String> result = new ArrayList<>();

        for (UHFTAGInfo info: scannedProducts) {
            result.add(info.getEPC());
        }

        insertIntoDatabase(result);

        LinkedHashSet<String> epcs = new LinkedHashSet<>(result);

        ArrayList<InventoryTag> tags = new ArrayList<>();

        int index = 1;
        for (String epc : epcs) {
            InventoryTag tag = new InventoryTag();
            tag.setNo(index);
            tag.setProductName(epc);
            tag.setRfid("2");
            tag.setStatus("5");
            tags.add(tag);
            index++;
        }

//        ArrayList<InventoryTag> tag = new ArrayList<>(tags);
        tableAdapterScanned.setInventoryTags(tags);
        tableAdapterScanned.notifyDataSetChanged();
    }

    private void insertIntoDatabase(ArrayList<String> data)
    {
        ArrayList<EPC> epcs = new ArrayList<>();
        for(String item : data) {
            EPC epc = new EPC();
            epc.setDataEpc(item);
            epcs.add(epc);
        }

        bulkInsertToDatabase(epcs);
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
        @SuppressLint("SetTextI18n")
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
            if (uhf.startInventoryTag()) {
                loopFlag = true;
            }

            while (loopFlag) {
                List<UHFTAGInfo> list = getUHFInfo();

                if (list == null || list.size() == 0) {
                    SystemClock.sleep(1);
                } else {
                    handler.post(() -> scannedProducts.addAll(list));
                }
            }
            stopInventory();
        }
    }

    private void bulkInsertToDatabase(ArrayList<EPC> epcs) {
        ApiHelper apiHelper = new ApiHelper(this);
        apiHelper.setBaseUrl("https://singgalang-adonis.herokuapp.com/");

        Retrofit retrofit = apiHelper.getRetrofit();
        ProductCheckService service = retrofit.create(ProductCheckService.class);

        TagCreateRequest request = new TagCreateRequest();
        request.setData(epcs);

        Call<Void> call = service.post(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("EPC Tag", "Success to insert into Database");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("EPC Tag", "Failed to insert into Database");
            }
        });
    }
}