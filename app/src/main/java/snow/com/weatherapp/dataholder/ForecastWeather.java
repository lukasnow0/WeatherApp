package snow.com.weatherapp.dataholder;

import java.io.Serializable;
import snow.com.weatherapp.R;

/**
 * Created by Snow on 2016-05-16.
 */
public class ForecastWeather extends CurrentWeather implements Serializable{
    private ForecastTemperature forecastTemperature;

    public ForecastTemperature getForecastTemperature() {
        return forecastTemperature;
    }

    public void setForecastTemperature(ForecastTemperature forecastTemperature) {
        this.forecastTemperature = forecastTemperature;
    }

    public int currentImageSelector(String icon){
        int id;
        switch (icon) {
            case "01d":
            case "01n":
                id = R.mipmap.ic_01d_small;
                break;
            case "02d":
            case "02n":
                id = R.mipmap.ic_02d_small;
                break;
            case "03d":
            case "03n":
                id = R.mipmap.ic_03d_small;
                break;
            case "04d":
            case "04n":
                id = R.mipmap.ic_04d_small;
                break;
            case "09d":
            case "09n":
                id = R.mipmap.ic_09d_small;
                break;
            case "10d":
            case "10n":
                id = R.mipmap.ic_10d_small;
                break;
            case "11d":
            case "11n":
                id = R.mipmap.ic_11d_small;
                break;
            case "13d":
            case "13n":
                id = R.mipmap.ic_13d_small;
                break;
            case "50d":
            case "50n":
                id = R.mipmap.ic_50d_small;
                break;
            default:
                id = R.mipmap.ic_dunno_small;
                break;
        }
        return id;
    }
}
