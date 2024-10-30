package org.meicode.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherTodayActivity extends AppCompatActivity {

    private TextView temperature;
    private TextView description;
    private TextView city;
    private EditText editTextCity;
    private Button buttonFetchWeather;

    private WeatherApiService weatherApiService; // Your API service interface
    private String apiKey = "39df6b63aad4aeaa900f15a70ee59176"; // Replace with your actual API key

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_weather);

        // Initialize your TextViews and other UI elements
        temperature = findViewById(R.id.tvTemperature);
        description = findViewById(R.id.tvPrecipitation);
        city = findViewById(R.id.tvCityName);
        editTextCity = findViewById(R.id.editTextCity);
        buttonFetchWeather = findViewById(R.id.btnForecast);
        RecyclerView recyclerViewHourly = findViewById(R.id.recyclerViewHourly);
        recyclerViewHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Mock data for testing
        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
        hourlyWeatherList.add(new HourlyWeather("15:00", "20", R.drawable.rain));
        hourlyWeatherList.add(new HourlyWeather("16:00", "22", R.drawable.sun));
        hourlyWeatherList.add(new HourlyWeather("17:00", "21", R.drawable.rain));
        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeatherList);
        recyclerViewHourly.setAdapter(hourlyWeatherAdapter);

        // Initialize your API service (Assuming you have set up Retrofit)
        weatherApiService = ApiClient.getClient().create(WeatherApiService.class);

        // Set onClickListener for the button
        buttonFetchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = editTextCity.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    fetchWeather(cityName); // Fetch weather for the entered city
                } else {
                    Toast.makeText(WeatherTodayActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchWeather(String cityName) {
        Call<WeatherResponse> call = weatherApiService.getCurrentWeather(cityName, apiKey, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse.getMain() != null && weatherResponse.getWeather() != null && weatherResponse.getWeather().length > 0) {
                        String temperatureValue = String.valueOf(weatherResponse.getMain().getTemp());
                        String descriptionValue = weatherResponse.getWeather()[0].getDescription();
                        String cityValue = weatherResponse.getName();

                        // Update your UI here with the fetched data
                        temperature.setText(temperatureValue + "°C");
                        description.setText(descriptionValue);
                        city.setText(cityValue);
                    } else {
                        Toast.makeText(WeatherTodayActivity.this, "Invalid response structure", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("WeatherError", "Response code: " + response.code() + " Message: " + response.message());
                    Toast.makeText(WeatherTodayActivity.this, "Error fetching weather data: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("WeatherError", "Network error: " + t.getMessage());
                Toast.makeText(WeatherTodayActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
