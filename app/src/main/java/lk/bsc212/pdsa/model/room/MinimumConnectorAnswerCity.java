package lk.bsc212.pdsa.model.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MinimumConnectorAnswerCity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long fromCityName;
    public long toCityName;

    public int shortestDistance;


    public long answerId;

    public MinimumConnectorAnswerCity(long fromCityName, long toCityName, int shortestDistance, long answerId) {
        this.fromCityName = fromCityName;
        this.toCityName = toCityName;
        this.shortestDistance = shortestDistance;
        this.answerId = answerId;
    }
}
