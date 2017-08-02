package snow.com.weatherapp.main;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import snow.com.weatherapp.dataholder.Location;

/**
 * Created by Snow on 2016-05-16.
 */
public class FileIOManager {
    public void saveLastId(String FILENAME, int id, Context context) throws IOException {
        FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeInt(id);
        dos.flush();
        dos.close();
        fos.close();
    }

    public int loadLastId(String FILENAME, Context context) throws IOException{
        FileInputStream fis = context.getApplicationContext().openFileInput(FILENAME);
        DataInputStream dis = new DataInputStream(fis);
        int id = dis.readInt();
        dis.close();
        fis.close();
        return id;
    }

    public void saveLocationArr (String FILENAME, ArrayList<Location> locationArr, Context context) throws IOException{
        FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));
        oos.writeObject(locationArr);
        oos.flush();
        oos.close();
        fos.close();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Location> loadLocationArr(String FILENAME, Context context) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(context.getApplicationContext().openFileInput(FILENAME)));
        ArrayList<Location> locationArr = (ArrayList<Location>) ois.readObject();
        ois.close();
        return locationArr;
    }
}
