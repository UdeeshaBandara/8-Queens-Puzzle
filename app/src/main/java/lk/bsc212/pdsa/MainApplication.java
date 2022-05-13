package lk.bsc212.pdsa;

import android.app.Application;
import android.content.Context;

import lk.bsc212.pdsa.room.AppDatabase;
import lk.bsc212.pdsa.room.dao.PlaceDao;
import lk.bsc212.pdsa.room.dao.ShortestPathDao;
import lk.bsc212.pdsa.room.dao.UserDao;

public class MainApplication extends Application {

    private static MainApplication context;

    public static MainApplication getInstance() {
        return context;
    }


    //Database
    public static AppDatabase appDatabase;
    public static PlaceDao placeDao;
    public static UserDao userDao;
    public static ShortestPathDao shortestPathDao;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        appDatabase = AppDatabase.getDatabase(MainApplication.this);
        placeDao = appDatabase.placeDao();
        userDao = appDatabase.userDao();
        shortestPathDao = appDatabase.shortestPathDao();


    }

    public static Context getContext() {
        return context;
    }
}
