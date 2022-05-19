package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShortestDistanceAnswerCity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cityName;

    public int shortestDistance;

    public long answerId;

    public ShortestDistanceAnswerCity(long cityName, int shortestDistance, long answerId) {
        this.cityName = cityName;
        this.shortestDistance = shortestDistance;
        this.answerId = answerId;
    }
}
