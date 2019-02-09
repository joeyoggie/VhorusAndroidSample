package com.vhorus.androidsample.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.vhorus.androidsample.entities.Location;

import java.util.List;

@Dao
public abstract class LocationDAO {
    @Query("SELECT * FROM location")
    public abstract List<Location> getAll();

    @Query("SELECT * FROM location WHERE id =:locationID")
    public abstract Location getLocation(long locationID);

    @Query("SELECT * FROM location WHERE title =:title")
    public abstract Location getLocationByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertLocation(Location location);

    @Query("DELETE from location WHERE id=:locationID")
    public abstract void removeLocation(long locationID);
}
