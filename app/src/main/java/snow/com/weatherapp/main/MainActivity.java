package snow.com.weatherapp.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import snow.com.weatherapp.R;
import snow.com.weatherapp.dataholder.Location;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME_LAST_ID = "last_id";
    private static final String FILENAME_LOCATION_ARR = "location_arr";
    private WeatherDataManager wdm = new WeatherDataManager(FILENAME_LAST_ID, FILENAME_LOCATION_ARR);
    private Location location = new Location();
    private int cityId = 0;

    private TextView locationText;
    private TextView dateText;
    private TextView currentTempText;
    private TextView maxMinTempText;
    private TextView weatherDescText;
    private TextView windSpeedText;
    private TextView pressureText;
    private TextView forecastDateDay1;
    private TextView forecastTempDay1;
    private TextView forecastDateDay2;
    private TextView forecastTempDay2;
    private TextView forecastDateDay3;
    private TextView forecastTempDay3;
    private TextView forecastDateDay4;
    private TextView forecastTempDay4;
    private TextView forecastDateDay5;
    private TextView forecastTempDay5;
    private TextView forecastDateDay6;
    private TextView forecastTempDay6;
    private ImageView iconCurrentImage;
    private ImageView iconWindDirection;
    private ImageView forecastIconDay1;
    private ImageView forecastIconDay2;
    private ImageView forecastIconDay3;
    private ImageView forecastIconDay4;
    private ImageView forecastIconDay5;
    private ImageView forecastIconDay6;
    private TextView OWMText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStartUpScreen();
        File fileId = new File(getFilesDir(), FILENAME_LAST_ID);
        File fileArr = new File(getFilesDir(), FILENAME_LOCATION_ARR);
        CurrentWeatherTask task = new CurrentWeatherTask();

        if(!fileId.exists() || !fileId.isFile() || fileId.length() == 0){
            Intent intent_search = new Intent(this, SearchActivity.class);
            this.startActivity(intent_search);
            finish();
            return;
        } else {
            try {
                cityId = wdm.fiom.loadLastId(FILENAME_LAST_ID, getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(fileArr.length() != 0 && cityId != 0 ){
                try {
                    location = task.execute(new Integer[]{cityId}).get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    System.out.println("1");
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    System.out.println("2");
                    e.printStackTrace();
                }
            }
            System.out.println("wdm.checkLAstUpdate(): " + wdm.checkLastUpdate(location));
            if(wdm.checkLastUpdate(location)){
                RefreshTask refTask = new RefreshTask();
                refTask.execute(new Integer[]{cityId});
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Intent intent_search = new Intent(this, SearchActivity.class);
                this.startActivity(intent_search);
                finish();
                break;
            case R.id.action_locations:
                Intent intent_locations = new Intent(this, LocationsActivity.class);
                this.startActivity(intent_locations);
                finish();
                break;
            case R.id.action_refresh:
                //dodaj zabezpieczenie przez uruchomieoniem refreshtask kiedy dziala
                RefreshTask task = new RefreshTask();
                task.execute(new Integer[]{cityId});
                break;
//        default:
//        	return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void setStartUpScreen() {
        locationText = (TextView) findViewById(R.id.locationText);
        dateText = (TextView) findViewById(R.id.dateText);
        currentTempText = (TextView) findViewById(R.id.currentTempText);
        maxMinTempText = (TextView) findViewById(R.id.maxMinTempText);
        weatherDescText = (TextView) findViewById(R.id.weatherDescText);
        windSpeedText = (TextView) findViewById(R.id.windSpeedText);
        iconWindDirection =  (ImageView) findViewById(R.id.iconWindDirection);
        pressureText = (TextView) findViewById(R.id.pressureText);
        forecastDateDay1 = (TextView) findViewById(R.id.forecastDateDay1);
        forecastTempDay1 = (TextView) findViewById(R.id.forecastTempDay1);
        forecastDateDay2 = (TextView) findViewById(R.id.forecastDateDay2);
        forecastTempDay2 = (TextView) findViewById(R.id.forecastTempDay2);
        forecastDateDay3 = (TextView) findViewById(R.id.forecastDateDay3);
        forecastTempDay3 = (TextView) findViewById(R.id.forecastTempDay3);
        forecastDateDay4 = (TextView) findViewById(R.id.forecastDateDay4);
        forecastTempDay4 = (TextView) findViewById(R.id.forecastTempDay4);
        forecastDateDay5 = (TextView) findViewById(R.id.forecastDateDay5);
        forecastTempDay5 = (TextView) findViewById(R.id.forecastTempDay5);
        forecastDateDay6 = (TextView) findViewById(R.id.forecastDateDay6);
        forecastTempDay6 = (TextView) findViewById(R.id.forecastTempDay6);
        iconCurrentImage = (ImageView) findViewById(R.id.iconCurrentImage);
        forecastIconDay1 = (ImageView) findViewById(R.id.forecastIconDay1);
        forecastIconDay2 = (ImageView) findViewById(R.id.forecastIconDay2);
        forecastIconDay3 = (ImageView) findViewById(R.id.forecastIconDay3);
        forecastIconDay4 = (ImageView) findViewById(R.id.forecastIconDay4);
        forecastIconDay5 = (ImageView) findViewById(R.id.forecastIconDay5);
        forecastIconDay6 = (ImageView) findViewById(R.id.forecastIconDay6);
        OWMText = (TextView) findViewById(R.id.OWMText);
    }

    @SuppressLint("NewApi") protected void setLayoutContent(String[] data, int[] icons){
        locationText.setText(data[0]);
        dateText.setText(data[1]);
        currentTempText.setText(data[2]);
        maxMinTempText.setText(data[3]);
        weatherDescText.setText(data[4]);
        windSpeedText.setText(data[5]);
        iconWindDirection.setRotation(Float.valueOf(data[6])+90);
        pressureText.setText(data[7]);
        forecastDateDay1.setText(data[8]);
        forecastDateDay2.setText(data[9]);
        forecastDateDay3.setText(data[10]);
        forecastDateDay4.setText(data[11]);
        forecastDateDay5.setText(data[12]);
        forecastDateDay6.setText(data[13]);
        forecastTempDay1.setText(data[14]);
        forecastTempDay2.setText(data[15]);
        forecastTempDay3.setText(data[16]);
        forecastTempDay4.setText(data[17]);
        forecastTempDay5.setText(data[18]);
        forecastTempDay6.setText(data[19]);
        iconCurrentImage.setImageResource(icons[0]);
        forecastIconDay1.setImageResource(icons[1]);
        forecastIconDay2.setImageResource(icons[2]);
        forecastIconDay3.setImageResource(icons[3]);
        forecastIconDay4.setImageResource(icons[4]);
        forecastIconDay5.setImageResource(icons[5]);
        forecastIconDay6.setImageResource(icons[6]);
    }



    private class CurrentWeatherTask extends AsyncTask<Integer, Void, Location> {

        @Override
        protected Location doInBackground(Integer... params) {

            try {
                wdm.loadSavedData(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("currentwearhertask cityId: " + params[0]);
                location = wdm.loadSavedLocation((int)params[0]);
                //if null refresh for cityId
            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;

        }


        @Override
        protected void onPostExecute(Location location) {
            super.onPostExecute(location);
            try {
                location.parseIcons();
                location.parseDataFields();
                setLayoutContent(location.getData(), location.getIcons());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class RefreshTask extends AsyncTask<Integer, Void, Location>{

        @Override
        protected Location doInBackground(Integer... params) {
            try {
                location = wdm.downloadData((int)params[0]);
                wdm.addLocationToArr(location);
                wdm.saveDownloadData(getApplicationContext());
                System.out.println("refreshtask cityId: " + cityId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;
        }
        @Override
        protected void onPostExecute(Location result) {
            super.onPostExecute(result);
            try {
                location.parseIcons();
                location.parseDataFields();
                setLayoutContent(location.getData(), location.getIcons());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }}
