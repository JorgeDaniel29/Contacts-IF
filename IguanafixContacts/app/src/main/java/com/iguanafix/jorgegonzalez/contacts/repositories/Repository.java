package com.iguanafix.jorgegonzalez.contacts.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.iguanafix.jorgegonzalez.contacts.response.ContactApiResponse;


/**
 * Created by jorgedanielgonzalez on 17/1/18.
 */

public interface Repository {
    void getConstactList(MutableLiveData<ContactApiResponse> mutableLiveData);
    void getConstact(MutableLiveData<ContactApiResponse> mutableLiveData, String contactId);
}
