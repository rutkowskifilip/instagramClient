package com.example.instagram.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectPhotoViewModel extends ViewModel {

    private MutableLiveData<Integer> mutablePhoto;

    public SelectPhotoViewModel() {
        this.mutablePhoto = new MutableLiveData<>();

    }

    public MutableLiveData<Integer> getObservedPhoto(){
        return mutablePhoto;
    }

    public void setMutablePhoto(MutableLiveData<Integer> mutablePhoto) {
        this.mutablePhoto = mutablePhoto;
    }
    // TODO: Implement the ViewModel
}