package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import lk.bsc212.pdsa.model.QueenPlace;

@Dao
public interface PlaceDao {


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(QueenPlace... places);

    @Query("SELECT * FROM QUEENPLACE")
    List<QueenPlace> getQueenPlaces();

    @Query("SELECT COUNT(*) FROM QUEENPLACE")
    int combinationCount();


    @Query("SELECT COUNT(*) FROM QUEENPLACE WHERE answeredUserId = 0")
    int checkOtherOptionExist();

    @Query("UPDATE QUEENPLACE SET answeredUserId = 0")
    int resetGame();


}
