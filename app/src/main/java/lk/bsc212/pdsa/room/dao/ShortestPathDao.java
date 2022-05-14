package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import lk.bsc212.pdsa.model.room.CityDistance;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.room.ShortestDistanceAnswerCity;

@Dao
public interface ShortestPathDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(ShortestDistanceAnswerCity... shortestDistanceAnswer);


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDistanceBetweenCities(CityDistance... cityDistance);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShortestPaths(ShortestDistanceAnswer... CityDistanceAnswer);
}
