package org.meicode.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FigmaActivity extends AppCompatActivity {
    private TextView maxTempText, minTempText, currentTempText, dateText, precipitationText;
    private LinearLayout hourlyForecastLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.figma);

        // Initialize views
        maxTempText = findViewById(R.id.maxTempText);
        minTempText = findViewById(R.id.minTempText);
        currentTempText = findViewById(R.id.currentTempText);
        dateText = findViewById(R.id.dateText);
        precipitationText = findViewById(R.id.precipitationText);
        hourlyForecastLayout = findViewById(R.id.hourlyForecastLayout);

        // Fetch weather data
        fetchWeatherData(17.3850, 78.4867); // Hyderabad coordinates
    }

    private void fetchWeatherData(double latitude, double longitude) {
        String apiKey = "95007de64e997831c2fad3fdeb28af05";
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=minutely,alerts&units=metric&appid=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse daily forecast data
                            JSONArray daily = response.getJSONArray("daily");
                            JSONObject today = daily.getJSONObject(0);

                            double maxTemp = today.getJSONObject("temp").getDouble("max");
                            double minTemp = today.getJSONObject("temp").getDouble("min");
                            double currentTemp = today.getJSONObject("temp").getDouble("day");
                            long dateUnix = today.getLong("dt") * 1000L; // Convert to milliseconds
                            double precipitation = today.getDouble("pop") * 100;

                            // Format and display data
                            maxTempText.setText("Max: " + maxTemp + "°");
                            minTempText.setText("Min: " + minTemp + "°");
                            currentTempText.setText(currentTemp + "°");
                            dateText.setText(formatDate(dateUnix));
                            precipitationText.setText("Precipitation: " + precipitation + "%");

                            // Display hourly forecast
                            JSONArray hourly = response.getJSONArray("hourly");
                            displayHourlyForecast(hourly);

                        } catch (JSONException e) {
                            Log.e("JSONError", "Failed to parse weather data", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("WeatherApp", "Error fetching weather data", error);
                    }
                });

        queue.add(request);
    }

    private void displayHourlyForecast(JSONArray hourly) {
        hourlyForecastLayout.removeAllViews();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        for (int i = 0; i < Math.min(hourly.length(), 7); i++) {
            try {
                JSONObject hourData = hourly.getJSONObject(i);
                long timeUnix = hourData.getLong("dt") * 1000L; // Convert to milliseconds
                double temp = hourData.getDouble("temp");

                // Create layout for each hour
                LinearLayout hourItem = new LinearLayout(FigmaActivity.this);
                hourItem.setOrientation(LinearLayout.VERTICAL);

                TextView hourText = new TextView(FigmaActivity.this);
                hourText.setText(hourFormat.format(new Date(timeUnix)));
                hourText.setTextColor(Color.WHITE);

                TextView tempText = new TextView(FigmaActivity.this);
                tempText.setText(temp + "°C");
                tempText.setTextColor(Color.CYAN);

                hourItem.addView(hourText);
                hourItem.addView(tempText);
                hourlyForecastLayout.addView(hourItem);

            } catch (JSONException e) {
                Log.e("JSONError", "Error parsing hourly data", e);
            }
        }
    }

    private String formatDate(long dateUnix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return dateFormat.format(new Date(dateUnix));
    }
}
