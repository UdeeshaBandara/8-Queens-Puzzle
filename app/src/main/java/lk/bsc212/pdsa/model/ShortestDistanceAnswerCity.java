package lk.bsc212.pdsa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShortestDistanceAnswerCity {
    @PrimaryKey(autoGenerate = true)
    public long answerId;

    public long userId;

    public int systemSelectedCityName;

    public ShortestDistanceAnswerCity(long userId, int systemSelectedCityName) {
        this.userId = userId;
        this.systemSelectedCityName = systemSelectedCityName;
    }
}
