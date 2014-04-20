package com.marfay.weatherreport.app;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.glass.app.Card;


public class MainActivity extends Activity {

  private TextToSpeech mTTS;
  private RequestQueue mQueue;
  private Card mCard;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
      }
    });
    mQueue = Volley.newRequestQueue(this);
    mCard = new Card(this);

    final String cityName = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS).get(0);
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
        mTTS.speak(String.format("%s in %s at %d degrees Fahrenheit", weatherData.description, cityName, weatherData.temperature), TextToSpeech.QUEUE_ADD, null);
      }
    });
  }
}
