package com.marfay.weatherreport.app;

import android.test.AndroidTestCase;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OpenWeatherAPITest extends AndroidTestCase {

  private RequestQueue mQueue;

  class MockCallback implements OpenWeatherAPI.Callback {
    public OpenWeatherAPI.WeatherData weatherData;

    @Override
    public void onWeatherData(OpenWeatherAPI.WeatherData weatherData) {
      this.weatherData = weatherData;

      synchronized (this) {
        notifyAll();
      }
    }
  }

  public void setUp() throws Exception {
    super.setUp();

    mQueue = Volley.newRequestQueue(getContext());
  }

  public void testGetWeatherData() throws Exception {
    MockCallback callback = new MockCallback();

    OpenWeatherAPI.getWeatherData("new york", mQueue, callback);

    synchronized (callback) {
      callback.wait(10000);
    }

    assertThat("WeatherData description", callback.weatherData.description, not(isEmptyOrNullString()));
    assertThat("WeatherData temperature", callback.weatherData.temperature, instanceOf(Integer.class));
    assertThat("WeatherData temperature", callback.weatherData.temperature, notNullValue());
  }
}