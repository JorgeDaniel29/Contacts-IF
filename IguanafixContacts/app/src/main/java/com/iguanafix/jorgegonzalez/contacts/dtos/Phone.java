package com.iguanafix.jorgegonzalez.contacts.dtos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.iguanafix.jorgegonzalez.contacts.R;


public class Phone {
    public final static String HOME = "Home";
    public final static String CELLPHONE = "Cellphone";
    public final static String OFFICE = "Office";

    @SerializedName("type")
    private String mType;
    @SerializedName("number")
    private String mNumber;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public Boolean isValidPhone(){
        return mNumber != null && !mNumber.isEmpty();
    }

    public void onCallContact(Context context){
        if(isValidPhone()) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getmNumber()));
            context.startActivity(intent);
        }
        else{
            Toast.makeText(context, context.getString(R.string.phones_message),Toast.LENGTH_SHORT).show();
        }
    }
}
