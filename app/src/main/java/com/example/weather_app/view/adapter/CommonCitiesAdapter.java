package com.example.weather_app.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_app.R;
import com.example.weather_app.common.Constants;
import com.example.weather_app.presenter.WeatherDataPresenter;
import com.example.weather_app.view.activities.MainActivity;
import com.example.weather_app.view.interfaces.WeatherDataInterface;

import java.util.ArrayList;
import java.util.Random;


public class CommonCitiesAdapter extends RecyclerView.Adapter<CommonCitiesAdapter.ViewHolder> {
    ArrayList<String> set_SF = new ArrayList<>();
    WeatherDataInterface interfac;
    Context cnx;

    Random random = new Random();

    String cityname;

    WeatherDataPresenter weatherDataPresenter;

    Float lat;

    Float log;


    public CommonCitiesAdapter(Context context, Float lat, Float lon, WeatherDataInterface weatherDataInterface, ArrayList<String> city_SF) {
        this.lat = lat;
        this.log = lon;
        this.interfac = weatherDataInterface;
        this.cnx = context;
        this.set_SF = city_SF;
    }

    @NonNull
    @Override
    public CommonCitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cnx).inflate(R.layout.comoncities_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommonCitiesAdapter.ViewHolder holder, int position) {

        holder.name.setText(set_SF.get(position));
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
          holder.background.setCardBackgroundColor(color);



        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataPresenter = new WeatherDataPresenter(interfac);
                weatherDataPresenter.getWeatherDataPresenter(cityname, lat, log);
                Toast.makeText(cnx, "cllll", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return set_SF.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_cities);
            background = itemView.findViewById(R.id.cons_layout_item);

        }
    }
}
