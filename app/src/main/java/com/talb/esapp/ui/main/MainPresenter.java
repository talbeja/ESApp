package com.talb.esapp.ui.main;

// Empty, as all data is displayed on the User fragment
public class MainPresenter implements  MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

}
