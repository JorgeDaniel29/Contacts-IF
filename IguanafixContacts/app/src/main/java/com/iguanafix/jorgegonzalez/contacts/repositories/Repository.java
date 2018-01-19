package com.iguanafix.jorgegonzalez.contacts.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.iguanafix.jorgegonzalez.contacts.response.ContactApiResponse;


/**
 * Created by jorgedanielgonzalez on 17/1/18.
 */

public interface Repository {
    LiveData<ContactApiResponse> getConstacts(Context context);
}
