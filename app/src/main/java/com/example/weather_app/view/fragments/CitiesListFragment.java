package com.example.weather_app.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weather_app.R;
import com.example.weather_app.common.Constants;
import com.example.weather_app.model.Callback.WeatherDataCallback;
import com.example.weather_app.model.pojo.Daily;
import com.example.weather_app.model.pojo.Hourly;
import com.example.weather_app.model.pojo.WeatherDataBySearch;
import com.example.weather_app.presenter.WeatherDataPresenter;
import com.example.weather_app.view.activities.MainActivity;
import com.example.weather_app.view.adapter.CommonCitiesAdapter;
import com.example.weather_app.view.interfaces.WeatherDataInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class CitiesListFragment extends Fragment implements WeatherDataInterface {

    View view;
    CommonCitiesAdapter commonCitiesAdapter;
    RecyclerView recyclerView;
    Random random = new Random();
    LinearLayoutManager linearLayoutManager;
    WeatherDataPresenter weatherDataPresenter;
    androidx.appcompat.widget.SearchView searchView;
    Context context;
    ArrayAdapter<String> adapter;
    MainActivity mainActivity;
    ListView listView;
    Float lat;
    Float lon;
    Geocoder gc;
    List<Address> addresses;


     NestedScrollView nsv;
     FloatingActionButton fab_;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cities_list, container, false);


        recyclerView = view.findViewById(R.id.recyclerView_common);

        searchView = view.findViewById(R.id.searchview_);
        listView = view.findViewById(R.id.listview);
        weatherDataPresenter = new WeatherDataPresenter(this);
        gc = new Geocoder(getContext());


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                try {
                    addresses = gc.getFromLocationName(query, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(addresses!=null) {
                    lat = Float.valueOf(String.valueOf(addresses.get(0).getLatitude()));
                    lon = Float.valueOf(String.valueOf(addresses.get(0).getLongitude()));

                    mainActivity = new MainActivity();

                    Bundle bundle = new Bundle();
                    bundle.putFloat("lat", lat);
                    bundle.putFloat("lon", lon);
                    bundle.putString("city", String.valueOf(query));
                    bundle.putParcelableArrayList("address", (ArrayList<? extends Parcelable>) addresses);

                    Constants.saveData(getContext(), query);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {

                    Toast.makeText(getContext(),"Not found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {


                return false;
            }
        });

        adapter_common(getContext());


        return view;

    }

    private void adapter_common(Context context) {

        Set<String> city_SF = Constants.loadData(context,"city");
        ArrayList<String> strings=new ArrayList<>(city_SF);


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        commonCitiesAdapter = new CommonCitiesAdapter(getContext(), lat, lon, this,  strings);
        recyclerView.setAdapter(commonCitiesAdapter);

    }


    @Override
    public void gettingWeatherData(WeatherDataCallback weatherDataCallback) {

    }

    @Override
    public void getdailydata(List<Daily> daily, List<Hourly> hourly) {

    }

    @Override
    public void getDatabySeach(WeatherDataBySearch weatherDataBySearch) {

    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchFailure(String message) {
        Toast.makeText(getContext(),"Failed Request "+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchComplete() {

    }

    public void setData(NestedScrollView nestedScrollView, FloatingActionButton fab) {
        this.nsv=nestedScrollView;
        this.fab_=fab;
    }
}
