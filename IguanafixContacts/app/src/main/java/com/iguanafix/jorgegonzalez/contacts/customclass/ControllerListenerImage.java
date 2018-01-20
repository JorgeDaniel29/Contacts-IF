package com.iguanafix.jorgegonzalez.contacts.customclass;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;


public class ControllerListenerImage extends BaseControllerListener<ImageInfo>{
    private MutableLiveData<Boolean> mSatisfactoryImage = new MutableLiveData<>();

    @Override
    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
        mSatisfactoryImage.setValue(true);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        mSatisfactoryImage.setValue(false);
    }


    public MutableLiveData<Boolean> getmSatisfactoryImage() {
        return mSatisfactoryImage;
    }
}
