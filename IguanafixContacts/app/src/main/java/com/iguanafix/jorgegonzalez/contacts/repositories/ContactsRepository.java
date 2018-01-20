package com.iguanafix.jorgegonzalez.contacts.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.response.ContactApiAdapter;
import com.iguanafix.jorgegonzalez.contacts.response.ContactApiResponse;
import com.iguanafix.jorgegonzalez.contacts.response.ResponseContactCallBack;
import com.iguanafix.jorgegonzalez.contacts.response.ResponseContactDetailCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Response;


public class ContactsRepository implements Repository{

    @Override
    public void getConstacts(final MutableLiveData<ContactApiResponse> mutableLiveData) {
        ContactApiAdapter.getApiService().getContactList().enqueue(new ResponseContactCallBack() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Contact>> call, @NonNull Response<ArrayList<Contact>> response) {
                if(response.isSuccessful())
                    mutableLiveData.setValue(new ContactApiResponse(sortByLastNameAndFirstName(response.body())));
                else{
                    int message;
                    switch (response.code()) {
                        case 404:
                            message = R.string.not_found;
                            break;
                        case 500:
                            message = R.string.server_broken;
                            break;
                        default:
                            message = R.string.unknown_error;
                    }
                    mutableLiveData.setValue(new ContactApiResponse(message));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Contact>> call, @NonNull Throwable t) {
                int message;
                if (t instanceof IOException)
                    message = R.string.network_failed;
                else
                    message = R.string.parse_error;
                mutableLiveData.setValue(new ContactApiResponse(message));
            }
        });
    }

    @Override
    public void getConstact(final MutableLiveData<ContactApiResponse> mutableLiveData, String contactId) {
        ContactApiAdapter.getApiService().getContactDetail(contactId).enqueue(new ResponseContactDetailCallBack() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.isSuccessful())
                    mutableLiveData.setValue(new ContactApiResponse(response.body()));
                else{
                    int message;
                    switch (response.code()) {
                        case 404:
                            message = R.string.not_found;
                            break;
                        case 500:
                            message = R.string.server_broken;
                            break;
                        default:
                            message = R.string.unknown_error;
                    }
                    mutableLiveData.setValue(new ContactApiResponse(message));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                int message;
                if (t instanceof IOException)
                    message = R.string.network_failed;
                else
                    message = R.string.parse_error;
                mutableLiveData.setValue(new ContactApiResponse(message));
            }
        });
    }

    private ArrayList<Contact> sortByLastNameAndFirstName(ArrayList<Contact> list){
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getmFirstName().compareTo(o2.getmFirstName());
            }
        });
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getmLastName().compareTo(o2.getmLastName());
            }
        });

        return list;
    }
}
