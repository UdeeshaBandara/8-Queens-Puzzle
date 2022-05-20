package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;

@Dao
public interface MinimumConnectorDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(MinimumConnectorAnswer... minimumConnectorAnswer);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDistanceBetweenCities(CityDistanceMinimumConnector... cityDistanceMinimumConnector);


    @Query("UPDATE CityDistanceMinimumConnector  SET isVisited = 1  WHERE (fromCityName = :from AND toCityName = :to) AND answerId = :answerId")
    void updateVisitedFlag(String from, String to, String answerId);
}
