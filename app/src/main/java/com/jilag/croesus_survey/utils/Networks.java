package com.jilag.croesus_survey.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class Networks {

    public Context context;

    public Networks() {
    }

    public Networks(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    /*
    This method actually checks if device is connected to internet(There is a possibility it's connected to a network but not to internet).
     */
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
