package com.iguanafix.jorgegonzalez.contacts.customclass;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.iguanafix.jorgegonzalez.contacts.R;


public class ControllerListenerImage extends BaseControllerListener<ImageInfo>{
    private LoadingImage mLoadingImage;

    public ControllerListenerImage(LoadingImage mLoadingImage) {
        this.mLoadingImage = mLoadingImage;
    }

    @Override
    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
        mLoadingImage.onRequestFinished(true);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        mLoadingImage.onRequestFinished(false);
        Toast.makeText((Context) mLoadingImage, R.string.not_load_image,Toast.LENGTH_SHORT).show();
    }


    public interface LoadingImage{
        void onRequestFinished(Boolean finishOk);
    }
}
