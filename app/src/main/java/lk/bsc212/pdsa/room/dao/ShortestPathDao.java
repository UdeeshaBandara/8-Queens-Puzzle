package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import java.util.List;

import lk.bsc212.pdsa.model.CityDistance;
import lk.bsc212.pdsa.model.ShortestDistanceAnswer;
import lk.bsc212.pdsa.model.ShortestDistanceAnswerCity;

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
