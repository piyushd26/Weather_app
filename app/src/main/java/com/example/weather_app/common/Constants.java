package com.example.weather_app.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import com.example.weather_app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
    public static final String LOCATION = "";
    public static final String appid = "24f9aa4eec3eeabf9ddafdcbea078db3";
    public static final String exclude = "minutely";
    public static final String iconBaseUrl = "https://openweathermap.org/img/wn/";

    public static final int[] view = {
            R.mipmap.autum_view,
            R.mipmap.closetonature_view,
            R.mipmap.cloud_view,
            R.mipmap.forest_view,
            R.mipmap.house_view,
            R.mipmap.landscape_view,
            R.mipmap.minimalist_view,
            R.mipmap.mountainminimal_view,
            R.mipmap.moutainsblue_view,
            R.mipmap.outlook_view,
            R.mipmap.peace_view,
            R.mipmap.praise_view,
            R.mipmap.quiteplace_view,
            R.mipmap.red_view,
            R.mipmap.winter_view,
            R.mipmap.orangeview,
            R.mipmap.orangebeachview,
            R.mipmap.mountainsview,
            R.mipmap.deerview,
            R.mipmap.insomniacview,
            R.mipmap.beachview,

    };

    public static  String []  cities = {
            "Chandigarh",
            "Mumbai",
            "Delhi"

    };


    public static final int[] font = {
            R.font.rusticoegular,
            R.font.montserrat_extrabold,
            R.font.lemonmilk_bold
    };
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "city";

    public static ArrayList<String> strings =new ArrayList<>();

    public static void saveData(Context context, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        strings.add(value);
        Set<String> set = new HashSet<String>(strings);
        editor.putStringSet("city", set);
        editor.apply();
    }

    public static Set<String> loadData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Set<String> set = new HashSet<String>(strings);
        Set<String> text = sharedPreferences.getStringSet(key, set);
        return text;
    }


    public void getWeatherAnimatedIcons(String weatherDescription, String weatherMain, ImageView imageView) {


        if (weatherMain.equals("Rain") && weatherDescription.equals("light rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_4);

        } else if (weatherMain.equals("Rain") && weatherDescription.equals("moderate rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_5);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("heavy intensity rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_6);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("very heavy rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_7);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("extreme rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_7);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("freezing rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_6);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("light intensity shower rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_5);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("shower rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_5);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("heavy intensity shower rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_7);
        } else if (weatherMain.equals("Rain") && weatherDescription.equals("ragged shower rain")) {
            imageView.setBackgroundResource(R.drawable.ic_rainy_4);
        } else if (weatherMain.equals("Clouds") && weatherDescription.equals("few clouds")) {
            imageView.setBackgroundResource(R.drawable.ic_cloudy_day_1);
        } else if (weatherMain.equals("Clouds") && weatherDescription.equals("scattered clouds")) {
            imageView.setBackgroundResource(R.drawable.ic_cloudy_day_2);
        } else if (weatherMain.equals("Clouds") && weatherDescription.equals("broken clouds")) {
            imageView.setBackgroundResource(R.drawable.ic_cloudy);
        } else if (weatherMain.equals("Clouds") && weatherDescription.equals("overcast clouds")) {
            imageView.setBackgroundResource(R.drawable.ic_cloudy);
        } else if (weatherMain.equals("thunder")) {

            imageView.setBackgroundResource(R.drawable.ic_thunder);

        } else if (weatherMain.equals("Snow") && weatherDescription.equals("light snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_1);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_5);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Heavy snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_6);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Sleet")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_3);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("light shower sleet")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_2);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Shower sleet")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_2);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("light rain and snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_3);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Rain and snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_3);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("light shower snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_3);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Shower snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_3);
        } else if (weatherMain.equals("Snow") && weatherDescription.equals("Heavy shower snow")) {
            imageView.setBackgroundResource(R.drawable.ic_snowy_6);
        } else if (weatherMain.equals("Clear")) {
            imageView.setBackgroundResource(R.drawable.ic_day);

        } else if (weatherMain.equals("Night")) {

            imageView.setBackgroundResource(R.drawable.ic_night);
        }


    }


}