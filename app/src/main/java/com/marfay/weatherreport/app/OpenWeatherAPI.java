package com.marfay.weatherreport.app;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mark on 4/19/14.
 */
public class OpenWeatherAPI {
  /**
   * Open Weather Map API Endpoint
   */
  public static final String URL = "http://api.openweathermap.org/data/2.1/forecast/city?units=imperial&q=";

  /**
   * Object containing qualitative description of weather as well as temperature in Fahrenheit.
   */
  public static class WeatherData {

    public final String description;
    public final int temperature;

    public WeatherData(String description, int temperature) {
      this.description = description;
      this.temperature = temperature;
    }
  }

  /**
   * Used by {@link #getWeatherData(String, com.android.volley.RequestQueue, com.marfay.weatherreport.app.OpenWeatherAPI.Callback)}
   * to return an initialized {@link com.marfay.weatherreport.app.OpenWeatherAPI.WeatherData} object, due to async
   * nature of making requests
   */
  public interface Callback {
    void onWeatherData(WeatherData weatherData);
  }

  /**
   * Makes a {@link com.android.volley.toolbox.JsonObjectRequest} with given city name and executes callback.
   *
   * @param cityName The name of the city that was passed from the voice prompt
   * @param queue    A {@link com.android.volley.RequestQueue}
   * @param callback An anonymous class to handle response containing
   *                 {@link com.marfay.weatherreport.app.OpenWeatherAPI.WeatherData}
   */
  public static void getWeatherData(String cityName, RequestQueue queue, final Callback callback) {
    queue.add(new JsonObjectRequest(URL + cityName, null, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
          JSONObject today = (JSONObject) response.getJSONArray("list").get(0);
          String description = today.getJSONObject("weather").getString("description");
          int temperature = today.getJSONObject("main").getInt("temp");

          callback.onWeatherData(new WeatherData(description, temperature));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("onErrorResponse", error.getMessage());
      }
    }));
  }
}
