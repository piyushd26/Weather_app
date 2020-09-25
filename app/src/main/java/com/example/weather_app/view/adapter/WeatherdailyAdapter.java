package com.example.weather_app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_app.R;
import com.example.weather_app.common.Constants;
import com.example.weather_app.model.pojo.Daily;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeatherdailyAdapter extends RecyclerView.Adapter<WeatherdailyAdapter.Viewholder> {


    List<Daily> dailydata = new ArrayList<>();

    Context cnx;

    int counts = 0;

    Calendar c;

    Constants constant = new Constants();

    String[] days;

    public WeatherdailyAdapter(Context context, List<Daily> dailyList) {

        this.cnx = context;
        this.dailydata = dailyList;


    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cnx).inflate(R.layout.dailyrecyclerview_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {


        if (position == 0) {
            c = Calendar.getInstance();
            int dayofweek = c.get(Calendar.DAY_OF_WEEK);

            if (dayofweek == 1)//Sunday
            {
                days = new String[]{"SUN", "SAT", "FRI", "THU", "WED", "TUE", "MON", "NOW"};

            } else if (dayofweek == 2) {//Monday
                days = new String[]{"MON", "SUN", "SAT", "FRI", "THU", "WED", "TUE", "NOW"};
            } else if (dayofweek == 3)//Tuesday
            {
                days = new String[]{"TUE", "MON", "SUN", "SAT", "FRI", "THU", "WED", "NOW"};
            } else if (dayofweek == 4) {//Wednesday
                days = new String[]{"WED", "TUE", "MON", "SUN", "SAT", "FRI", "THU", "NOW"};
            } else if (dayofweek == 5) {//Thursday
                days = new String[]{"THU", "WED", "TUE", "MON", "SUN", "SAT", "FRI", "NOW"};
            } else if (dayofweek == 6)//Friday
            {
                days = new String[]{"FRI", "THU", "WED", "TUE", "MON", "SUN", "SAT", "NOW"};
            } else if (dayofweek == 7)//Saturday
            {
                days = new String[]{"SAT", "FRI", "THU", "WED", "TUE", "MON", "SUN", "NOW"};
            }
        }


        holder.name.setText(days[position]);

        String iconURL = Constants.iconBaseUrl + dailydata.get(position).getWeather().get(0).getIcon() + "@2x.png";

        Picasso.with(cnx).load(iconURL).into(holder.icon);
        // Picasso.with(cnx).load((constant.getWeatherAnimatedIcons(dailydata.get(position).getWeather().get(0).getDescription(),dailydata.get(position).getWeather().get(0).getMain(),holder.icon))).into(holder.icon);

        String description = dailydata.get(position).getWeather().get(0).getDescription();
        String main = dailydata.get(position).getWeather().get(0).getMain();
        //constant.getWeatherAnimatedIcons(description,main,holder.icon);

    }

    @Override
    public int getItemCount() {
        return dailydata.size();
    }

    public void updatedata(List<Daily> daily) {

        dailydata = daily;
        notifyDataSetChanged();

    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namerv);
            icon = itemView.findViewById(R.id.iconrv);
        }
    }
}
