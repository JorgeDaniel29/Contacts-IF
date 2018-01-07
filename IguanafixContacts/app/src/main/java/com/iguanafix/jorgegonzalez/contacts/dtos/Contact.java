package com.iguanafix.jorgegonzalez.contacts.dtos;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Contact {
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("birth_date")
    private String mBirthDate;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("phones")
    private ArrayList<Phone> mPhones;
    @SerializedName("thumb")
    private String mThumb;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("addresses")
    private ArrayList<Address> mAddresses;

    private Boolean mShowPhones = false;
    private Boolean mContactSeparator = false;
    private final static SimpleDateFormat sdfOrigin = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmCreatedAtFormat() {
        SimpleDateFormat sdfResponse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date dateCreation = null;
        try {
            dateCreation = sdfResponse.parse(mCreatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateCreation !=  null ? sdfOrigin.format(dateCreation) : "-";
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getmBirthDateFormat() {
        SimpleDateFormat sdfBirthDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dateBirthDay = null;
        try {
            dateBirthDay = sdfBirthDay.parse(mBirthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateBirthDay !=  null ? sdfOrigin.format(dateBirthDay) : "-";
    }

    public String getmCreatedAt() {
        return mCreatedAt;
    }

    public String getmBirthDate() {
        return mBirthDate;
    }

    public void setmBirthDate(String mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public ArrayList<Phone> getmPhones() {
        return mPhones;
    }

    public Phone getPhone(String typePhone){
        for (Phone p: mPhones) {
            if(p.getmType().equals(typePhone))
                return p;
        }
        return null;
    }

    public void setmPhones(ArrayList<Phone> mPhones) {
        this.mPhones = mPhones;
    }

    public String getmThumb() {
        return mThumb;
    }

    public void setmThumb(String mThumb) {
        this.mThumb = mThumb;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public Address getmAddresses() {
        return mAddresses.get(0);
    }

    public void setmAddresses(ArrayList<Address> mAddresses) {
        this.mAddresses = mAddresses;
    }

    public void setmContactSeparator() {
        this.mContactSeparator = true;
    }

    public Boolean isContactSeparator(){
        return mContactSeparator;
    }

    public void setmShowPhones(Boolean mShowPhones) {
        this.mShowPhones = mShowPhones;
    }

    public Boolean getmShowPhones() {
        return mShowPhones;
    }
}
