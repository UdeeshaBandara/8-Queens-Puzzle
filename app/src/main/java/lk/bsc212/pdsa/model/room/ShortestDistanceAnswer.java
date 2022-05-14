package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShortestDistanceAnswer {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cityName;

    public double shortestDistance;


    public long answerId;

    public ShortestDistanceAnswer(long cityName, double shortestDistance, long answerId) {
        this.cityName = cityName;
        this.shortestDistance = shortestDistance;
        this.answerId = answerId;
    }
}
