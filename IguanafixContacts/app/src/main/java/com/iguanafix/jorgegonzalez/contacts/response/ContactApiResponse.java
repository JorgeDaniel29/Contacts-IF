package com.iguanafix.jorgegonzalez.contacts.response;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;

import java.util.ArrayList;

public class ContactApiResponse {
    private ArrayList<Contact> mConstacts;
    private Integer mMessageError;
    private Boolean mHasError;

    public ContactApiResponse(ArrayList<Contact> constacts) {
        this.mConstacts = constacts;
        this.mMessageError = null;
        this.mHasError = false;
    }

    public ContactApiResponse(int messageError) {
        this.mConstacts = null;
        this.mMessageError = messageError;
        this.mHasError = true;
    }

    public ArrayList<Contact> getmConstacts() {
        return mConstacts;
    }

    public Integer getmMessageError() {
        return mMessageError;
    }

    public Boolean getmHasError() {
        return mHasError;
    }
}
