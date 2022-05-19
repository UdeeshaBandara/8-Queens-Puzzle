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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertShortestPaths(MinimumConnectorAnswerCity... minimumConnectorAnswerCity);

//    @Query("SELECT id FROM CityDistanceMinimumConnector WHERE (fromCityName = :from AND toCityName = :to) OR (fromCityName = :to AND toCityName = :from) AND distance = :distance ORDER BY ID DESC LIMIT 1")
//    long getCityIdByFromAndToCity(String from, String to, String distance);

    @Query("UPDATE CityDistanceMinimumConnector  SET isVisited = 1  WHERE (fromCityName = :from AND toCityName = :to) OR (fromCityName = :to AND toCityName = :from) AND answerId = :answerId")
    long updateVisitedFlag(String from, String to, String answerId);
}
