package snow.com.weatherapp.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import snow.com.weatherapp.dataholder.City;
import snow.com.weatherapp.dataholder.CurrentWeather;
import snow.com.weatherapp.dataholder.ForecastTemperature;
import snow.com.weatherapp.dataholder.ForecastWeather;
import snow.com.weatherapp.dataholder.Location;
import snow.com.weatherapp.dataholder.Temperature;
import snow.com.weatherapp.dataholder.Wind;

/**
 * Created by Snow on 2016-05-16.
 */
public class JSONWeatherParser {public static Location getWeather(String data) throws JSONException {
    Location location = new Location();
    CurrentWeather currentWeather = new CurrentWeather();
    Temperature temperature = new Temperature();
    Wind wind = new Wind();

    try {
        JSONObject jObj = new JSONObject(data);
        location.setCityId(jObj.getInt("id"));
        location.setCityName(jObj.getString("name"));

        JSONObject coordObj = jObj.getJSONObject("coord");
        location.setLongitude(coordObj.getDouble("lon"));
        location.setLatitude(coordObj.getDouble("lat"));

        JSONObject sysObj = jObj.getJSONObject("sys");
        location.setCountry(sysObj.getString("country"));
        currentWeather.setTime(jObj.getLong("dt"));

        JSONObject mainObj = jObj.getJSONObject("main");
        temperature.setTemp(mainObj.getDouble("temp"));
        temperature.setMaxTemp(mainObj.getDouble("temp_max"));
        temperature.setMinTemp(mainObj.getDouble("temp_min"));
        currentWeather.setPressure(mainObj.getDouble("pressure"));

        JSONObject windObj = jObj.getJSONObject("wind");
        wind.setWindSpeed(windObj.getDouble("speed"));
        wind.setWindDeg(windObj.getDouble("deg"));

        JSONObject cloudsObj = jObj.getJSONObject("clouds");
        currentWeather.setClouds(cloudsObj.getInt("all"));


        JSONArray jArr = jObj.getJSONArray("weather");
        JSONObject weatherObj = jArr.getJSONObject(0);
        currentWeather.setWeatherId(weatherObj.getInt("id"));
        currentWeather.setIcon(weatherObj.getString("icon"));
        currentWeather.setMain(weatherObj.getString("main"));
        currentWeather.setDescription(weatherObj.getString("description"));

    } catch (Exception e) {
        e.printStackTrace();
    }

    currentWeather.setTemp(temperature);
    currentWeather.setWind(wind);
    location.setCurrentWeather(currentWeather);
    return location;
}

    public static ForecastWeather[] getForecast(String data) throws JSONException {

        ForecastWeather [] forecastWeather = new ForecastWeather[7];

        try {
            JSONObject jObj = new JSONObject(data);
            JSONArray listArr = jObj.getJSONArray("list");

            for (int i = 0; i < listArr.length(); i++){
                ForecastWeather tempFw = new ForecastWeather();
                ForecastTemperature forecastTemperature = new ForecastTemperature();

                JSONObject listObj = listArr.getJSONObject(i);
                tempFw.setTime(listObj.getLong("dt"));
                tempFw.setPressure(listObj.getDouble("pressure"));
                JSONObject tempObj = listObj.getJSONObject("temp");
                forecastTemperature.setDayTemp(tempObj.getDouble("max"));
                forecastTemperature.setNightTemp(tempObj.getDouble("min"));
                JSONArray weatherArr = listObj.getJSONArray("weather");
                JSONObject weatherObj = weatherArr.getJSONObject(0);
                tempFw.setWeatherId(weatherObj.getInt("id"));
                tempFw.setIcon(weatherObj.getString("icon"));
                tempFw.setMain(weatherObj.getString("main"));
                tempFw.setDescription(weatherObj.getString("description"));

                tempFw.setForecastTemperature(forecastTemperature);
                forecastWeather[i] = tempFw;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastWeather;
    }

    public static ArrayList<City> getCityList(String data) throws JSONException{
        ArrayList<City> cityArr = new ArrayList<>();

        try {
            JSONObject jObj = new JSONObject(data);
            JSONArray jArr = jObj.getJSONArray("list");

            for(int i = 0; i < jArr.length(); i++){
                JSONObject listObj = jArr.getJSONObject(i);

                int id = listObj.getInt("id");
                String name = listObj.getString("name");

                JSONObject sys = listObj.getJSONObject("sys");
                String country = sys.getString("country");
                cityArr.add(new City(id, name, country));
            }

        } catch (JSONException e){
            System.out.println("PARSER CITY EXP");//only for testing
            e.printStackTrace();
        }

        return cityArr;
    }
}
