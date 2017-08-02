package snow.com.weatherapp.main;

import android.content.Context;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;

import snow.com.weatherapp.dataholder.Location;

/**
 * Created by Snow on 2016-05-16.
 */
public class WeatherDataManager {private String FILENAME_LAST_ID;
    private String FILENAME_LOCATION_ARR;
    private int lastId = 0;
    private ArrayList<Location> locationArr = new ArrayList<Location>();
    private Iterator<Location> iterator = locationArr.iterator();
    public FileIOManager fiom = new FileIOManager();

    public WeatherDataManager(String FILENAME_LAST_ID, String FILENAME_LOCATION_ARR){
        this.FILENAME_LAST_ID = FILENAME_LAST_ID;
        this.FILENAME_LOCATION_ARR = FILENAME_LOCATION_ARR;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public ArrayList<Location> getLocationArr() {
        return locationArr;
    }


    public void setLocationArr(ArrayList<Location> locationArr) {
        this.locationArr = locationArr;
    }



    public void loadSavedId(Context context) throws IOException {
        lastId = fiom.loadLastId(FILENAME_LAST_ID, context);
    }

    public Location loadSavedLocation(){
        if(!locationArr.isEmpty()){
            while(iterator.hasNext()){
                Location location = iterator.next();
                if(location.getCityId() == lastId){
                    return location;
                }
            }
        }
        return null;
    }

    public Location loadSavedLocation(int id){
        System.out.println("loadSavedLocation1");
        System.out.println("loadSavedLocation locationArr size:" + locationArr.size());
        if(!locationArr.isEmpty()){
            System.out.println("loadSavedLocation2");
            for (Location location : locationArr) {
                System.out.println("loadSavedLocation3");
                if(location.getCityId() == id){
                    System.out.println("loadSavedLocation4: loc found");
                    return location;
                }
            }
        }
        return null;
    }


//	public Location downloadData(String... params) {
//		Location location = new Location();
//		String dataCurrent = new HttpClient().getCurrentWeatherData(params[0]);
//		String dataForecast = new HttpClient().getWeatherForcastData(params[0]);
//
//		try {
//			location = JSONWeatherParser.getWeather(dataCurrent);
//			location.setForecastWeather(JSONWeatherParser.getForecast(dataForecast));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		try{
//			addLocationToArr(location);
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		return location;
//	}

    public Location downloadData(int id) {
        Location location = new Location();
        String dataCurrent = new HttpClient().getCurrentWeatherData(id);
        String dataForecast = new HttpClient().getWeatherForcastData(id);

        try {
            location = JSONWeatherParser.getWeather(dataCurrent);
            location.setForecastWeather(JSONWeatherParser.getForecast(dataForecast));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location;
    }

    public ArrayList<Location> fillLocationArray(){
        ArrayList<Location> tepmLocationArr = new ArrayList<Location>();
        while(iterator.hasNext()){
            Location location = downloadData(iterator.next().getCityId());
            tepmLocationArr.add(location);
        }
//		for(int i = 0; i < locationArr.size(); i++){			// tu chyba raczej while(iterator.hasNext())
//			Location location = downloadData(iterator.next().getCityId());
//			tepmLocationArr.add(location);
//		}
        return tepmLocationArr;
    }

    public void addLocationToArr (Location location){
        int id = location.getCityId();
        for (int i = 0; i < locationArr.size(); i++) {
            Location l = locationArr.get(i);
            if (l.getCityId() == id) {
                locationArr.set(i, location);
                return;
            }
        }
        locationArr.add(location);
    }

//	public boolean checkLastUpdate(Context context) throws IOException{
//		if (!locationArr.isEmpty()) {
//			if (iterator.next().getCurrentWeather().getTime() + 3600 < (System
//					.currentTimeMillis() + java.util.Calendar.ZONE_OFFSET) / 1000) {
//				locationArr = fillLocationArray();
//				fiom.saveLocationArr(FILENAME_LOCATION_ARR, locationArr,
//						context);
//				return true;
//			}
//		}
//		return false;
//	}

    //nowa
    public boolean checkLastUpdate(Location loc){
        long time = loc.getCurrentWeather().getTime();
        if (time > 0 ) {
            if (time + 7200 < (System
                    .currentTimeMillis() + java.util.Calendar.ZONE_OFFSET) / 1000) {
                return true;
            }
        }
        return false;
    }
    //test this
//	public boolean checkLastUpdate2(Location loc){
//		if (loc.getCurrentWeather().getTime() > 0 && loc.getCurrentWeather().getTime() + 7200 < (System
//				.currentTimeMillis() + java.util.Calendar.ZONE_OFFSET) / 1000) {
//			return true;
//		}
//		return false;
//	}


//sprobuj pobrac z location, powinna miec wartosc

//	public boolean checkLastUpdate(Context context, int id) throws IOException{
//		try {
//			loadSavedData(context);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		if (!locationArr.isEmpty()) {
//			for (Location loc : locationArr){
//				while (loc.getCityId() == id) {
//					if(loc.getCurrentWeather().getTime() + 7200 < (System
//							.currentTimeMillis() + java.util.Calendar.ZONE_OFFSET) / 1000){
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}

    public void saveDownloadData(Context context) throws IOException{
        fiom.saveLocationArr(FILENAME_LOCATION_ARR, locationArr, context);
    }

    public void loadSavedData(Context context) throws StreamCorruptedException, FileNotFoundException, ClassNotFoundException, IOException{
        locationArr = fiom.loadLocationArr(FILENAME_LOCATION_ARR, context);
    }
}
