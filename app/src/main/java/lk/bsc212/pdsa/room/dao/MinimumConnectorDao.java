package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import lk.bsc212.pdsa.model.room.CityDistanceMinimumConnector;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswer;
import lk.bsc212.pdsa.model.room.MinimumConnectorAnswerCity;
@Dao
public interface MinimumConnectorDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(MinimumConnectorAnswer... minimumConnectorAnswer);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDistanceBetweenCities(CityDistanceMinimumConnector... cityDistanceMinimumConnector);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertShortestPaths(MinimumConnectorAnswerCity... minimumConnectorAnswerCity);
}
