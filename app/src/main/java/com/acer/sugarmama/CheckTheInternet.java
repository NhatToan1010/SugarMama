package com.acer.sugarmama;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class CheckTheInternet {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((mobileConn != null && mobileConn.isConnected()) || (wifiConn != null && wifiConn.isConnected())){
            return true;
        }
        else {
            Toast.makeText(context, "Internet Connection Required!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
