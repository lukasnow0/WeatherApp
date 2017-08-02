package snow.com.weatherapp.dataholder;

import java.io.Serializable;

/**
 * Created by Snow on 2016-05-16.
 */
public class Temperature implements Serializable{
    private double temp;
    private double maxTemp;
    private double minTemp;


    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
}
