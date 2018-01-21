package com.iguanafix.jorgegonzalez.contacts.response;


import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;

import java.util.ArrayList;

public class ContactApiResponse <T>{
    private ArrayList<Contact> mConstacts;
    private Contact mContact;
    private Integer mMessageError;
    private Boolean mHasError;

    public ContactApiResponse(ArrayList<Contact> constacts) {
        this.mConstacts = constacts;
        this.mContact = null;
        this.mMessageError = null;
        this.mHasError = false;
    }

    public ContactApiResponse(Contact contact) {
        this.mConstacts = null;
        this.mContact = contact;
        this.mMessageError = null;
        this.mHasError = false;
    }

    public ContactApiResponse(int messageError) {
        this.mConstacts = null;
        this.mContact = null;
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
