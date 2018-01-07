package com.iguanafix.jorgegonzalez.contacts.dtos;

import com.google.gson.annotations.SerializedName;


public class Address {
    public final static String HOME = "home";
    public final static String WORK = "work";

    @SerializedName("home")
    private String mHome;
    @SerializedName("work")
    private String mWork;

    public String getmHome() {
        return mHome !=  null ? mHome : "-";
    }

    public void setmHome(String mHome) {
        this.mHome = mHome;
    }

    public String getmWork() {
        return mWork !=  null ? mWork : "-";
    }

    public void setmWork(String mWork) {
        this.mWork = mWork;
    }

}
