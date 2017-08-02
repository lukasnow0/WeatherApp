package snow.com.weatherapp.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import snow.com.weatherapp.R;
import snow.com.weatherapp.dataholder.City;
import snow.com.weatherapp.dataholder.Location;

/**
 * Created by Snow on 2016-05-23.
 */
public class LocationsActivity extends AppCompatActivity {
    private static final String FILENAME_LAST_ID = "last_id";
    private static final String FILENAME_LOCATION_ARR = "location_arr";
    private WeatherDataManager wdm = new WeatherDataManager(FILENAME_LAST_ID, FILENAME_LOCATION_ARR);
    protected ArrayList<City> cityList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        listView = (ListView) findViewById(R.id.cityListView);
        CityListLoaderTask task = new CityListLoaderTask();
        task.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    wdm.fiom.saveLastId(FILENAME_LAST_ID, cityList.get(position).getId(), getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LocationsActivity.this, MainActivity.class);
                LocationsActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LocationsActivity.this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }



    private class CityListLoaderTask extends AsyncTask<Void, Void, ArrayList<City>> {

        @Override
        protected ArrayList<City> doInBackground(Void... params) {
            ArrayList<Location> locationArr = new ArrayList<>();
            try {
                locationArr = wdm.fiom.loadLocationArr(FILENAME_LOCATION_ARR, getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!locationArr.isEmpty()){
                for (Location location : locationArr) {
                    int id = location.getCityId();
                    String name = location.getCityName();
                    String country = location.getCountry();
                    cityList.add(new City(id, name, country));
                }
                return cityList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<City> cityList) {
            super.onPostExecute(cityList);
            setListView(cityList);
        }

        protected void setListView(ArrayList<City> cityList){
            List<Map<String, String>> list = new ArrayList<>();
            for (City city : cityList) {
                list.add(createCity("key", city.getName() + ", " + city.getCountry()));
            }
            SimpleAdapter simpAdapt = new SimpleAdapter(LocationsActivity.this, list, android.R.layout.simple_list_item_1, new String[] {"key"}, new int[] {android.R.id.text1});
            listView.setAdapter(simpAdapt);
        }

        private HashMap<String, String> createCity(String key, String name) {
            HashMap<String, String> city = new HashMap<>();
            city.put(key, name);
            return city;
        }
    }
}
