package lk.bsc212.pdsa.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import lk.bsc212.pdsa.model.User;

@Dao
public interface UserDao {

    @Insert
    long[] insertAll(User... users);


    @Query("SELECT * FROM USER")
    List<User> getQueenPlaces();

    @Query("SELECT COUNT(*) FROM USER")
    int combinationCount();


}
