package lk.bsc212.pdsa.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.CityDistanceShortestPath;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;
import lk.bsc212.pdsa.model.room.QueenPlace;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswerCity;
import lk.bsc212.pdsa.model.room.User;
import lk.bsc212.pdsa.room.dao.MinimumConnectorDao;
import lk.bsc212.pdsa.room.dao.QueenPlaceDao;
import lk.bsc212.pdsa.room.dao.ShortestPathDao;
import lk.bsc212.pdsa.room.dao.UserDao;

@Database(entities = {QueenPlace.class, User.class,
        ShortestDistanceAnswerCity.class, ShortestDistanceAnswer.class,
        CityDistanceShortestPath.class, MinimumConnectorAnswer.class,
        CityDistanceMinimumConnector.class}, version = 6)

public abstract class AppDatabase extends RoomDatabase {
    public abstract QueenPlaceDao queenPlaceDao();

    public abstract UserDao userDao();

    public abstract ShortestPathDao shortestPathDao();

    public abstract MinimumConnectorDao minimumConnectorDao();

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