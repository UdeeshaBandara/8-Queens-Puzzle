package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityDistanceMinimumConnector {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int fromCityName;

    public int toCityName;

    public double distance;

    public long answerId;

    public int isVisited;


    public CityDistanceMinimumConnector(int fromCityName, int toCityName, double distance, long answerId) {
        this.fromCityName = fromCityName;
        this.toCityName = toCityName;
        this.distance = distance;
        this.answerId = answerId;
    }
}
