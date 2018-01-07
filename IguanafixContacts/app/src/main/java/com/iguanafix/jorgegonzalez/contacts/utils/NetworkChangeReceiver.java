package com.iguanafix.jorgegonzalez.contacts.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetworkChange mListener;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(mListener != null)
            mListener.onNetworkChange(NetworkUtil.getConnectivityStatus(context));
    }

    public void setListener(NetworkChange listener){
        mListener = listener;
    }

    public interface NetworkChange{
        void onNetworkChange(Boolean isConnected);
    }
}