package org.meicode.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
