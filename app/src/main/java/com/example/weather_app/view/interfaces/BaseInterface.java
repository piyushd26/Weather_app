package com.example.weather_app.view.interfaces;

public interface BaseInterface {
    void onFetchStart();
    void onFetchFailure(String message);
    void onFetchComplete();
}
