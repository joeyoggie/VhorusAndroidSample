package com.vhorus.androidsample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vhorus.androidsample.dao.LocationDAO;
import com.vhorus.androidsample.entities.Location;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract LocationDAO locationDAO();
}
