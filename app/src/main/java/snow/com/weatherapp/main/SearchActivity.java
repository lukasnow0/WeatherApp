package snow.com.weatherapp.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import snow.com.weatherapp.R;
import snow.com.weatherapp.dataholder.City;
import snow.com.weatherapp.dataholder.Location;

/**
 * Created by Snow on 2016-05-23.
 */
public class SearchActivity extends AppCompatActivity {	private static final String FILENAME_LAST_ID = "last_id";
    private static final String FILENAME_LOCATION_ARR = "location_arr";
    private WeatherDataManager wdm = new WeatherDataManager(FILENAME_LAST_ID, FILENAME_LOCATION_ARR);
    protected ArrayList<City> cityList = new ArrayList<>();


    private TextView citySearchText;
    private EditText cityEnterEditText;
    private Button searchButton;
    private TextView resultText;
    private ListView resultListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpScreen();
        System.out.println("SearchActivity startup");
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(cityEnterEditText.length() > 0){
                    String[] params = {cityEnterEditText.getText().toString()};
                    JSONsearchCityTask task = new JSONsearchCityTask();
                    task.execute(params);
                }
            }
        });
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SearchActivity.this, "Downloading weather ...", Toast.LENGTH_SHORT).show();
                try {
                    wdm.fiom.saveLastId(FILENAME_LAST_ID, cityList.get(position).getId(), getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DownloadWeatherOnClickTask task = new DownloadWeatherOnClickTask();
                task.execute(cityList.get(position).getId());
            }
        });

    }
    @Override
    public void onBackPressed(){
        File fileArr = new File(getFilesDir(), FILENAME_LOCATION_ARR);
        System.out.println(fileArr.exists());
        if(fileArr.exists()) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            SearchActivity.this.startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }


    private void setUpScreen() {
        citySearchText = (TextView) findViewById(R.id.citySearchText);
        cityEnterEditText = (EditText) findViewById(R.id.cityEnterEdidText);
        searchButton = (Button) findViewById(R.id.searchButton);
        resultText = (TextView) findViewById(R.id.resultText);
        resultListView = (ListView) findViewById(R.id.resultListView);
    }

    private class JSONsearchCityTask extends AsyncTask<String, Void, ArrayList<City>> {

        @TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
        protected ArrayList<City> doInBackground(String... params) {
            ArrayList<City> result = new ArrayList<City>();
            String data = null;
            try {
                data = new HttpClient().searchCity(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SearchActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
            }
            try {
                if(!data.isEmpty()){
                    result = JSONWeatherParser.getCityList(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cityList = result;
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<City> result) {
            super.onPostExecute(result);
            setListView(result);
        }

        protected void setListView(ArrayList<City> cityList){
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Iterator<City> iterator = cityList.iterator();
            while(iterator.hasNext()){
                City city = iterator.next();
                list.add(createCity("key", city.getName() +", " + city.getCountry()));
            }
            SimpleAdapter simpAdapt = new SimpleAdapter(SearchActivity.this, list, android.R.layout.simple_list_item_1, new String[] {"key"}, new int[] {android.R.id.text1});
            resultListView.setAdapter(simpAdapt);
        }

        private HashMap<String, String> createCity(String key, String name) {
            HashMap<String, String> city = new HashMap<>();
            city.put(key, name);
            return city;
        }
    }

    private class DownloadWeatherOnClickTask extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                wdm.loadSavedData(getApplicationContext());
            }  catch (Exception e) {
                e.printStackTrace();
            }
            Location location = new Location();
            try {
                location = wdm.downloadData(params[0]);
                System.out.println("SearchActivity: DWOCtask: location.getCityName: " + location.getCityName());
                if(location.getCityId() != 0){
                    wdm.addLocationToArr(location);
                    System.out.println("SearchActivity: DWOCtask if");
                    try {
                        wdm.saveDownloadData(getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            SearchActivity.this.startActivity(intent);
            finish();
        }

    }

}
