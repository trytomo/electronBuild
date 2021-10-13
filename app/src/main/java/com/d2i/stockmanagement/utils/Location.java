package com.d2i.stockmanagement.utils;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class Location {
    private final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 100;

    public Location(Activity activity) {
        String[] requests = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(activity, requests, ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
    }
}
