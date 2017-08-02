package snow.com.weatherapp.dataholder;

import java.io.Serializable;

/**
 * Created by Snow on 2016-05-16.
 */
public class Wind implements Serializable {
    private double windSpeed;
    private double windDeg;

    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public double getWindDeg() {
        return windDeg;
    }
    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }
}
