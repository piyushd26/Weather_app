package com.example.weather_app.presenter;

import android.location.Address;
import android.util.Log;
import android.widget.Toast;

import com.example.weather_app.common.Constants;
import com.example.weather_app.model.Callback.WeatherDataCallback;
import com.example.weather_app.model.pojo.Current;
import com.example.weather_app.model.pojo.Daily;
import com.example.weather_app.model.pojo.Hourly;
import com.example.weather_app.model.pojo.Weather;
import com.example.weather_app.model.pojo.WeatherDataBySearch;
import com.example.weather_app.model.request.NetworkService;
import com.example.weather_app.model.request.RequestGet;
import com.example.weather_app.view.activities.MainActivity;
import com.example.weather_app.view.interfaces.WeatherDataInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataPresenter {
    WeatherDataInterface weatherDataInterface;
    List<Current> current;
    List<Daily> dailyList;

    public WeatherDataPresenter(WeatherDataInterface weatherDataInterface) {
     this.weatherDataInterface=weatherDataInterface;
    }


    public void getWeatherDataPresenter(String city,Float lat,Float lon) {

        weatherDataInterface.onFetchStart();

        Call<WeatherDataCallback> weatherDataCallbackCall = NetworkService.createService(RequestGet.class).getAllData(lat,lon,"minutely",Constants.appid);
        weatherDataCallbackCall.enqueue(new Callback<WeatherDataCallback>() {
            @Override
            public void onResponse(Call<WeatherDataCallback> call, Response<WeatherDataCallback> response) {
                if(response.isSuccessful())
                {
                    weatherDataInterface.onFetchComplete();


                    weatherDataInterface.gettingWeatherData(response.body());
                   current = Collections.singletonList(response.body().getCurrent());
                   dailyList=response.body().getDaily();
                   weatherDataInterface.getdailydata(response.body().getDaily(),response.body().getHourly());

                }
            }

            @Override
            public void onFailure(Call<WeatherDataCallback> call, Throwable t) {
                Log.e("Error", "onFailure: WeatherDataPresenter" );

                weatherDataInterface.onFetchFailure(t.getMessage());

            }
        });
    }

  // public void getWeatherDataPresenter(String cityname) {
  //     weatherDataInterface.onFetchStart();
  //     Call<WeatherDataBySearch>  weatherDataCallbackCall = NetworkService.createService(RequestGet.class).getDataBycity(cityname,Constants.appid);
  //     weatherDataCallbackCall.enqueue(new Callback<WeatherDataBySearch>() {
  //         @Override
  //         public void onResponse(Call<WeatherDataBySearch> call, Response<WeatherDataBySearch> response) {
  //             if(response.isSuccessful())
  //             {
  //                 weatherDataInterface.onFetchComplete();
  //                 weatherDataInterface.getDatabySeach(response.body());
  //             }
  //         }

  //         @Override
  //         public void onFailure(Call<WeatherDataBySearch> call, Throwable t) {

  //             weatherDataInterface.onFetchFailure(t.getMessage());
  //         }
  //     });
  // }
}
