package com.iguanafix.jorgegonzalez.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.R;

import java.util.ArrayList;


public class ContactListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final static int SEPARATOR_HEADER_VIEW = 0;
    private final static int CONTACT_VIEW = 1;

    private ArrayList<Contact> mData = new ArrayList<>();
    private Context mContext;

    public ContactListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;

        if(viewType == SEPARATOR_HEADER_VIEW)
            holder = new SeparatorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.separator_contact, parent, false));
        else
            holder = new ContactViewHolder(LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false), (ContactViewHolder.ScrollPosition) mContext);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        final Contact contact = mData.get(position);
        holder.doUpdate(contact, position);

    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).isContactSeparator() ? SEPARATOR_HEADER_VIEW : CONTACT_VIEW;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmData(ArrayList<Contact> data){
        String character = "&";
        Contact contact;

        mData.clear();

        for (Contact c: data) {
            if(mData.isEmpty() || !character.equals(c.getmLastName().substring(0,1))) {
                character = c.getmLastName().substring(0,1);
                contact = new Contact();
                contact.setmContactSeparator();
                contact.setmLastName(c.getmLastName());
                mData.add(contact);
                mData.add(c);
            }else
                mData.add(c);
        }

        notifyDataSetChanged();
    }


}
