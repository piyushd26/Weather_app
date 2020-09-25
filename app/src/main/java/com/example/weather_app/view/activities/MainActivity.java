package com.example.weather_app.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_app.ConvertKelvinCelsius;
import com.example.weather_app.R;
import com.example.weather_app.common.Constants;
import com.example.weather_app.model.Callback.WeatherDataCallback;
import com.example.weather_app.model.pojo.Daily;
import com.example.weather_app.model.pojo.Hourly;
import com.example.weather_app.model.pojo.WeatherDataBySearch;
import com.example.weather_app.presenter.WeatherDataPresenter;
import com.example.weather_app.view.adapter.WeatherHourlyAdapter;
import com.example.weather_app.view.adapter.WeatherdailyAdapter;
import com.example.weather_app.view.fragments.CitiesListFragment;
import com.example.weather_app.view.interfaces.WeatherDataInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity implements WeatherDataInterface {

    String CitynameFromFragment;
     Float latfromFragment;
     Float lonfromFragment;


    public static final String TAG = "MainActivity";
    private LineChart mChart;
    private int mFillColor = Color.argb(150, 51, 181, 229);
    public static final int My_Permission = 0;
    TextView textView_city, description, lat, lng, uvi, wind, pressure, humidity;
    TextView temp;
    GifImageView progressBar;
    ConvertKelvinCelsius convertKelvinCelsius = new ConvertKelvinCelsius();
    WeatherDataPresenter weatherDataPresenter;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addresses = new ArrayList<>();
    String myCurrentLocation;
    View view;
    Calendar c;
    Random random = new Random();
    List<Daily> dailyList = new ArrayList<>();
    RecyclerView dailyy_RV, houlyrv;
    WeatherdailyAdapter weatherdailyAdapter;
    LinearLayoutManager linearLayoutManager;
    WeatherHourlyAdapter weatherHourlyAdapter;
    CitiesListFragment citiesListFragment;
    ImageView currentIconAnimated;
    String iconurl;
    NestedScrollView nestedScrollView;
    List<Hourly> hourlyList = new ArrayList<>();
    Constants constants = new Constants();
    ConstraintLayout constraintLayout;
    FloatingActionButton fab;


    int intendedWidth;


    int vibrant;
    int vibrantLight;
    int vibrantDark;
    int muted;
    int mutedLight;
    int mutedDark;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        mChart = findViewById(R.id.linechartview);


        textView_city = findViewById(R.id.locationname);
        progressBar = findViewById(R.id.WD_pb);
        temp = findViewById(R.id.temp);
        view = findViewById(R.id.activitymain_layout);
        fab = findViewById(R.id.fab);
        constraintLayout = findViewById(R.id.constrainrslayouts);
        humidity = findViewById(R.id.humidity);
        houlyrv = findViewById(R.id.hourlyrv);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        dailyy_RV = findViewById(R.id.recyclerView_dailyy);
        currentIconAnimated = findViewById(R.id.current_icon);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        uvi = findViewById(R.id.uvi);
        description = findViewById(R.id.des);
        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Intent in=getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latfromFragment =bundle.getFloat("lat");
            lonfromFragment = bundle.getFloat("lon");
            CitynameFromFragment = bundle.getString("city");
            addresses=bundle.getParcelableArrayList("address");
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, My_Permission);
        } else {
            if (CitynameFromFragment ==null) {
                location();
            }else {
                locationfromFragment(latfromFragment,lonfromFragment,CitynameFromFragment );
            }
        }

    }

    private void locationfromFragment(Float latfromFragment, Float lonfromFragment, String citynameFromFragment) {
        weatherDataPresenter=new WeatherDataPresenter(this);
        weatherDataPresenter.getWeatherDataPresenter(citynameFromFragment, latfromFragment, lonfromFragment);
    }

    private void setData(int count, int range, List<Daily> dailies) {

        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Float f = convertKelvinCelsius.KtoC(dailies.get(i).getTemp().getMax());
            yVals.add(new Entry(i, f));
        }


        ArrayList<Entry> yVal1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            // float  val  =   (float) (Math.random()*range) + 50;
            Float f = convertKelvinCelsius.KtoC(dailies.get(i).getTemp().getMin());
            yVal1.add(new Entry(i, f));
        }


        LineDataSet set1, set2;
        set1 = new LineDataSet(yVals, "Data Set1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.BLUE);
        set1.setDrawCircles(false);
        set1.setLineWidth(3f);
        set1.setHighlightEnabled(false);
        set1.setFillAlpha(54);
        set1.setDrawCircles(true);
        set1.setCircleColorHole(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setDrawFilled(true);
        set1.setFillColor(Color.WHITE);

        set2 = new LineDataSet(yVal1, "Data Set2");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(Color.BLACK);
        set2.setDrawCircles(false);
        set2.setLineWidth(3f);
        set2.setHighlightEnabled(false);
        set2.setDrawCircles(true);
        set2.setCircleColor(Color.BLACK);
        set2.setCircleColorHole(Color.BLACK);
        set2.setFillAlpha(54);
        set2.setDrawFilled(true);
        set2.setFillColor(Color.WHITE);

        LineData data = new LineData(set2, set1);
        data.setDrawValues(true);
        mChart.setData(data);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case My_Permission: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location();
                } else {
                    Toast.makeText(MainActivity.this, "Location Denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }


    private void location() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //country = String.valueOf(Html.fromHtml("" + addresses.get(0).getCountryName()));
                        weatherDataPresenter = new WeatherDataPresenter(MainActivity.this);
                        myCurrentLocation = addresses.get(0).getLocality();
                        weatherDataPresenter.getWeatherDataPresenter(String.valueOf(Html.fromHtml("" + myCurrentLocation)), Float.valueOf(String.valueOf(location.getLatitude())), Float.valueOf(String.valueOf(location.getLongitude())));
                     //   String apikey="AIzaSyCeUrklEFeFYduc2F1fmaVgndfO2UYy1SU";
                     //     staticmapUrl =   "http://maps.google.com/maps/api/staticmap?center=" + addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude() + "&zoom=15&size=200x200&sensor=false&key="+apikey;
                       //   Picasso.with(getApplicationContext()).load(staticmapUrl).into( mapView);



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "null location", Toast.LENGTH_LONG).show();
                }
            }

        });
    }



    @Override
    public void onFetchStart() {
        progressBar.setVisibility(View.VISIBLE);


        progressBar.setImageResource(R.mipmap.preloader);

    }

    @Override
    public void onFetchFailure(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchComplete() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void gettingWeatherData(WeatherDataCallback weatherDataCallback) {


        SpannableStringBuilder builder_lat = new SpannableStringBuilder();
        SpannableStringBuilder builder_lon = new SpannableStringBuilder();
        SpannableStringBuilder builder_wind = new SpannableStringBuilder();
        SpannableStringBuilder builder_HUM = new SpannableStringBuilder();
        SpannableStringBuilder builder_Pressure = new SpannableStringBuilder();
        SpannableStringBuilder builder_UVI = new SpannableStringBuilder();
        String latitude = "Lat ";
        String lonitude = "Lon ";
        String HUM = "HUM ";
        String Wind = "Wind ";
        String Pressure = "Pressure ";
        String UVI = "UVi ";
        SpannableString greyspannable = new SpannableString(latitude);
        SpannableString greyLonspannable = new SpannableString(lonitude);
        SpannableString greyWindspannable = new SpannableString(Wind);
        SpannableString greyHUMLonspannable = new SpannableString(HUM);
        SpannableString greyPressurespannable = new SpannableString(Pressure);
        SpannableString greyUVILonspannable = new SpannableString(UVI);
        greyspannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, latitude.length(), 0);
        greyLonspannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, lonitude.length(), 0);
        greyWindspannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, Wind.length(), 0);
        greyHUMLonspannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, HUM.length(), 0);
        greyPressurespannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, Pressure.length(), 0);
        greyUVILonspannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, UVI.length(), 0);
        builder_lat.append(greyspannable);
        builder_lon.append(greyLonspannable);
        builder_wind.append(greyWindspannable);
        builder_HUM.append(greyHUMLonspannable);
        builder_Pressure.append(greyPressurespannable);
        builder_UVI.append(greyUVILonspannable);
        textView_city.setText(addresses.get(0).getLocality());
        temp.setText((String.format("%.0f", convertKelvinCelsius.KtoC(weatherDataCallback.getCurrent().getTemp()))) + "°");
        description.setText(weatherDataCallback.getCurrent().getWeather().get(0).getDescription().substring(0, 1).toUpperCase() + weatherDataCallback.getCurrent().getWeather().get(0).getDescription().substring(1).toLowerCase());
        lat.setText(builder_lat.append("\n").append(String.valueOf(weatherDataCallback.getLat())), TextView.BufferType.SPANNABLE);
        lng.setText(builder_lon.append("\n").append(String.valueOf(weatherDataCallback.getLon())), TextView.BufferType.SPANNABLE);
        humidity.setText(builder_HUM.append("\n").append(String.valueOf(weatherDataCallback.getCurrent().getHumidity() + "%")), TextView.BufferType.SPANNABLE);
        uvi.setText(builder_UVI.append("\n").append(String.valueOf(weatherDataCallback.getCurrent().getUvi())), TextView.BufferType.SPANNABLE);
        pressure.setText(builder_Pressure.append("\n").append(String.valueOf(weatherDataCallback.getCurrent().getPressure() + "hPa")), TextView.BufferType.SPANNABLE);
        wind.setText(builder_wind.append("\n").append(String.valueOf(weatherDataCallback.getCurrent().getWindSpeed() + "m/s")), TextView.BufferType.SPANNABLE);

    //    country_name.setText(addresses.get(0).getCountryName());
    //    c = Calendar.getInstance();
    //    String date = String.valueOf(c.get(Calendar.DATE));
    //    int month = c.get(Calendar.MONTH);
    //    String monthofyear = null;
    //    if(month==1){monthofyear="Jan";}
    //    else if(month==2) {monthofyear="Feb";}
    //    else if(month==3) {monthofyear="Mar";}
    //    else if(month==4) {monthofyear="April";}
    //    else if(month==5) {monthofyear="May";}
    //    else if(month==6) {monthofyear="June";}
    //    else if(month==7) {monthofyear="July";}
    //    else if(month==8) {monthofyear="Aug";}
    //    else if(month==9) {monthofyear="Sept";}
    //    else if(month==10) {monthofyear="Oct";}
    //    else if(month==11) {monthofyear="Nov";}
    //    else if(month==12) {monthofyear="Dec";}
//
    //    int year = c.get(Calendar.YEAR);
//
    //    date_toolbar.setText(monthofyear+" "+date+" "+year);



        //ICON
        String icon = weatherDataCallback.getCurrent().getWeather().get(0).getIcon();
        if (icon != null) {
            iconurl = Constants.iconBaseUrl + icon + "@2x.png";

            Picasso.with(this).load(iconurl).into(currentIconAnimated);
            //constants.getWeatherAnimatedIcons(weatherDataCallback.getCurrent().getWeather().get(0).getDescription(),weatherDataCallback.getCurrent().getWeather().get(0).getMain(),currentIconAnimated);
            //Glide.with(this).load(R.drawable.ic_clear_day).into(currentIconAnimated);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add New City", Toast.LENGTH_LONG).show();

                citiesListFragment= new CitiesListFragment();
                citiesListFragment.setData(nestedScrollView,fab);
                pushfragment(citiesListFragment);
                nestedScrollView.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);


            }
        });

        c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        int drawableid = Constants.view[random.nextInt(Constants.view.length)];
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(drawableid);
        }
        if (timeOfDay >= 0 && timeOfDay < 12) {

            view.setBackground(drawable);
            palette(drawableid);

            textView_city.setTextColor(vibrantDark);
            if (vibrantDark == 0) {
                textView_city.setTextColor(vibrant);
            } else if (vibrant == 0) {
                textView_city.setTextColor(vibrantLight);
            }


        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            view.setBackground(drawable);
            palette(drawableid);
            textView_city.setTextColor(vibrantDark);
            if (vibrantDark == 0) {
                textView_city.setTextColor(vibrant);
            } else if (vibrant == 0) {
                textView_city.setTextColor(vibrantLight);
            }

        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            view.setBackground(drawable);
            palette(drawableid);

            textView_city.setTextColor(vibrantDark);
            if (vibrantDark == 0) {
                textView_city.setTextColor(vibrant);
            } else if (vibrant == 0) {
                textView_city.setTextColor(vibrantLight);
            }
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            view.setBackground(drawable);
            palette(drawableid);
            textView_city.setTextColor(vibrantDark);
            if (vibrantDark == 0) {
                textView_city.setTextColor(vibrant);
            } else if (vibrant == 0) {
                textView_city.setTextColor(vibrantLight);
            }

        }


    }

    private void pushfragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activitymain_layout  ,fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void getdailydata(List<Daily> daily, List<Hourly> hourly) {

        dailyList = daily;

        if (daily != null) {
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
            dailyy_RV.setLayoutManager(linearLayoutManager);
            dailyy_RV.setHasFixedSize(true);
            weatherdailyAdapter = new WeatherdailyAdapter(getApplicationContext(), daily);
            dailyy_RV.setAdapter(weatherdailyAdapter);
            weatherdailyAdapter.updatedata(daily);
        }

        hourlyList = hourly;

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        houlyrv.setLayoutManager(linearLayoutManager);
        houlyrv.setHasFixedSize(true);
        weatherHourlyAdapter = new WeatherHourlyAdapter(getApplicationContext(), hourlyList);
        houlyrv.setAdapter(weatherHourlyAdapter);
        weatherHourlyAdapter.updatedata(hourlyList);


//LineCharts
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);

        mChart.setDrawBorders(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);

        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setEnabled(false);
        mChart.setDragEnabled(false);

        mChart.getXAxis().disableGridDashedLine();
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();

        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMaximum(50);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(false);

        setData(6, 6, daily);


    }

    @Override
    public void getDatabySeach(WeatherDataBySearch weatherDataBySearch) {

    }


    public void palette(int view) {
        Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                // access palette colors here
            }
        };
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), view);
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette.from(myBitmap).generate(paletteListener);
        }
        Palette palette = null;
        if (myBitmap != null) {
            palette = Palette.generate(myBitmap);
        }
        int defaul = 0x000000;
        vibrant = palette.getVibrantColor(defaul);
        vibrantLight = palette.getLightVibrantColor(defaul);
        vibrantDark = palette.getDarkVibrantColor(defaul);
        muted = palette.getMutedColor(defaul);
        mutedLight = palette.getLightMutedColor(defaul);
        mutedDark = palette.getDarkMutedColor(defaul);
    }


}
