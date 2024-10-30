package org.meicode.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView t1, t2;
    Animation animate_btn, animate_txt; // Declare animate_txt here

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        animate_btn = AnimationUtils.loadAnimation(this, R.anim.anime_btn);
        btn.startAnimation(animate_btn); // Use startAnimation instead of setAnimation

        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView4);

        animate_txt = AnimationUtils.loadAnimation(this, R.anim.animate_texts); // Correctly instantiate animate_txt
        t1.startAnimation(animate_txt); // Use startAnimation for t1
        t2.startAnimation(animate_txt); // Use startAnimation for t2
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WeatherTodayActivity.class);
            startActivity(intent);
        });
    }
}
