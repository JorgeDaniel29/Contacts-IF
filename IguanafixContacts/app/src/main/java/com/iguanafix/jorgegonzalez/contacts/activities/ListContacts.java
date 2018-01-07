package com.iguanafix.jorgegonzalez.contacts.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iguanafix.jorgegonzalez.contacts.adapter.ContactListAdapter;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.adapter.ContactViewHolder;
import com.iguanafix.jorgegonzalez.contacts.customclass.CustomLayoutManager;
import com.iguanafix.jorgegonzalez.contacts.utils.NetworkChangeReceiver;
import com.iguanafix.jorgegonzalez.contacts.viewmodel.ContactViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListContacts extends AppCompatActivity implements ContactViewModel.LoadingData, ContactViewHolder.ScrollPosition {
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
    private NetworkChangeReceiver mNetworkChangeReceiver;

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
        mViewModel.setListener(this);
        mViewModel.getListConstact(this);
    }

    private void initComponents(){
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
    public void onLoadData() {
        mAdapter.setmData(mViewModel.getContacts());
        mListContact.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestingData() {
        mProgressContact.setVisibility(View.VISIBLE);
        mListContact.setVisibility(View.GONE);
    }

    @Override
    public void onRequestFinished(Boolean finishOk) {
        if(finishOk) {
            mListContact.setVisibility(View.VISIBLE);
            mRetryButton.setVisibility(View.GONE);
        }else{
            mListContact.setVisibility(View.GONE);
            mRetryButton.setVisibility(View.VISIBLE);
        }
        mProgressContact.setVisibility(View.GONE);
    }

    @Override
    public void onScroll(int position) {
        mListContact.smoothScrollToPosition(position);
    }


    @OnClick(R.id.retry_button)
    public void onRetryListContact(){
        mRetryButton.setVisibility(View.GONE);
        mViewModel.getListConstact(this);
    }
}
