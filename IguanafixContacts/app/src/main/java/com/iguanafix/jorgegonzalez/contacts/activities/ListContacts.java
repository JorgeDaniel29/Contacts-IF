package com.iguanafix.jorgegonzalez.contacts.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iguanafix.jorgegonzalez.contacts.adapter.ContactListAdapter;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.adapter.ContactViewHolder;
import com.iguanafix.jorgegonzalez.contacts.customclass.CustomLayoutManager;
import com.iguanafix.jorgegonzalez.contacts.dtos.Contact;
import com.iguanafix.jorgegonzalez.contacts.response.ContactApiResponse;
import com.iguanafix.jorgegonzalez.contacts.utils.NetworkChangeReceiver;
import com.iguanafix.jorgegonzalez.contacts.viewmodel.ContactViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListContacts extends AppCompatActivity implements /*ContactViewModel.LoadingData,*/ ContactViewHolder.ScrollPosition {
    private ContactViewModel mViewModel;
    private ContactListAdapter mAdapter;
    @BindView(R.id.list_contact)
    public RecyclerView mListContact;
    @BindView(R.id.search_tool_bar)
    public Toolbar mSearchToolbar;
    @BindView(R.id.progress_bar_contact)
    public ProgressBar mProgressContact;
    @BindView(R.id.retry_button)
    public Button mRetryButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        ButterKnife.bind(this);
        initComponents();
        createViewModel();
    }

    private void createViewModel(){
        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mViewModel.loadContacts(this);
        mViewModel.getListContacts().observe(this, new Observer<ContactApiResponse>() {

            @Override
            public void onChanged(@Nullable ContactApiResponse contactApiResponse) {
                if (contactApiResponse != null) {
                    if (!contactApiResponse.getmHasError()) {
                        mAdapter.setmData(contactApiResponse.getmConstacts());
                        mListContact.setVisibility(View.VISIBLE);
                        mRetryButton.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(mContext, contactApiResponse.getmMessageError(), Toast.LENGTH_SHORT).show();
                        mListContact.setVisibility(View.GONE);
                        mRetryButton.setVisibility(View.VISIBLE);
                    }
                    mProgressContact.setVisibility(View.GONE);

                }
            }
        });
    }

    private void initComponents(){
        mContext = this;
        Fresco.initialize(this);

        setSupportActionBar(mSearchToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mSearchToolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
        mSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mListContact.setLayoutManager(new CustomLayoutManager(this));
        mAdapter = new ContactListAdapter(this);
        mListContact.setAdapter(mAdapter);
    }


    @Override
    public void onScroll(int position) {
        mListContact.smoothScrollToPosition(position);
    }


    @OnClick(R.id.retry_button)
    public void onRetryListContact(){
        mRetryButton.setVisibility(View.GONE);
        mProgressContact.setVisibility(View.VISIBLE);
    }
}
