package lk.bsc212.pdsa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityDistance {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int fromCityName;

    public int toCityName;

    public double distance;

    public long answerId;


    public CityDistance(int fromCityName, int toCityName, double distance, long answerId) {
        this.fromCityName = fromCityName;
        this.toCityName = toCityName;
        this.distance = distance;
        this.answerId = answerId;
    }
}
