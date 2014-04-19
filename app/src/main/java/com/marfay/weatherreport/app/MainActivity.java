package com.marfay.weatherreport.app;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.glass.app.Card;


public class MainActivity extends Activity {

  private RequestQueue mQueue;
  private Card mCard;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mQueue = Volley.newRequestQueue(this);
    mCard = new Card(this);

    String cityName = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS).get(0);
    if (cityName == null) {
      mCard.setText("Could not parse city name, please try again!");
      setContentView(mCard.getView());
      return;
    }

    OpenWeatherAPI.getWeatherData(cityName, mQueue, new OpenWeatherAPI.Callback() {
      @Override
      public void onWeatherData(OpenWeatherAPI.WeatherData weatherData) {
        mCard
            .setText(weatherData.description)
            .setFootnote(weatherData.temperature + "° Fahrenheit");

        setContentView(mCard.getView());
      }
    });
  }
}
