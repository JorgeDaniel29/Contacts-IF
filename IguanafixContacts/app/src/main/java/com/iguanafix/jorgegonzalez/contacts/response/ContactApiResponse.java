package com.iguanafix.jorgegonzalez.contacts.response;


import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;

import java.util.ArrayList;

public class ContactApiResponse {
    private ArrayList<Contact> mConstacts;
    private Contact mContact;
    private Integer mMessageError;
    private Boolean mHasError;

    public ContactApiResponse(ArrayList<Contact> constacts) {
        this.mConstacts = constacts;
        this.mMessageError = null;
        this.mHasError = false;
    }

    public ContactApiResponse(Contact contact) {
        this.mContact = contact;
        this.mConstacts = null;
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

    public Contact getmContact() {
        return mContact;
    }

    public Integer getmMessageError() {
        return mMessageError;
    }

    public Boolean getmHasError() {
        return mHasError;
    }
}
