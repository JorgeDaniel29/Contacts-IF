package com.iguanafix.jorgegonzalez.contacts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void doUpdate(Contact contact, int position);
}
