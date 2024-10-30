package org.meicode.weatherapp;

public class HourlyWeather {
    private String time;
    private String temperature;
    private int iconResId;

    public HourlyWeather(String time, String temperature, int iconResId) {
        this.time = time;
        this.temperature = temperature;
        this.iconResId = iconResId;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getIconResId() {
        return iconResId;
    }
}
