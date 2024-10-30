package org.meicode.weatherapp;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private Weather[] weather;
    @SerializedName("name")
    private String name;

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        @SerializedName("temp")
        private float temp;
        @SerializedName("feels_like")
        private float feelsLike;

        public float getTemp() {
            return temp;
        }

        public float getFeelsLike() {
            return feelsLike;
        }
    }

    public static class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
