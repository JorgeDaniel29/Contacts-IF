package com.iguanafix.jorgegonzalez.contacts.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.repositories.ContactsRepository;
import com.iguanafix.jorgegonzalez.contacts.response.ContactApiResponse;


public class ContactViewModel extends ViewModel {
    private Contact mContact;
    private MutableLiveData<ContactApiResponse> mLiveListContacts = new MutableLiveData<>();
    private MutableLiveData<ContactApiResponse> mLiveContact = new MutableLiveData<>();
    private ContactsRepository mRepository = new ContactsRepository();

    public Contact getmContact() {
        return mContact;
    }

    public void setmContact(Contact mContact) {
        this.mContact = mContact;
    }

    public void loadContactList(){
        mRepository.getConstactList(mLiveListContacts);
    }

    public LiveData<ContactApiResponse> getListContacts(){
        return mLiveListContacts;
    }

    public void loadContact(String contactId){
        mRepository.getConstact(mLiveContact, contactId);
    }

    public LiveData<ContactApiResponse> getContact(){
        return mLiveContact;
    }

}
