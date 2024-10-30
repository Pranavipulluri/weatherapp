package org.meicode.weatherapp;

public class HourlyForecast {
    private long time; // Unix timestamp
    private double temperature;
    private int iconResId; // Resource ID for weather icon

    public HourlyForecast(long time, double temperature, int iconResId) {
        this.time = time;
        this.temperature = temperature;
        this.iconResId = iconResId;
    }

    public long getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getIconResId() {
        return iconResId;
    }
}
