package com.talb.esapp.ui.main;

public class MainPresenter implements  MainContract.Presenter {
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

}
