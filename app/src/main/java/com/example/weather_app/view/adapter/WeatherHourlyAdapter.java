package com.example.weather_app.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_app.R;
import com.example.weather_app.common.Constants;
import com.example.weather_app.model.pojo.Hourly;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.ViewHolder> {
    Context cxn;
    List<Hourly> hourly;
    long time;
    Date date;
    SimpleDateFormat format;

    public WeatherHourlyAdapter(Context applicationContext, List<Hourly> hourlyList) {
        this.cxn = applicationContext;
        this.hourly = hourlyList;
    }

    @NonNull
    @Override
    public WeatherHourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cxn).inflate(R.layout.dailyrecyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyAdapter.ViewHolder holder, int position) {


         time  = hourly.get(position).getDt() * (long) 1000;
        date = new Date(time);
       format = new SimpleDateFormat("h a");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Log.d("date", format.format(date));


        holder.day.setText(String.valueOf(format.format(date)));
        String iconURL = Constants.iconBaseUrl + hourly.get(position).getWeather().get(0).getIcon() + "@2x.png";
        Picasso.with(cxn).load(iconURL).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return hourly.size();
    }

    public void updatedata(List<Hourly> hourlyList) {
        this.hourly = hourlyList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.namerv);
            icon = itemView.findViewById(R.id.iconrv);
        }
    }
}
