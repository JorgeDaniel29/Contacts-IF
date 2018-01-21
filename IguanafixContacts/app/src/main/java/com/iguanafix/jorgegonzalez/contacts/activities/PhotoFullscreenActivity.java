package com.iguanafix.jorgegonzalez.contacts.activities;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.iguanafix.jorgegonzalez.contacts.R;
import com.iguanafix.jorgegonzalez.contacts.customclass.ControllerListenerImage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoFullscreenActivity extends AppCompatActivity{
    public final static String PHOTO_KEY = "PHOTO_KEY";
    private static final String NAME_KEY = "NAME_KEY";
    @BindView(R.id.photo)
    SimpleDraweeView mPhotoFullScreen;
    @BindView(R.id.progress_bar_full_image)
    ProgressBar mProgressBarFullImage;
    @BindView(R.id.toolbar_photo_full_sccreen)
    Toolbar mToolbarPhotoFullScreen;
    @BindView(R.id.name_contact_title)
    TextView mNameContact;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fullscreen);
        Fresco.initialize(this);
        ButterKnife.bind(this);
        initComponent();
    }

    public static Intent newIntent(Context context, String url, String name){
        return new Intent(context, PhotoFullscreenActivity.class).putExtra(PHOTO_KEY, url).putExtra(NAME_KEY, name);
    }

    private void initComponent(){
        mContext = this;
        String photoUrl = null;
        String nameContact = null;

        if(getIntent().getExtras() != null) {
            photoUrl = getIntent().getExtras().getString(PHOTO_KEY);
            nameContact = getIntent().getExtras().getString(NAME_KEY);
        }

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(photoUrl))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(false)
                .build();

        ControllerListenerImage listenerImage = new ControllerListenerImage();
        listenerImage.getmSatisfactoryImage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mProgressBarFullImage.setVisibility(View.GONE);
                if(aBoolean != null && aBoolean){
                    int color = getResources().getColor(R.color.blue400);
                    RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
                    roundingParams.setBorder(color,10.0f);
                    roundingParams.setRoundAsCircle(true);
                    mPhotoFullScreen.getHierarchy().setRoundingParams(roundingParams);
                }
                else
                    Toast.makeText(mContext, R.string.not_load_image,Toast.LENGTH_SHORT).show();

            }
        });

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setControllerListener(listenerImage)
                .build();

        mPhotoFullScreen.setController(controller);
        mNameContact.setText(nameContact);

        setSupportActionBar(mToolbarPhotoFullScreen);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbarPhotoFullScreen.setNavigationIcon(R.drawable.ic_action_arrow_back);
        mToolbarPhotoFullScreen.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
