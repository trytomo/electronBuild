package com.d2i.stockmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class BluetoothHelper {
    Context mContext;

    public BluetoothHelper(Context context) {
        mContext = context;
    }

    public String getAddress() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
        return sharedPreferences.getString("bluetooth_address", "");
    }

    public void setAddress(String address) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bluetooth_address", address);
        editor.apply();
    }
}
