package com.iguanafix.jorgegonzalez.contacts.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.dtos.Phone;
import com.iguanafix.jorgegonzalez.contacts.viewmodel.ContactViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsDetail extends AppCompatActivity implements ContactViewModel.LoadingData{
    private static final String CONTACT_KEY = "CONTACT_KEY";
    private ContactViewModel mViewModel;
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.thumb)
    public SimpleDraweeView mThumb;
    @BindView(R.id.contact_name)
    public TextView mContactName;
    @BindView(R.id.creation)
    public TextView mCreation;
    @BindView(R.id.birth_date_txt)
    public TextView mBirthDate;
    @BindView(R.id.house_button)
    public ImageButton mHouseButton;
    @BindView(R.id.cellphone_button)
    public ImageButton mCellphoneButton;
    @BindView(R.id.office_button)
    public ImageButton mOfficeButton;
    @BindView(R.id.home_txt)
    public TextView mHomeAddress;
    @BindView(R.id.work_txt)
    public TextView mWorkAddress;
    @BindView(R.id.content_detail)
    public View mContentView;
    @BindView(R.id.progress_bar_contact_detail)
    public ProgressBar mProgressContact;
    @BindView(R.id.retry_button)
    public Button mRetryButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        initComponents();
        createViewModel();
    }

    public static Intent newIntent(Context context, String userId){
        return new Intent(context, ContactsDetail.class).putExtra(CONTACT_KEY, userId);
    }

    private void createViewModel(){
        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mViewModel.setListener(this);
        mViewModel.getContactDetail(this,getIntent().getStringExtra(CONTACT_KEY));
    }

    private void initComponents(){
        Fresco.initialize(this);
        ButterKnife.bind(this);
        mContext = this;
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onLoadData() {
        mThumb.setImageURI(mViewModel.getmContact().getmThumb());
        mContactName.setText(getString(R.string.fullname).replace("{LASTNAME}", mViewModel.getmContact().getmLastName())
                .replace("{FIRSTNAME}",mViewModel.getmContact().getmFirstName()));

        mCreation.setText(mViewModel.getmContact().getmCreatedAtFormat());
        mBirthDate.setText(mViewModel.getmContact().getmBirthDateFormat());
        mHomeAddress.setText(mViewModel.getmContact().getmAddresses().getmHome());
        mWorkAddress.setText(mViewModel.getmContact().getmAddresses().getmWork());
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestingData() {
        mProgressContact.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void onRequestFinished(Boolean finishOk) {
        if(finishOk) {
            mContentView.setVisibility(View.VISIBLE);
            mRetryButton.setVisibility(View.GONE);
        }else{
            mContentView.setVisibility(View.GONE);
            mRetryButton.setVisibility(View.VISIBLE);
        }
        mProgressContact.setVisibility(View.GONE);
    }

    @OnClick({R.id.house_button, R.id.cellphone_button, R.id.office_button})
    public void onCallContact(View view){
        Phone phone = null;
        switch (view.getId()){
            case(R.id.house_button):{
                phone = mViewModel.getmContact().getPhone(Phone.HOME);
                break;
            }
            case(R.id.cellphone_button):{
                phone = mViewModel.getmContact().getPhone(Phone.CELLPHONE);
                break;
            }
            case(R.id.office_button):{
                phone = mViewModel.getmContact().getPhone(Phone.OFFICE);
                break;
            }
        }
        if(phone != null)
            phone.onCallContact(mContext);

    }

    @OnClick(R.id.thumb)
    public void onShowPhoto(){
        if(mViewModel.getmContact().getmPhoto() != null)
            mContext.startActivity(PhotoFullscreenActivity.newIntent(mContext, mViewModel.getmContact().getmPhoto(), mContactName.getText().toString()));
        else
            Toast.makeText(mContext,R.string.not_have_photo,Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.retry_button)
    public void onRetryListContact(){
        mRetryButton.setVisibility(View.GONE);
        mViewModel.getContactDetail(this,getIntent().getStringExtra(CONTACT_KEY));
    }

}
