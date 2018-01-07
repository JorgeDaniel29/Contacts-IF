package com.iguanafix.jorgegonzalez.contacts.response;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ContactsApiServices {
    @GET("/contacts")
    Call<ArrayList<Contact>> getContactList();

    @GET("/contacts/{contact_id}")
    Call<Contact> getContactDetail(@Path("contact_id") String id);
}
