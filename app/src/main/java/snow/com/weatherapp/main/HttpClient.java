package snow.com.weatherapp.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Snow on 2016-05-16.
 */
public class HttpClient {
    //	private static String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String CURRENT_URL_BY_ID = "http://api.openweathermap.org/data/2.5/weather?id=";
    //	private static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static String FORECAST_URL_BY_ID = "http://api.openweathermap.org/data/2.5/forecast/daily?id=";
    private static String SEARCH_CITY_URL = "http://api.openweathermap.org/data/2.5/find?q=";
    private static String APPID_KEY = "&APPID=68199cdf1e42ae0ca0ec5e87009d671e";

    public String getCurrentWeatherData(int id) {

        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            connection = (HttpURLConnection) (new URL(CURRENT_URL_BY_ID + id + APPID_KEY)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setDoOutput(true);
            connection.connect();

            StringBuffer sBuf = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String data = null;
            while((data = br.readLine()) != null) {
                sBuf.append(data).append("\r\n");
            }

            is.close();
            connection.disconnect();
            System.out.println("Pobrano dane current: " + sBuf.toString());//For testing only
            return sBuf.toString();
        }

        catch(Throwable t) {
            t.printStackTrace();
        }

        finally {
            try {
                is.close();
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            try {
                connection.disconnect();
            }
            catch (Throwable t ){
                t.printStackTrace();
            }

        }

        return null;
    }

    public String getWeatherForcastData (int id){
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            connection = (HttpURLConnection) (new URL(FORECAST_URL_BY_ID + id + APPID_KEY)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setDoOutput(true);
            connection.connect();

            StringBuffer sBuf = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String data = null;
            while((data = br.readLine()) != null) {
                sBuf.append(data).append("\r\n");
            }

            is.close();
            connection.disconnect();
            System.out.println("Pobrano dane forecast: " + sBuf.toString());//For testing only
            return sBuf.toString();
        }

        catch(Throwable t) {
            t.printStackTrace();
        }

        finally {
            try {
                is.close();
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            try {
                connection.disconnect();
            }
            catch (Throwable t ){
                t.printStackTrace();
            }

        }

        return null;
    }

//	public String getCurrentWeatherData(String location) {
//
//		HttpURLConnection connection = null;
//		InputStream is = null;
//
//		try {
//			connection = (HttpURLConnection) (new URL(CURRENT_URL + location + APPID_KEY)).openConnection();
//			connection.setRequestMethod("GET");
//			connection.setDoInput(true);
//			connection.setDoOutput(true);
//			connection.connect();
//
//			StringBuffer sBuf = new StringBuffer();
//			is = connection.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			String data = null;
//			while((data = br.readLine()) != null) {
//				sBuf.append(data + "\r\n");
//			}
//
//			is.close();
//			connection.disconnect();
//			System.out.println("Pobrano dane current: " + sBuf.toString());//For testing only
//			return sBuf.toString();
//		}
//
//		catch(Throwable t) {
//			t.printStackTrace();
//		}
//
//		finally {
//			try {
//				is.close();
//			}
//			catch (Throwable t){
//				t.printStackTrace();
//			}
//			try {
//				connection.disconnect();
//			}
//			catch (Throwable t ){
//				t.printStackTrace();
//			}
//
//		}
//
//		return null;
//	}

//	public String getWeatherForcastData (String location){
//		HttpURLConnection connection = null;
//		InputStream is = null;
//
//		try {
//			connection = (HttpURLConnection) (new URL(FORECAST_URL + location + APPID_KEY)).openConnection();
//			connection.setRequestMethod("GET");
//			connection.setDoInput(true);
//			connection.setDoOutput(true);
//			connection.connect();
//
//			StringBuffer sBuf = new StringBuffer();
//			is = connection.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			String data = null;
//			while((data = br.readLine()) != null) {
//				sBuf.append(data + "\r\n");
//			}
//
//			is.close();
//			connection.disconnect();
//			System.out.println("Pobrano dane forecast: " + sBuf.toString());//For testing only
//			return sBuf.toString();
//		}
//
//		catch(Throwable t) {
//			t.printStackTrace();
//		}
//
//		finally {
//			try {
//				is.close();
//			}
//			catch (Throwable t){
//				t.printStackTrace();
//			}
//			try {
//				connection.disconnect();
//			}
//			catch (Throwable t ){
//				t.printStackTrace();
//			}
//
//		}
//
//		return null;
//	}

    public String searchCity (String location){
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            connection = (HttpURLConnection) (new URL(SEARCH_CITY_URL + location + APPID_KEY)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setDoOutput(true);
            connection.connect();

            StringBuffer sBuf = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String data = null;
            while((data = br.readLine()) != null) {
                sBuf.append(data).append("\r\n");
            }

            is.close();
            connection.disconnect();
            System.out.println("Pobrano dane searchcity: " + sBuf.toString());//For testing only
            return sBuf.toString();
        }

        catch(Throwable t) {
            t.printStackTrace();
        }

        finally {
            try {
                is.close();
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            try {
                connection.disconnect();
            }
            catch (Throwable t ){
                t.printStackTrace();
            }

        }

        return null;
    }
}
