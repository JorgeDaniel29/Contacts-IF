package com.iguanafix.jorgegonzalez.contacts.adapter;

import android.view.View;
import android.widget.TextView;

import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SeparatorViewHolder extends BaseViewHolder {
    @BindView(R.id.text_separator)
    TextView mTextSeparator;
    @BindView(R.id.line_separator)
    View mLineSeparator;

    public SeparatorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void doUpdate(Contact contact, int position) {
        mTextSeparator.setText(contact.getmLastName().substring(0,1));
    }
}
