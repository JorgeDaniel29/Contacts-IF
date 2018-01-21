package com.iguanafix.jorgegonzalez.contacts.response;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApiAdapter {
    private static ContactsApiServices mInstance;

    public static ContactsApiServices getApiService(){

        String mBaseUrl = "https://private-d0cc1-iguanafixtest.apiary-mock.com";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        if(mInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            mInstance = retrofit.create(ContactsApiServices.class);
        }

        return mInstance;
    }

}
