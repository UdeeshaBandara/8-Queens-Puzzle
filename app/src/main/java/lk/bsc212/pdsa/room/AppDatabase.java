package lk.bsc212.pdsa.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lk.bsc212.pdsa.model.QueenPlace;
import lk.bsc212.pdsa.model.User;
import lk.bsc212.pdsa.room.dao.PlaceDao;
import lk.bsc212.pdsa.room.dao.UserDao;

@Database(entities = {QueenPlace.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "pdsa")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}