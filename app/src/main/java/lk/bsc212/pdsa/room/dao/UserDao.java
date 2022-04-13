package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import lk.bsc212.pdsa.model.QueenPlaceUser;
import lk.bsc212.pdsa.model.User;

@Dao
public interface UserDao {

    @Insert
    long[] insertAll(User... users);


    @Transaction
    @Query("SELECT * FROM User INNER JOIN QUEENPLACE ON User.userId = QUEENPLACE.answeredUserId WHERE  QUEENPLACE.placeId  = :placeId")
    List<QueenPlaceUser> getAnsweredUser(long placeId);

}
