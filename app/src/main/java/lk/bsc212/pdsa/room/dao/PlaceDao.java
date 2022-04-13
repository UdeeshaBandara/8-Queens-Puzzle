package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import lk.bsc212.pdsa.model.QueenPlace;

@Dao
public interface PlaceDao {
    @Insert
    void insertAll(QueenPlace... users);


    @Query("SELECT * FROM QUEENPLACE")
    List<QueenPlace> getQueenPlaces();

    @Query("SELECT COUNT(*) FROM QUEENPLACE")
    int combinationCount();
}
