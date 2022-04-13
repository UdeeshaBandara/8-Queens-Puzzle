package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import lk.bsc212.pdsa.model.QueenPlace;
import lk.bsc212.pdsa.model.QueenPlaceUser;

@Dao
public interface PlaceDao {
    @Insert
    void insertAll(QueenPlace... places);

    @Insert
    default void inset(QueenPlaceUser... answer){


    }


    @Query("SELECT * FROM QUEENPLACE")
    List<QueenPlace> getQueenPlaces();

    @Query("SELECT COUNT(*) FROM QUEENPLACE")
    int combinationCount();

    @Transaction
    @Query("SELECT * FROM QUEENPLACE WHERE placeId = :placeId")
    List<QueenPlaceUser> getAnsweredUser(int placeId);

}
