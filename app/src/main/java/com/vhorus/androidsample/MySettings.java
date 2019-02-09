package com.vhorus.androidsample;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import com.vhorus.androidsample.entities.Location;

import java.util.List;

public class MySettings {
    private static final String TAG = MySettings.class.getSimpleName();

    private static SharedPreferences sharedPref;

    private static AppDatabase database;

    private MySettings(){

    }

    public static void addLocation(Location location){
        MySettings.initDB().locationDAO().insertLocation(location);
    }
    public static List<Location> getAllLocations(){
        return MySettings.initDB().locationDAO().getAll();
    }
    public static Location getLocation(long locationID){
        return MySettings.initDB().locationDAO().getLocation(locationID);
    }
    public static Location getLocationByTitle(String title){
        return MySettings.initDB().locationDAO().getLocationByTitle(title);
    }
    public static void removeLocation(long locationID){
        MySettings.initDB().locationDAO().removeLocation(locationID);
    }

    public static AppDatabase initDB(){
        if(database != null){
            return database;
        }else{
            database = Room.databaseBuilder(MyApp.getInstance(), AppDatabase.class, Constants.DB_NAME)
                    .allowMainThreadQueries().
                            build();
            return database;
        }
    }

    public static SharedPreferences getSettings() {
        if(sharedPref == null){
            sharedPref = MyApp.getShardPrefs();
        }

        return sharedPref;
    }
}
