package com.example.weather_app.model.request;

import com.example.weather_app.model.Callback.WeatherDataCallback;
import com.example.weather_app.model.pojo.Weather;
import com.example.weather_app.model.pojo.WeatherDataBySearch;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestGet {

    //https://api.openweathermap.org/data/2.5/onecall?lat=30.73&lon=76.77&exclude=current&appid=24f9aa4eec3eeabf9ddafdcbea078db3

   // api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}


    @GET("data/2.5/onecall?")
    Call<WeatherDataCallback> getAllData(
            @Query("lat") Float lat,
            @Query("lon") Float lon,
            @Query("exclude") String exclude,
            @Query("appid") String appid
    );

     @GET("data/2.5/weather?")
    Call<WeatherDataBySearch> getDataBycity(
            @Query("q") String city,
            @Query("appid") String appid


     );
}
