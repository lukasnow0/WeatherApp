package snow.com.weatherapp.dataholder;

import java.io.Serializable;

/**
 * Created by Snow on 2016-05-16.
 */
public class ForecastTemperature extends Temperature implements Serializable{
    private double dayTemp;
    private double nightTemp;

    public double getDayTemp() {
        return dayTemp;
    }
    public void setDayTemp(double dayTemp) {
        this.dayTemp = dayTemp;
    }
    public double getNightTemp() {
        return nightTemp;
    }
    public void setNightTemp(double nightTemp) {
        this.nightTemp = nightTemp;
    }
}
