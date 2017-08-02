package snow.com.weatherapp.dataholder;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by Snow on 2016-05-16.
 */
public class Location implements Serializable {
    private CurrentWeather currentWeather;
    private ForecastWeather [] forecastWeather;

    private int cityId;
    private String cityName;
    private double longitude;
    private double latitude;
    private String country;
    private String[] data;
    private int[] icons;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
    public ForecastWeather[] getForecastWeather() {
        return forecastWeather;
    }
    public void setForecastWeather(ForecastWeather[] forecastWeather) {
        this.forecastWeather = forecastWeather;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int[] getIcons() {
        return icons;
    }
    public void setIcons(int[] icons) {
        this.icons = icons;
    }
    public String[] getData() {
        return data;
    }
    public void setData(String[] data) {
        this.data = data;
    }
    @SuppressLint("SimpleDateFormat") public void parseDataFields(){
        if(forecastWeather.length != 0){
            String d[] = new String[20];
            ForecastTemperature forecastTemperature = new ForecastTemperature();
            long time = (currentWeather.getTime() + java.util.Calendar.ZONE_OFFSET)*1000;
            d[0] = getCityName() + ", " + getCountry();
            d[1] = String.valueOf(new SimpleDateFormat("E, dd MMM, HH:mm").format(new java.util.Date(time)));
            d[2] = String.valueOf(Math.round(currentWeather.getTemp().getTemp() - 273.15)) + "\u00b0" + "C";
            d[3] = String.valueOf(Math.round(forecastWeather[0].getForecastTemperature().getDayTemp()- 273.15))  + "\u00b0" + "C" + "/"
                    + String.valueOf(Math.round(forecastWeather[0].getForecastTemperature().getNightTemp() - 273.15)) + "\u00b0" + "C";
            d[4] = currentWeather.getDescription();
            d[5] = String.valueOf(currentWeather.getWind().getWindSpeed()) + " m/s";
            d[6] = String.valueOf(currentWeather.getWind().getWindDeg());
            d[7] = String.valueOf(currentWeather.getPressure()) + " hPa";

            for (int i = 0; i < forecastWeather.length - 1; i++){
                time = (forecastWeather[i + 1].getTime() + java.util.Calendar.ZONE_OFFSET)*1000;
                d[8 + i] = String.valueOf(new SimpleDateFormat("E, dd-MM").format(new java.util.Date(time)));
            }
            for (int i = 0; i < forecastWeather.length - 1; i++){
                forecastTemperature = forecastWeather[i + 1].getForecastTemperature();
                d[14 + i] = String.valueOf(Math.round(forecastTemperature.getDayTemp()- 273.15))  + "\u00b0" + "C" + "/"
                        + String.valueOf(Math.round(forecastTemperature.getNightTemp() - 273.15)) + "\u00b0" + "C";
            }
            data = d;
        }
    }
    public void parseIcons(){
        if(forecastWeather.length != 0){
            int ic[] = new int[7];
            ic[0] = currentWeather.currentImageSelector(currentWeather.getIcon());
            for (int i = 1; i < forecastWeather.length; i++){
                ic[i] = forecastWeather[i].currentImageSelector(forecastWeather[i].getIcon());
            }
            icons = ic;
        }
    }
}
