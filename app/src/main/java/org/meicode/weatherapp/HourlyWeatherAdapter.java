package org.meicode.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder> {
    private List<HourlyWeather> hourlyWeatherList;

    public HourlyWeatherAdapter(List<HourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_weather, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyWeather weather = hourlyWeatherList.get(position);
        holder.temperature.setText(weather.getTemperature() + "°C");
        holder.time.setText(weather.getTime());
        holder.icon.setImageResource(weather.getIconResId()); // Replace with actual icon loading
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherList.size();
    }

    public static class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView temperature, time;
        ImageView icon;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.tvHourlyTemperature);
            time = itemView.findViewById(R.id.tvHourlyTime);
            icon = itemView.findViewById(R.id.imgHourlyIcon);
        }
    }
}
