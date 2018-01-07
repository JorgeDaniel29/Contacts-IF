package com.iguanafix.jorgegonzalez.contacts.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.activities.ContactsDetail;
import com.iguanafix.jorgegonzalez.contacts.activities.PhotoFullscreenActivity;
import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.dtos.Phone;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactViewHolder extends BaseViewHolder{
    @BindView(R.id.thumb)
    SimpleDraweeView mThumbContact;
    @BindView(R.id.contact_name)
    TextView mContactName;
    @BindView(R.id.type_phones)
    ConstraintLayout mConstraintTypePhones;
    @BindView(R.id.house_button)
    ImageButton mHouseButton;
    @BindView(R.id.cellphone_button)
    ImageButton mCellphoneButton;
    @BindView(R.id.office_button)
    ImageButton mOfficeButton;
    @BindView(R.id.detail_button)
    ImageButton mDetailButton;
    private Context mContext;
    private View mItemView;
    private ScrollPosition mScrollPosition;

    public ContactViewHolder(View itemView, ScrollPosition scrollPosition) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mItemView = itemView;
        mContext = itemView.getContext();
        mScrollPosition = scrollPosition;
    }

    /****/
    public void onShowPhotoFullScreen(String url, String name){
        if(url != null)
            mContext.startActivity(PhotoFullscreenActivity.newIntent(mContext, url, name));
        else
            Toast.makeText(mContext,R.string.not_have_photo,Toast.LENGTH_SHORT).show();
    }

    /****/
    public void onShowContact(String userId){
        mContext.startActivity(ContactsDetail.newIntent(mContext, userId));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void doUpdate(final Contact contact, final int position) {
        mThumbContact.setImageURI(contact.getmThumb());
        mContactName.setText(mContext.getString(R.string.fullname).replace("{LASTNAME}", contact.getmLastName())
                .replace("{FIRSTNAME}",contact.getmFirstName()));
        mThumbContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPhotoFullScreen(contact.getmPhoto(), mContactName.getText().toString());
            }
        });

        mHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.getPhone(Phone.HOME).onCallContact(mContext);
            }
        });
        mCellphoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.getPhone(Phone.CELLPHONE).onCallContact(mContext);
            }
        });
        mOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.getPhone(Phone.OFFICE).onCallContact(mContext);
            }
        });
        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowContact(contact.getmUserId());
            }
        });

        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConstraintTypePhones.getVisibility() == View.GONE) {
                    mScrollPosition.onScroll(position + 1);
                    mConstraintTypePhones.setVisibility(View.VISIBLE);
                    contact.setmShowPhones(true);
                } else {
                    mConstraintTypePhones.setVisibility(View.GONE);
                    contact.setmShowPhones(false);
                }
            }
        });

        mConstraintTypePhones.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if(contact.getmShowPhones())
            mConstraintTypePhones.setVisibility(View.VISIBLE);
        else
            mConstraintTypePhones.setVisibility(View.GONE);
    }

    public interface ScrollPosition{
        void onScroll(int position);
    }

}
