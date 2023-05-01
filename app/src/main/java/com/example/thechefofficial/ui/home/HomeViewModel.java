package com.example.thechefofficial.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel {

    private String recipeName;
    private String recipeDes;
    private String recipeDis;
    private String userId;


    public HomeViewModel() {
    }

    public HomeViewModel(String recipeName, String recipeDes, String recipeDis, String userId) {
        this.recipeName = recipeName;
        this.recipeDes = recipeDes;
        this.recipeDis = recipeDis;
        this.userId = userId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDes() {
        return recipeDes;
    }

    public void setRecipeDes(String recipeDes) {
        this.recipeDes = recipeDes;
    }

    public String getRecipeDis() {
        return recipeDis;
    }

    public void setRecipeDis(String recipeDis) {
        this.recipeDis = recipeDis;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}