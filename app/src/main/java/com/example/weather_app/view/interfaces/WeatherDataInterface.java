package com.example.weather_app.view.interfaces;

import com.example.weather_app.model.Callback.WeatherDataCallback;
import com.example.weather_app.model.pojo.Daily;
import com.example.weather_app.model.pojo.Hourly;
import com.example.weather_app.model.pojo.WeatherDataBySearch;

import java.util.List;

public interface WeatherDataInterface extends BaseInterface {

    void gettingWeatherData(WeatherDataCallback weatherDataCallback);

    void getdailydata(List<Daily> daily, List<Hourly> hourly);

    void getDatabySeach(WeatherDataBySearch weatherDataBySearch);

}
