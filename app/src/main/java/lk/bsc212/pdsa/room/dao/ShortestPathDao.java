package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import lk.bsc212.pdsa.model.room.CityDistanceShortestPath;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswerCity;

@Dao
public interface ShortestPathDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(ShortestDistanceAnswer... shortestDistanceAnswer);


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDistanceBetweenCities(CityDistanceShortestPath... cityDistanceShortestPath);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShortestPaths(ShortestDistanceAnswerCity... CityDistanceAnswer);
}
