package com.iguanafix.jorgegonzalez.contacts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

    public static Boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;

        if(cm != null) {
            activeNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (activeNetwork != null && activeNetwork.isConnected())
                return true;

            activeNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (activeNetwork != null && activeNetwork.isConnected())
                return true;

            activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnected())
                return true;
        }

        return false;
    }
}
