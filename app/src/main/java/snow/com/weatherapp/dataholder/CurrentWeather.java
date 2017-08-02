package snow.com.weatherapp.dataholder;

import java.io.Serializable;
import snow.com.weatherapp.R;

/**
 * Created by Snow on 2016-05-16.
 */
public class CurrentWeather implements Serializable {
    private Temperature temperature;
    private Wind wind;

    private long time;
    private double pressure;
    private int clouds;
    private int weatherId;
    private String icon;
    private String main;
    private String description;

    public Temperature getTemp() {
        return temperature;
    }
    public void setTemp(Temperature temperature) {
        this.temperature = temperature;
    }
    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public int getClouds() {
        return clouds;
    }
    public void setClouds(int clouds) {
        this.clouds = clouds;
    }
    public int getWeatherId() {
        return weatherId;
    }
    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int currentImageSelector(String icon){
        int id;
        switch (icon) {
            case "01d":
                id = R.mipmap.ic_01d;
                break;
            case "01n":
                id = R.mipmap.ic_01n;
                break;
            case "02d":
                id = R.mipmap.ic_02d;
                break;
            case "02n":
                id = R.mipmap.ic_02n;
                break;
            case "03d":
                id = R.mipmap.ic_03d;
                break;
            case "03n":
                id = R.mipmap.ic_03n;
                break;
            case "04d":
                id = R.mipmap.ic_04d;
                break;
            case "04n":
                id = R.mipmap.ic_04n;
                break;
            case "09d":
                id = R.mipmap.ic_09d;
                break;
            case "09n":
                id = R.mipmap.ic_09n;
                break;
            case "10d":
                id = R.mipmap.ic_10d;
                break;
            case "10n":
                id = R.mipmap.ic_10n;
                break;
            case "11d":
                id = R.mipmap.ic_11d;
                break;
            case "11n":
                id = R.mipmap.ic_11n;
                break;
            case "13d":
                id = R.mipmap.ic_13d;
                break;
            case "13n":
                id = R.mipmap.ic_13n;
                break;
            case "50d":
                id = R.mipmap.ic_50d;
                break;
            case "50n":
                id = R.mipmap.ic_50n;
                break;
            default:
                id = R.mipmap.ic_dunno;
                break;
        }
        return id;
    }
}
