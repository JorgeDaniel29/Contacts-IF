package com.iguanafix.jorgegonzalez.contacts.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.response.ContactApiAdapter;
import com.iguanafix.jorgegonzalez.contacts.response.ResponseContactCallBack;
import com.iguanafix.jorgegonzalez.contacts.response.ResponseContactDetailCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindString;
import retrofit2.Call;
import retrofit2.Response;


public class ContactViewModel extends ViewModel {
    private ArrayList<Contact> mListContact;
    private LoadingData mLoadDataListener;
    private Contact mContact;

    public void setListener(LoadingData listener){
        mLoadDataListener = listener;
    }

    public ArrayList<Contact> getContacts() {
        return mListContact;
    }

    public void setmListContact(ArrayList<Contact> mListContact) {
        this.mListContact = mListContact;
    }

    public Contact getmContact() {
        return mContact;
    }

    public void setmContact(Contact mContact) {
        this.mContact = mContact;
    }

    public void getListConstact(final Context context){
        Call<ArrayList<Contact>> call = ContactApiAdapter.getApiService().getContactList();
        mLoadDataListener.onRequestingData();
        call.enqueue(new ResponseContactCallBack() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Contact>> call, @NonNull Response<ArrayList<Contact>> response) {
                if(response.isSuccessful()){
                    mListContact = response.body();

                    sortByLastNameAndFirstName();

                    mLoadDataListener.onLoadData();
                    mLoadDataListener.onRequestFinished(true);
                }else {
                    String message;
                    switch (response.code()) {
                        case 404:
                            message = context.getString(R.string.not_found);
                            break;
                        case 500:
                            message = context.getString(R.string.server_broken);
                            break;
                        default:
                            message = context.getString(R.string.unknown_error);
                            break;
                    }

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    mLoadDataListener.onRequestFinished(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Contact>> call, @NonNull Throwable t) {
                mLoadDataListener.onRequestFinished(false);
                if (t instanceof IOException)
                    Toast.makeText(context, context.getString(R.string.network_failed), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, context.getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getContactDetail(final Context context, String userId){
        Call<Contact> call = ContactApiAdapter.getApiService().getContactDetail(userId);
        mLoadDataListener.onRequestingData();
        call.enqueue(new ResponseContactDetailCallBack() {
            @Override
            public void onResponse(@NonNull Call<Contact> call, @NonNull Response<Contact> response) {
                if(response.isSuccessful()){
                    mContact = response.body();
                    mLoadDataListener.onLoadData();
                    mLoadDataListener.onRequestFinished(true);
                }else {
                    String message;
                    switch (response.code()) {
                        case 404:
                            message = context.getString(R.string.not_found);
                            break;
                        case 500:
                            message = context.getString(R.string.server_broken);
                            break;
                        default:
                            message = context.getString(R.string.unknown_error);
                            break;
                    }

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    mLoadDataListener.onRequestFinished(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Contact> call, @NonNull Throwable t) {
                mLoadDataListener.onRequestFinished(false);
                if (t instanceof IOException)
                    Toast.makeText(context, context.getString(R.string.network_failed), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, context.getString(R.string.parse_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortByLastNameAndFirstName(){
        Collections.sort(mListContact, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getmFirstName().compareTo(o2.getmFirstName());
            }
        });
        Collections.sort(mListContact, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getmLastName().compareTo(o2.getmLastName());
            }
        });
    }

    public interface LoadingData{
        void onLoadData();
        void onRequestingData();
        void onRequestFinished(Boolean finishOk);
    }
}
